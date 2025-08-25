package com.petition.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreatePetitionDto {
    @NotNull(message = "Se debe agregar el documento de identidad")
    private String identityDocument;
    @NotNull(message = "Se debe agregar el monto")
    private BigDecimal mount;
    @NotNull(message = "Se debe agregar el plazo")
    private LocalDate loanTerm;
    @NotNull(message = "Se debe agregar el id del tipo de prestamo")
    private Long idLoanType;
    //private LoanTypeDto loanTypeDto;
}
