package com.petition.api;

import com.petition.api.dto.CreatePetitionDto;
import com.petition.api.exception.IdentityDocumentNotFoundException;
import com.petition.api.exception.ValidatorHandler;
import com.petition.api.exceptionhandler.ExceptionResponse;
import com.petition.api.helper.IPetitionRequestMapper;
import com.petition.api.webclient.IAuthenticationClient;
import com.petition.api.webclient.dto.UserDto;
import com.petition.usecase.petition.PetitionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor

public class Handler {
    private final PetitionUseCase petitionUseCase;
    private final ValidatorHandler validatorHandler;
    private final IPetitionRequestMapper petitionRequestMapper;
    private final IAuthenticationClient authenticationClient;

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

    public Mono<ServerResponse> savePetition(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreatePetitionDto.class)
                .map(validatorHandler::validate)
                .flatMap(petitionDto ->
                        authenticationClient.findByDocument(petitionDto.getIdentityDocument())
                                .switchIfEmpty(Mono.error(new IdentityDocumentNotFoundException( ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getCode(),
                                        String.format(ExceptionResponse.IDENTITY_DOCUMENT_NOT_FOUND.getMessage(), petitionDto.getIdentityDocument()))))
                                .map(userDto -> petitionRequestMapper.toModel(petitionDto))
                )
                .flatMap(petitionUseCase::savePetition)
                .then(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(""));
    }
}
