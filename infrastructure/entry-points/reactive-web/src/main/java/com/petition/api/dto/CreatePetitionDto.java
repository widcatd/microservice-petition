package com.petition.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Documento de identidad del usuario", example = "1234567890")
    private String identityDocument;

    @Schema(description = "Monto solicitado en el préstamo", example = "15000.50")
    private BigDecimal mount;

    @Schema(description = "Plazo del préstamo en meses", example = "24")
    private Integer loanTerm;

    @Schema(description = "Identificador del tipo de préstamo", example = "1")
    private Long idLoanType;

    @Schema(description = "Correo electrónico del usuario",
            example = "usuario@correo.com")
    private String email;
    @Schema(description = "Identificador de estado",
            example = "1")
    private Long idState;
}
