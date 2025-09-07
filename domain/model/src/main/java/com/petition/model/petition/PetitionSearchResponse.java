package com.petition.model.petition;

import com.petition.model.loantype.LoanType;
import com.petition.model.state.State;
import com.petition.model.webclient.User;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PetitionSearchResponse {
    private BigDecimal amount;
    private Integer term;
    private String email;
    private String loanType;
    private BigDecimal interestRate;
    private String petitionState;
    private BigDecimal baseSalary;

    public static PetitionSearchResponse toResponse(Petition petition, LoanType loanType, State state, User user) {
        return PetitionSearchResponse.builder()
                .amount(petition.getMount())
                .term(petition.getLoanTerm())
                .email(petition.getEmail())
                .interestRate(loanType.getInterestRate())
                .loanType(loanType.getName())
                .petitionState(state.getName())
                .baseSalary(user.getSalaryBase())
                .build();
    }
}
