package com.petition.api.dto;

import lombok.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoanTypeDto {
    private Long idLoanType;
    private String name;
    private BigDecimal minMount;
    private BigDecimal maxMount;
    private BigDecimal interestRate;
    private Boolean automaticValidation;
}
