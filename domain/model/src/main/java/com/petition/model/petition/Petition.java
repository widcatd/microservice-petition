package com.petition.model.petition;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Petition {
    private Long idPetition;
    private BigDecimal mount;
    private Integer loanTerm;
    private String email;
    private Long idState;
    private Long idLoanType;
}
