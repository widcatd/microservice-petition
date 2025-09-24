package com.petition.sqs.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petition.model.constants.Constants;
import com.petition.model.sqs.PetitionDecisionResponse;
import com.petition.usecase.petition.PetitionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ObjectMapper objectMapper;
    private final PetitionUseCase petitionUseCase;

    @Override
    public Mono<Void> apply(Message message) {
        String traceId = message.messageId();
        log.info(Constants.LOG_SQS_MESSAGE_RECEIVED, message.messageId(), traceId);
        try {
            PetitionDecisionResponse decision =
                    objectMapper.readValue(message.body(), PetitionDecisionResponse.class)
                    ;
            log.info(Constants.LOG_SQS_MESSAGE_PARSED, decision.getTraceId());
            return petitionUseCase.updatePetitionSQSResponse(decision)
                    .doOnSubscribe(sub ->
                            log.info(Constants.LOG_SQS_UPDATE_START, decision.getIdPetition(), traceId))
                    .doOnSuccess(v ->
                            log.info(Constants.LOG_SQS_UPDATE_SUCCESS, decision.getIdPetition(), traceId))
                    .doOnError(e ->
                            log.error(Constants.LOG_SQS_PROCESS_ERROR, e.getMessage(), traceId, e))
                    .then();
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
