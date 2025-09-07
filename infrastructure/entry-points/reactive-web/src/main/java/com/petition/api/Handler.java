package com.petition.api;

import com.petition.model.exception.*;
import com.petition.api.exceptionhandler.ControllerAdvisor;
import com.petition.model.constants.Constants;
import com.petition.model.petition.PageRequest;
import com.petition.usecase.petition.validation.PetitionValidatorUseCase;
import com.petition.model.petition.Petition;
import com.petition.usecase.petition.PetitionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Solicitudes", description = "Operaciones sobre solicitudes")
public class Handler {
    private final PetitionUseCase petitionUseCase;
    private final ControllerAdvisor controllerAdvisor;
    private final PetitionValidatorUseCase petitionValidatorUseCase;

    @Operation(summary = "Crear una solicitud")
    public Mono<ServerResponse> savePetition(ServerRequest serverRequest) {
        String traceId = serverRequest.headers().firstHeader("X-Trace-Id");
        String authHeader = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        log.info(Constants.LOG_REQUEST_RECEIVED, traceId);
        return serverRequest.bodyToMono(Petition.class)
                .flatMap(petitionValidatorUseCase::validate)
                .doOnNext(result -> log.info(Constants.LOG_PETITION_VALIDATOR_PROCESSING, result, traceId))
                .flatMap(petition -> petitionUseCase.savePetition(petition,traceId, authHeader))
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
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest))
                .onErrorResume(PermissionDeniedException.class, ex ->
                        controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest));

    }

    public Mono<ServerResponse> findBySearch(ServerRequest serverRequest) {
        String traceId = serverRequest.headers().firstHeader("X-Trace-Id");
        String authHeader = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        Long stateId = serverRequest.queryParam("stateId")
                .map(Long::valueOf)
                .orElse(null);

        PageRequest pageRequest = PageRequest.builder()
                .page(Integer.parseInt(serverRequest.queryParam("page").orElse("0")))
                .size(Integer.parseInt(serverRequest.queryParam("size").orElse("10")))
                .build();
        log.info(Constants.LOG_SEARCH_REQUEST_RECEIVED, stateId, pageRequest.getPage(), pageRequest.getSize(), traceId);
        return petitionUseCase.findBySearch(stateId, pageRequest, traceId, authHeader)
                .collectList()
                .flatMap(list -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(list))
                .doOnSuccess(resp -> log.info(Constants.LOG_SEARCH_RESPONSE_SENT, resp.statusCode(), traceId))
                .doOnError(error -> log.error(Constants.LOG_SEARCH_ERROR, error.getMessage(), traceId, error))
                .onErrorResume(DataNotFoundException.class,
                        ex -> controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest))
                .onErrorResume(PermissionDeniedException.class,
                ex -> controllerAdvisor.handleDataAlreadyExistsException(ex, serverRequest));

    }
}
