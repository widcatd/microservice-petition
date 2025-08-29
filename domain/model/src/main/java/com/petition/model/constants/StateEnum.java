package com.petition.model.constants;

import lombok.Getter;

@Getter
public enum StateEnum {

    PENDING_REVIEW("PENDIENTE DE REVISION"),
    ANUAL_REVISION("REVISION MANUAL"),
    REJECTED("RECHAZADAS");

    private final String stateName;

    StateEnum(String stateName) {
        this.stateName = stateName;
    }
}
