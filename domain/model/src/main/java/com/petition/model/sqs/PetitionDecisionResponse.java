package com.petition.model.sqs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetitionDecisionResponse {
    private Long idPetition;
    private String statePayment;
    private String traceId;
}
