package com.petition.usecase.petition;

import com.petition.model.loantype.LoanType;
import com.petition.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanTypeUseCase {
    private final LoanTypeRepository loanTypeRepository;
    public Mono<LoanType> findByIdLoanType(Long idLoanType) {
        return loanTypeRepository.findByIdLoanType(idLoanType);
    }
}
