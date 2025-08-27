package com.petition.api;

import com.petition.api.exception.IdentityDocumentNotFoundException;
import com.petition.api.exception.ValidatorHandler;
import com.petition.api.exceptionhandler.ControllerAdvisor;
import com.petition.api.exceptionhandler.ExceptionResponse;
import com.petition.api.helper.IPetitionRequestMapper;
import com.petition.api.webclient.IAuthenticationClient;
import com.petition.model.exception.PetitionValidationException;
import com.petition.usecase.petition.validation.PetitionValidatorUseCase;
import com.petition.model.exception.RegisterNotFoundException;
import com.petition.model.petition.Petition;
import com.petition.usecase.petition.PetitionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Tag(name = "Solicitudes", description = "Operaciones sobre solicitudes")
public class Handler {
    private final PetitionUseCase petitionUseCase;
    private final ValidatorHandler validatorHandler;
    private final IPetitionRequestMapper petitionRequestMapper;
    private final IAuthenticationClient authenticationClient;
    private final ControllerAdvisor controllerAdvisor;
    private final PetitionValidatorUseCase petitionValidatorUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }
    @Operation(summary = "Crear una solicitud")
    public Mono<ServerResponse> savePetition(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Petition.class)
                .flatMap(petitionValidatorUseCase::validate)
                .flatMap(petition ->
                        authenticationClient.findByDocument(petition.getIdentityDocument())
                                .switchIfEmpty(Mono.error(new IdentityDocumentNotFoundException( ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getCode(),
                                        String.format(ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getMessage(), petition.getIdentityDocument()))))
                                .map(userDto ->{
                                    petition.setEmail(userDto.getEmail());
                                    return petition;
                                })
                )
                .flatMap(petitionUseCase::savePetition)
                .then(Mono.defer(() -> ServerResponse.created(URI.create("/api/v1/solicitud/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()))
                .onErrorResume(RegisterNotFoundException.class, ex ->
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest))
                .onErrorResume(IdentityDocumentNotFoundException.class, ex ->
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest))
                .onErrorResume(PetitionValidationException.class, ex ->
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest));

    }
}
