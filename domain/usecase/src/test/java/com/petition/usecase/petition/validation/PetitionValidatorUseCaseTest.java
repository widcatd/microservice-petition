package com.petition.usecase.petition.validation;

import com.petition.model.exception.PetitionValidationException;
import com.petition.model.exceptionusecase.ExceptionUseCaseResponse;
import com.petition.model.petition.Petition;
import com.petition.usecase.petition.mock.PetitionMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PetitionValidatorUseCaseTest {

    @InjectMocks
    private PetitionValidatorUseCase validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Petition validada exitosamente")
    void shouldValidateSuccessfully() {
        Petition petition = PetitionMock.validPetition();

        Mono<Petition> result = validator.validate(petition);

        StepVerifier.create(result)
                .expectNext(petition)
                .verifyComplete();
    }

    @Test
    @DisplayName("Falla por identityDocument nulo")
    void shouldFailWhenIdentityDocumentIsNull() {
        Petition petition = PetitionMock.validPetition();
        petition.setIdentityDocument(null);

        Mono<Petition> result = validator.validate(petition);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assert error instanceof PetitionValidationException;
                    PetitionValidationException ex = (PetitionValidationException) error;
                    assert ex.getCode().equals(ExceptionUseCaseResponse.PETITION_IDENTITY_DOCUMENT_NULL.getCode());
                    assert ex.getMessage().equals(ExceptionUseCaseResponse.PETITION_IDENTITY_DOCUMENT_NULL.getMessage());
                })
                .verify();
    }

    @Test
    @DisplayName("Falla por monto nulo")
    void shouldFailWhenMountIsNull() {
        Petition petition = PetitionMock.validPetition();
        petition.setMount(null);

        Mono<Petition> result = validator.validate(petition);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assert error instanceof PetitionValidationException;
                    PetitionValidationException ex = (PetitionValidationException) error;
                    assert ex.getCode().equals(ExceptionUseCaseResponse.PETITION_MOUNT_NULL.getCode());
                    assert ex.getMessage().equals(ExceptionUseCaseResponse.PETITION_MOUNT_NULL.getMessage());
                })
                .verify();
    }

    @Test
    @DisplayName("Falla por loanTerm nulo")
    void shouldFailWhenLoanTermIsNull() {
        Petition petition = PetitionMock.validPetition();
        petition.setLoanTerm(null);

        Mono<Petition> result = validator.validate(petition);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assert error instanceof PetitionValidationException;
                    PetitionValidationException ex = (PetitionValidationException) error;
                    assert ex.getCode().equals(ExceptionUseCaseResponse.PETITION_LOAN_TERM_NULL.getCode());
                    assert ex.getMessage().equals(ExceptionUseCaseResponse.PETITION_LOAN_TERM_NULL.getMessage());
                })
                .verify();
    }

    @Test
    @DisplayName("Falla por idLoanType nulo")
    void shouldFailWhenIdLoanTypeIsNull() {
        Petition petition = PetitionMock.validPetition();
        petition.setIdLoanType(null);

        Mono<Petition> result = validator.validate(petition);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assert error instanceof PetitionValidationException;
                    PetitionValidationException ex = (PetitionValidationException) error;
                    assert ex.getCode().equals(ExceptionUseCaseResponse.PETITION_ID_LOAN_TYPE_NULL.getCode());
                    assert ex.getMessage().equals(ExceptionUseCaseResponse.PETITION_ID_LOAN_TYPE_NULL.getMessage());
                })
                .verify();
    }
}