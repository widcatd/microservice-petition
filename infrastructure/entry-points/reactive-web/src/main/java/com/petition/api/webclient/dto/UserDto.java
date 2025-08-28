package com.petition.api.webclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long idUser;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastName;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-05-20")
    private LocalDate dateBirthday;

    @Schema(description = "Dirección del usuario", example = "Av. Siempre Viva 123")
    private String address;

    @Schema(description = "Número de teléfono del usuario", example = "987654321")
    private String phone;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@mail.com")
    private String email;

    @Schema(description = "Salario base del usuario", example = "3500.50")
    private BigDecimal salaryBase;

    @Schema(description = "Documento de identidad del usuario", example = "1234567890")
    private String identityDocument;
}
