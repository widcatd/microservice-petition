package com.petition.model.constants;

import lombok.Getter;

@Getter
public enum StateEnum {

    PENDING_REVIEW("PENDIENTE DE REVISION"),
    APPROVED("APROBADO"),
    REJECTED("RECHAZADO");

    private final String stateName;

    StateEnum(String stateName) {
        this.stateName = stateName;
    }
}
