package com.petition.usecase.petition;

import com.petition.model.auth.IJwtProvider;
import com.petition.model.exception.IdentityDocumentNotFoundException;
import com.petition.model.exception.PermissionDeniedException;
import com.petition.model.exceptionusecase.ExceptionResponse;
import com.petition.model.petition.Petition;
import com.petition.model.petition.gateways.PetitionRepository;
import com.petition.model.state.gateways.AuthClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
public class PetitionUseCase {
    private final PetitionRepository petitionRepository;
    private final IJwtProvider jwtProvider;
    private final AuthClient authClient;

    public Mono<Void> savePetition(Petition petition, String traceId, String authHeader) {
        return authClient.findByDocument(petition.getIdentityDocument(), authHeader)
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
}
