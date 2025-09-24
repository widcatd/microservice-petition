package com.petition.usecase.petition;

import com.petition.model.loantype.LoanType;

import java.math.BigDecimal;

public class LoanTypeMock {

    public static LoanType validLoanType() {
        return LoanType.builder()
                .idLoanType(1L)
                .name("Préstamo Personal")
                .interestRate(BigDecimal.valueOf(12.5))
                .build();
    }
}
