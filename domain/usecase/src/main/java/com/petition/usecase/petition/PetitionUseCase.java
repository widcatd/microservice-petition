package com.petition.usecase.petition;

import com.petition.model.petition.Petition;
import com.petition.model.petition.gateways.PetitionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PetitionUseCase {
    private final PetitionRepository petitionRepository;
    public Mono<Void> savePetition(Petition petition, String traceId) {
        return petitionRepository.savePetition(petition, traceId).then();
    }
}
