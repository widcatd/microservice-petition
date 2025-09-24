package com.petition.sqs.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        log.info("comienza recibir el mensaje de calculo de capacidad");
        try {
            PetitionDecisionResponse decision =
                    objectMapper.readValue(message.body(), PetitionDecisionResponse.class);
            return petitionUseCase.updatePetitionSQSResponse(decision)
                    .then();
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
