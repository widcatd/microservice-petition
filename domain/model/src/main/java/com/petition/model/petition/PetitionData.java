package com.petition.model.petition;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PetitionData {
    private Long idPetition;
    private BigDecimal mount;
    private Integer loanTerm;
    private String email;
    private Long idState;
    private Long idLoanType;
    private BigDecimal interestRate;
    private BigDecimal salaryBase;
}
