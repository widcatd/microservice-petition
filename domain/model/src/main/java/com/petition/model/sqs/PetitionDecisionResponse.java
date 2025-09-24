package com.petition.model.sqs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PetitionDecisionResponse {
    private Long idPetition;
    private String statePayment;
    private String traceId;
}
