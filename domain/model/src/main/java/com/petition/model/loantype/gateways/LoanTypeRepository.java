package com.petition.model.loantype.gateways;

import com.petition.model.loantype.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<LoanType> findByIdLoanType(Long idLoanType);
}
