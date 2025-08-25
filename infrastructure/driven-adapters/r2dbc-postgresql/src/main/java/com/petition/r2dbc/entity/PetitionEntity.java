package com.petition.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "solicitud")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PetitionEntity {
    @Id
    @Column("id_solicitud")
    private Long idPetition;
    @Column("monto")
    private BigDecimal mount;
    @Column("plazo")
    private LocalDate loanTerm;
    @Column("email")
    private String email;
    @Transient
    private Long idState;
    @Transient
    private Long idLoanType;


}
