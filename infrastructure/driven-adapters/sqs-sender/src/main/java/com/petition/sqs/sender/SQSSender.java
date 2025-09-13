package com.petition.sqs.sender;

import com.petition.model.state.gateways.SqsClient;
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
    private final ObjectMapper objectMapper;

    public Mono<String> send(String message) {
        return Mono.fromCallable(() -> buildRequest(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Message sent {}", response.messageId()))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }

    @Override
    public Mono<String> sendMessage(MessageRequest messageRequest, String traceId) {
        log.info("comienza a enviar el mensaje");
        try {
            String body = objectMapper.writeValueAsString(messageRequest);
            return send(body);
        }
        catch (Exception ex){
            return Mono.empty();
        }

    }
}
