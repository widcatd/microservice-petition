package com.petition.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "estado")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StateEntity {
    @Id
    @Column("id_estado")
    private Long idState;
    @Column("nombre")
    private String name;
    @Column("descripcion")
    private String description;
}
