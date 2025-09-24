package com.petition.model.state.gateways;

import com.petition.model.petition.PetitionResponseCalculate;
import com.petition.model.sqs.MessageRequest;
import reactor.core.publisher.Mono;

public interface SqsClient {
    Mono<String> sendMessage(MessageRequest messageRequest, String traceId);
    Mono<String> calculateCapacity(PetitionResponseCalculate petitionResponseCalculate, String traceId);
}
