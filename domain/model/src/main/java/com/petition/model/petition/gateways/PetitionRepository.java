package com.petition.model.petition.gateways;

import com.petition.model.petition.PageRequest;
import com.petition.model.petition.Petition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetitionRepository {
    Mono<Petition> savePetition(Petition petition, String traceId);
    Flux<Petition> findByIdState(Long stateId, PageRequest pageRequest, String traceId);
}
