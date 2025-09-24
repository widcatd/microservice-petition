package com.petition.model.petition;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PetitionResponseCalculate {
    private String traceId;
    private String email;
    private BigDecimal salaryBase;
    private BigDecimal mount;
    private Integer loanTerm;
    private BigDecimal interestRate;
    private Long idPetition;
    private List<PetitionData> petitionDataList;
}
