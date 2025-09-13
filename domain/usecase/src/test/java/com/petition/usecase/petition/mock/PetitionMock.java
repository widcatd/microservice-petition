package com.petition.usecase.petition.mock;

import com.petition.model.petition.Petition;

import java.math.BigDecimal;

public class PetitionMock {

    public static Petition validPetition() {
        return Petition.builder()
                .idPetition(1L)
                .identityDocument("1234567890")
                .mount(BigDecimal.valueOf(15000.50))
                .loanTerm(12)
                .idLoanType(1L)
                .build();
    }
    public static Petition invalidPetition() {
        return Petition.builder()
                .identityDocument("1234567890")
                .mount(BigDecimal.valueOf(15000.50))
                .loanTerm(12)
                .idLoanType(1L)
                .build();
    }
}
