package com.petition.model.sqs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {
    private String email;
    private String message;
    private String subject;
    private Integer loanTerm;
    private BigDecimal mount;
    private BigDecimal interestRate;
    private String state;
}
