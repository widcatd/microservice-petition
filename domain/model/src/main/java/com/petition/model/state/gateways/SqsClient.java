package com.petition.model.state.gateways;

import com.petition.model.petition.Petition;
import com.petition.model.sqs.MessageRequest;
import reactor.core.publisher.Mono;

public interface SqsClient {
    Mono<String> sendMessage(MessageRequest messageRequest, String traceId);
}
