package com.petition.sqs.sender;

import com.petition.model.constants.Constants;
import com.petition.model.petition.PetitionResponseCalculate;
import com.petition.model.state.gateways.SqsClient;
import com.petition.sqs.sender.config.SQSSenderCapacityProperties;
import com.petition.sqs.sender.config.SQSSenderProperties;
import com.petition.model.sqs.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements SqsClient {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final SQSSenderCapacityProperties sqsSenderCapacityProperties;
    private final ObjectMapper objectMapper;

    public Mono<String> send(String message) {
        return Mono.fromCallable(() -> buildRequest(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .map(SendMessageResponse::messageId);
    }

    public Mono<String> sendCapacity(String message) {
        return Mono.fromCallable(() -> buildRequestCapacity(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }
    private SendMessageRequest buildRequestCapacity(String message) {
        return SendMessageRequest.builder()
                .queueUrl(sqsSenderCapacityProperties.queueUrl())
                .messageBody(message)
                .build();
    }

    @Override
    public Mono<String> sendMessage(MessageRequest messageRequest, String traceId) {
        log.info(Constants.LOG_SEND_MESSAGE_START, traceId);
        try {
            String body = objectMapper.writeValueAsString(messageRequest);
            return send(body)
                    .doOnSuccess(resp -> log.info(Constants.LOG_SEND_MESSAGE_SUCCESS, traceId))
                    .doOnError(err -> log.error(Constants.LOG_SEND_MESSAGE_ERROR, err.getMessage(), traceId, err));
        }
        catch (Exception ex){
            log.error(Constants.LOG_SEND_MESSAGE_ERROR, ex.getMessage(), traceId, ex);
            return Mono.empty();
        }

    }

    @Override
    public Mono<String> calculateCapacity(PetitionResponseCalculate petitionResponseCalculate, String traceId) {
        log.info(Constants.LOG_SEND_CAPACITY_START, traceId);
        try {
            String body = objectMapper.writeValueAsString(petitionResponseCalculate);
            return sendCapacity(body)
                    .doOnSuccess(resp -> log.info(Constants.LOG_SEND_CAPACITY_SUCCESS, traceId))
                    .doOnError(err -> log.error(Constants.LOG_SEND_CAPACITY_ERROR, err.getMessage(), traceId, err));
        } catch (Exception ex) {
            log.error(Constants.LOG_SEND_CAPACITY_ERROR, ex.getMessage(), traceId, ex);
            return Mono.error(ex);
        }
    }
}
