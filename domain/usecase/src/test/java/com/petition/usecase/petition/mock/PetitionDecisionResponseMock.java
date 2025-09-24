package com.petition.usecase.petition.mock;

import com.petition.model.sqs.PetitionDecisionResponse;

public class PetitionDecisionResponseMock {
    public static PetitionDecisionResponse approved(String traceId) {
        return PetitionDecisionResponse.builder()
                .traceId(traceId)
                .statePayment("APROBADO")
                .build();
    }
}
