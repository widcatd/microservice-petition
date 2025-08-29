package com.petition.usecase.petition;

import com.petition.model.petition.Petition;
import com.petition.model.petition.gateways.PetitionRepository;
import com.petition.usecase.petition.mock.PetitionMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class PetitionUseCaseTest {
    private static final String traceId= "74654859-d75f-448e-9b5e-a6ddee9f5278";

    @Mock
    private PetitionRepository petitionRepository;

    @InjectMocks
    private PetitionUseCase petitionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Se guarda la petition exitosamente")
    void shouldSavePetitionSuccessfully() {
        Petition petition = PetitionMock.validPetition();
        when(petitionRepository.savePetition(petition,traceId)).thenReturn(Mono.just(petition));

        Mono<Void> result = petitionUseCase.savePetition(petition,traceId);


        StepVerifier.create(result)
                .verifyComplete();
        verify(petitionRepository, times(1)).savePetition(petition, traceId);
    }

}