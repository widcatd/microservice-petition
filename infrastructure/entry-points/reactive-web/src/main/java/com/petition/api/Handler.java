package com.petition.api;

import com.petition.api.exception.IdentityDocumentNotFoundException;
import com.petition.api.exceptionhandler.ControllerAdvisor;
import com.petition.api.exceptionhandler.ExceptionResponse;
import com.petition.api.webclient.IAuthenticationClient;
import com.petition.model.constants.Constants;
import com.petition.model.exception.PetitionValidationException;
import com.petition.usecase.petition.validation.PetitionValidatorUseCase;
import com.petition.model.exception.RegisterNotFoundException;
import com.petition.model.petition.Petition;
import com.petition.usecase.petition.PetitionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Solicitudes", description = "Operaciones sobre solicitudes")
public class Handler {
    private final PetitionUseCase petitionUseCase;
    private final IAuthenticationClient authenticationClient;
    private final ControllerAdvisor controllerAdvisor;
    private final PetitionValidatorUseCase petitionValidatorUseCase;

    @Operation(summary = "Crear una solicitud")
    public Mono<ServerResponse> savePetition(ServerRequest serverRequest) {
        String traceId = serverRequest.headers().firstHeader("X-Trace-Id");
        log.info(Constants.LOG_REQUEST_RECEIVED, traceId);
        return serverRequest.bodyToMono(Petition.class)
                .flatMap(petitionValidatorUseCase::validate)
                .doOnNext(result -> log.info(Constants.LOG_PETITION_VALIDATOR_PROCESSING, result, traceId))
                .flatMap(petition ->
                        authenticationClient.findByDocument(petition.getIdentityDocument())
                                .switchIfEmpty(Mono.error(new IdentityDocumentNotFoundException( ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getCode(),
                                        String.format(ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getMessage(), petition.getIdentityDocument()))))
                                .doOnNext(userDto -> log.info(Constants.LOG_USER_FOUND, userDto.getEmail(), traceId))
                                .map(userDto ->{
                                    petition.setEmail(userDto.getEmail());
                                    return petition;
                                })
                )
                .flatMap(petition -> petitionUseCase.savePetition(petition,traceId))
                .doOnSuccess(saved -> log.info(Constants.LOG_PETITION_SAVED, traceId))
                .then(Mono.defer(() -> ServerResponse.created(URI.create("/api/v1/solicitud/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()))
                .doOnSuccess(resp -> log.info(Constants.LOG_RESPONSE_SENT, resp.statusCode(), traceId))
                .doOnError(error -> log.error(Constants.LOG_ERROR_PROCESSING, error.getMessage(), traceId, error))
                .onErrorResume(RegisterNotFoundException.class, ex ->
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest))
                .onErrorResume(IdentityDocumentNotFoundException.class, ex ->
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest))
                .onErrorResume(PetitionValidationException.class, ex ->
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest));

    }
}
