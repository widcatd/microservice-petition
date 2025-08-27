package com.petition.r2dbc;

import com.petition.model.exception.RegisterNotFoundException;
import com.petition.model.exceptionusecase.ExceptionUseCaseResponse;
import com.petition.model.petition.Petition;
import com.petition.model.petition.gateways.PetitionRepository;
import com.petition.r2dbc.entity.PetitionEntity;
import com.petition.r2dbc.helper.ReactiveAdapterOperations;
import com.petition.r2dbc.repository.loantype.LoanTypeReactiveRepository;
import com.petition.r2dbc.repository.state.StateReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Petition/* change for domain model */,
        PetitionEntity/* change for adapter model */,
    Long,
    MyReactiveRepository
> implements PetitionRepository {
    private final LoanTypeReactiveRepository loanTypeReactiveRepository;
    private final StateReactiveRepository stateReactiveRepository;
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper, LoanTypeReactiveRepository loanTypeReactiveRepository, StateReactiveRepository stateReactiveRepository) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Petition.class/* change for domain model */));
        this.loanTypeReactiveRepository = loanTypeReactiveRepository;
        this.stateReactiveRepository= stateReactiveRepository;
    }

    @Override
    public Mono<Petition> savePetition(Petition petition) {
        PetitionEntity petitionEntity = mapper.map(petition, PetitionEntity.class);
        return loanTypeReactiveRepository.findByIdLoanType(petition.getIdLoanType())
                .switchIfEmpty(Mono.error(new RegisterNotFoundException(
                        ExceptionUseCaseResponse.ID_LOAN_TYPE_NOT_FOUND.getCode(),
                        String.format(ExceptionUseCaseResponse.ID_LOAN_TYPE_NOT_FOUND.getMessage(), petition.getIdLoanType())
                )))
                .flatMap(loanTypeEntity -> {
                    petitionEntity.setIdLoanType(loanTypeEntity.getIdLoanType());

                    return stateReactiveRepository.findByName("PENDIENTE DE REVISION")
                            .switchIfEmpty(Mono.error(new RegisterNotFoundException(
                                    ExceptionUseCaseResponse.STATE_REGISTER_NOT_FOUND.getCode(),
                                    String.format(ExceptionUseCaseResponse.STATE_REGISTER_NOT_FOUND.getMessage())
                            )))
                            .flatMap(stateEntity -> {
                                petitionEntity.setIdState(stateEntity.getIdState());
                                return repository.save(petitionEntity);
                            });
                })
                .map(e -> mapper.map(e, Petition.class));
    }
}
