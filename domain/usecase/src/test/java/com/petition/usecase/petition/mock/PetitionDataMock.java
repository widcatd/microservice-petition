package com.petition.usecase.petition.mock;

import com.petition.model.petition.PetitionData;

import java.math.BigDecimal;

public class PetitionDataMock {

    public static PetitionData validPetitionData(String email) {
        return PetitionData.builder()
                .idPetition(2L)
                .email(email)
                .mount(BigDecimal.valueOf(5000))
                .loanTerm(6)
                .interestRate(BigDecimal.valueOf(10))
                .idLoanType(1L)
                .idState(1L)
                .build();
    }
}
