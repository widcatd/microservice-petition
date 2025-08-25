package com.petition.usecase.petition;

import com.petition.model.loantype.gateways.LoanTypeRepository;
import com.petition.model.petition.Petition;
import com.petition.model.petition.gateways.PetitionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PetitionUseCase {
    private final PetitionRepository petitionRepository;
    private final LoanTypeRepository loanTypeRepository;
    public Mono<Void> savePetition(Petition petition) {
        return petitionRepository.savePetition(petition).then();
    }
}
