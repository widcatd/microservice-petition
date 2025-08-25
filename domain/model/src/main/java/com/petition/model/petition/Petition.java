package com.petition.model.petition;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Petition {
    private Long idPetition;
    private Long document;
    private BigDecimal mount;
    private LocalDate loanTerm;
    private Long idLoanType;
}
