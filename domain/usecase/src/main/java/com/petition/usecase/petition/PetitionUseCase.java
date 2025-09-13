package com.petition.usecase.petition;

import com.petition.model.auth.IJwtProvider;
import com.petition.model.exception.DataNotFoundException;
import com.petition.model.exception.IdentityDocumentNotFoundException;
import com.petition.model.exception.PermissionDeniedException;
import com.petition.model.exception.RegisterNotFoundException;
import com.petition.model.exceptionusecase.ExceptionResponse;
import com.petition.model.exceptionusecase.ExceptionUseCaseResponse;
import com.petition.model.loantype.gateways.LoanTypeRepository;
import com.petition.model.petition.*;
import com.petition.model.petition.gateways.PetitionRepository;
import com.petition.model.sqs.MessageRequest;
import com.petition.model.state.gateways.AuthClient;
import com.petition.model.state.gateways.SqsClient;
import com.petition.model.state.gateways.StateRepository;
import com.petition.usecase.petition.message.Message;
import com.petition.usecase.petition.validation.PetitionValidatorUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@RequiredArgsConstructor
public class PetitionUseCase {
    private final PetitionRepository petitionRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StateRepository stateRepository;
    private final IJwtProvider jwtProvider;
    private final AuthClient authClient;
    private final PetitionValidatorUseCase petitionValidatorUseCase;
    private final SqsClient sqsClient;

    public Mono<Void> savePetition(Petition petition, String traceId, String authHeader) {
        return authClient.findByDocument(petition.getIdentityDocument(), authHeader, traceId)
                .switchIfEmpty(Mono.error(new IdentityDocumentNotFoundException(
                        ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getCode(),
                        String.format(ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getMessage(), petition.getIdentityDocument())
                )))
                .flatMap(userDto -> {
                    petition.setEmail(userDto.getEmail());
                    return jwtProvider.getSubject()
                            .flatMap(subject -> {
                                if (!subject.equals(userDto.getEmail())) {
                                    return Mono.error(new PermissionDeniedException(
                                            ExceptionResponse.PERMISSION_DENIED.getCode(),
                                            String.format(ExceptionResponse.PERMISSION_DENIED.getMessage(), petition.getIdentityDocument())
                                    ));
                                }
                                return petitionRepository.savePetition(petition, traceId).then();
                            });
                });
    }

    public Flux<PetitionSearchResponse> findBySearch(Long stateId, PageRequest pageRequest, String traceId, String authHeader) {
        if (stateId == null) {
            return Flux.error(new DataNotFoundException(
                    ExceptionUseCaseResponse.STATE_ID_NOT_FOUND.getCode(),
                    String.format(ExceptionUseCaseResponse.STATE_ID_NOT_FOUND.getMessage(), stateId)
            ));
        }
        return petitionRepository.findByIdState(stateId, pageRequest, traceId)
                .switchIfEmpty(Mono.error(new RegisterNotFoundException(
                        ExceptionUseCaseResponse.PETITION_REGISTER_BY_ID_STATUS_NOT_FOUND.getCode(),
                        String.format(ExceptionUseCaseResponse.PETITION_REGISTER_BY_ID_STATUS_NOT_FOUND.getMessage(), stateId)
                )))
                .flatMap(petition ->
                        Mono.zip(
                                loanTypeRepository.findByIdLoanType(petition.getIdLoanType()),
                                stateRepository.findByIdState(petition.getIdState()),
                                authClient.findByEmail(petition.getEmail(), authHeader,traceId)
                        ).map(TupleUtils.function((loanType, state, user) ->
                                PetitionSearchResponse.toResponse(petition, loanType, state, user)
                        ))
                );
    }

    public Mono<Petition> updatePetition(Petition petition, String traceId) {
        return petitionValidatorUseCase.validateUpdate(petition)
                .flatMap(petitionUpdate -> petitionRepository.updatePetition(petitionUpdate, traceId ))
                .flatMap(petitionResponse ->
                        stateRepository.findByIdState(petitionResponse.getIdState())
                                .flatMap(state -> {
                                    MessageRequest messageRequest =
                                            new MessageRequest(petitionResponse.getEmail(),
                                                    String.format(Message.ESTADO_SOLICITUD,state.getName()),
                                                    Message.SUBJECT);
                                    return sqsClient.sendMessage(messageRequest, traceId);
                                })
                                .flatMap(message -> Mono.just(petitionResponse))
                );
    }
}
