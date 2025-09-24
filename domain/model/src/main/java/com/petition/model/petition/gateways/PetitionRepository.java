package com.petition.model.petition.gateways;

import com.petition.model.petition.PageRequest;
import com.petition.model.petition.Petition;
import com.petition.model.petition.PetitionData;
import com.petition.model.sqs.PetitionDecisionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetitionRepository {
    Mono<Petition> savePetition(Petition petition, String traceId);
    Flux<Petition> findByIdState(Long stateId, PageRequest pageRequest, String traceId);
    Mono<Petition> updatePetition(Petition petition, String traceId);
    Flux<PetitionData> findAllPetitionByEmailAndStateAproved(String email, String traceId);
    Mono<Petition> updatePetitionSQSResponse(PetitionDecisionResponse decision);
}
