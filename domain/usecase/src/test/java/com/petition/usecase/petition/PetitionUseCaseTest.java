package com.petition.usecase.petition;

import com.petition.model.auth.IJwtProvider;
import com.petition.model.exception.DataNotFoundException;
import com.petition.model.exception.IdentityDocumentNotFoundException;
import com.petition.model.exception.RegisterNotFoundException;
import com.petition.model.loantype.LoanType;
import com.petition.model.loantype.gateways.LoanTypeRepository;
import com.petition.model.petition.Petition;
import com.petition.model.petition.PageRequest;
import com.petition.model.petition.PetitionSearchResponse;
import com.petition.model.petition.gateways.PetitionRepository;
import com.petition.model.state.State;
import com.petition.model.state.gateways.AuthClient;
import com.petition.model.state.gateways.StateRepository;
import com.petition.model.webclient.User;
import com.petition.usecase.petition.mock.PetitionMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class PetitionUseCaseTest {
    private static final String TRACE_ID = "74654859-d75f-448e-9b5e-a6ddee9f5278";
    private static final String AUTH_HEADER = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";
    private static final String EMAIL = "test@mail.com";

    @Mock
    private PetitionRepository petitionRepository;
    @Mock
    private LoanTypeRepository loanTypeRepository;
    @Mock
    private StateRepository stateRepository;
    @Mock
    private IJwtProvider jwtProvider;
    @Mock
    private AuthClient authClient;

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
        User user = User.builder().email(EMAIL).build();
        when(authClient.findByDocument(petition.getIdentityDocument(), AUTH_HEADER, TRACE_ID))
                .thenReturn(Mono.just(user));
        when(jwtProvider.getSubject()).thenReturn(Mono.just(EMAIL));
        when(petitionRepository.savePetition(petition, TRACE_ID)).thenReturn(Mono.just(petition));

        Mono<Void> result = petitionUseCase.savePetition(petition, TRACE_ID, AUTH_HEADER);

        StepVerifier.create(result)
                .verifyComplete();
        verify(petitionRepository, times(1)).savePetition(petition, TRACE_ID);
    }
    @Test
    @DisplayName("documento no encontrado")
    void shouldThrowIdentityDocumentNotFound() {
        Petition petition = PetitionMock.validPetition();

        when(authClient.findByDocument(petition.getIdentityDocument(), AUTH_HEADER, TRACE_ID))
                .thenReturn(Mono.empty());

        Mono<Void> result = petitionUseCase.savePetition(petition, TRACE_ID, AUTH_HEADER);

        StepVerifier.create(result)
                .expectError(IdentityDocumentNotFoundException.class)
                .verify();
    }
    @Test
    @DisplayName("stateId es null")
    void shouldWhenStateIdIsNull() {

        Flux<PetitionSearchResponse> result = petitionUseCase.findBySearch(null, new PageRequest(0, 10), TRACE_ID, AUTH_HEADER);

        StepVerifier.create(result)
                .expectError(DataNotFoundException.class)
                .verify();
    }
    @Test
    @DisplayName("findBySearch no hay registros")
    void shouldThrowWhenNoRegistersFound() {
        Long stateId = 1L;
        var pageRequest = PageRequest.builder()
                .page(0)
                .size(10)
                .build();

        when(petitionRepository.findByIdState(stateId, pageRequest, TRACE_ID))
                .thenReturn(Flux.empty());

        Flux<PetitionSearchResponse> result = petitionUseCase.findBySearch(stateId, pageRequest, TRACE_ID, AUTH_HEADER);

        StepVerifier.create(result)
                .expectError(RegisterNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("findBySearch - retorna resultados correctamente")
    void shouldReturnSearchResultsSuccessfully() {
        Long stateId = 1L;
        Petition petition = PetitionMock.validPetition();
        var pageRequest = PageRequest.builder()
                .page(0)
                .size(10)
                .build();

        when(petitionRepository.findByIdState(stateId, pageRequest, TRACE_ID))
                .thenReturn(Flux.just(petition));
        when(loanTypeRepository.findByIdLoanType(petition.getIdLoanType()))
                .thenReturn(Mono.just(
                        LoanType.builder()
                                .name("Hipotecario")
                                .interestRate(BigDecimal.valueOf(10.55))
                                .build()
                ));
        when(stateRepository.findByIdState(petition.getIdState()))
                .thenReturn(Mono.just(
                        State.builder()
                                .name("Aprobado")
                                .build()
                ));
        when(authClient.findByEmail(petition.getEmail(), AUTH_HEADER, TRACE_ID))
                .thenReturn(Mono.just(
                        User.builder()
                                .email("test@email.com")
                                .salaryBase(BigDecimal.valueOf(2500.0))
                                .build()
                ));

        Flux<PetitionSearchResponse> result =
                petitionUseCase.findBySearch(stateId, pageRequest, TRACE_ID, AUTH_HEADER);

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getLoanType().equals("Hipotecario")
                                && response.getPetitionState().equals("Aprobado")
                                && response.getBaseSalary().compareTo(BigDecimal.valueOf(2500.0)) == 0
                )
                .verifyComplete();

        verify(petitionRepository).findByIdState(stateId, pageRequest, TRACE_ID);
        verify(loanTypeRepository).findByIdLoanType(petition.getIdLoanType());
        verify(stateRepository).findByIdState(petition.getIdState());
        verify(authClient).findByEmail(petition.getEmail(), AUTH_HEADER, TRACE_ID);
    }


}