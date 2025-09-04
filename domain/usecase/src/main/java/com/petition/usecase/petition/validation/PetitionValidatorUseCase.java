package com.petition.usecase.petition.validation;

import com.petition.model.exception.PetitionValidationException;
import com.petition.model.exceptionusecase.ExceptionUseCaseResponse;
import com.petition.model.petition.Petition;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PetitionValidatorUseCase {

    public Mono<Petition> validate(Petition petition) {
        return Mono.just(petition)

                .filter(p -> p.getIdentityDocument() != null)
                .switchIfEmpty(Mono.error(new PetitionValidationException(
                        ExceptionUseCaseResponse.PETITION_IDENTITY_DOCUMENT_NULL.getCode(),
                        ExceptionUseCaseResponse.PETITION_IDENTITY_DOCUMENT_NULL.getMessage()
                )))

                .filter(p -> p.getMount() != null)
                .switchIfEmpty(Mono.error(new PetitionValidationException(
                        ExceptionUseCaseResponse.PETITION_MOUNT_NULL.getCode(),
                        ExceptionUseCaseResponse.PETITION_MOUNT_NULL.getMessage()
                )))

                .filter(p -> p.getLoanTerm() != null)
                .switchIfEmpty(Mono.error(new PetitionValidationException(
                        ExceptionUseCaseResponse.PETITION_LOAN_TERM_NULL.getCode(),
                        ExceptionUseCaseResponse.PETITION_LOAN_TERM_NULL.getMessage()
                )))

                .filter(p -> p.getIdLoanType() != null)
                .switchIfEmpty(Mono.error(new PetitionValidationException(
                        ExceptionUseCaseResponse.PETITION_ID_LOAN_TYPE_NULL.getCode(),
                        ExceptionUseCaseResponse.PETITION_ID_LOAN_TYPE_NULL.getMessage()
                )));
    }
}
