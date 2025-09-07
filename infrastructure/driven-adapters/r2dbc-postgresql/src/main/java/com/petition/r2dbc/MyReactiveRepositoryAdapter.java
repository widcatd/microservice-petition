package com.petition.r2dbc;

import com.petition.model.constants.Constants;
import com.petition.model.constants.StateEnum;
import com.petition.model.exception.RegisterNotFoundException;
import com.petition.model.exceptionusecase.ExceptionUseCaseResponse;
import com.petition.model.petition.PageRequest;
import com.petition.model.petition.Petition;
import com.petition.model.petition.gateways.PetitionRepository;
import com.petition.r2dbc.entity.PetitionEntity;
import com.petition.r2dbc.helper.ReactiveAdapterOperations;
import com.petition.r2dbc.repository.loantype.LoanTypeReactiveRepository;
import com.petition.r2dbc.repository.state.StateReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
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
    public Mono<Petition> savePetition(Petition petition, String traceId) {
        PetitionEntity petitionEntity = mapper.map(petition, PetitionEntity.class);
        log.info(Constants.LOG_REPO_START_SAVE, petition.getIdLoanType(), traceId);
        return loanTypeReactiveRepository.findByIdLoanType(petition.getIdLoanType())
                .doOnNext(loanTypeEntity -> log.info(Constants.LOG_REPO_LOAN_TYPE_FOUND, loanTypeEntity.getIdLoanType(), traceId))
                .switchIfEmpty(Mono.error(new RegisterNotFoundException(
                        ExceptionUseCaseResponse.ID_LOAN_TYPE_NOT_FOUND.getCode(),
                        String.format(ExceptionUseCaseResponse.ID_LOAN_TYPE_NOT_FOUND.getMessage(), petition.getIdLoanType())
                )))
                .flatMap(loanTypeEntity -> {
                    petitionEntity.setIdLoanType(loanTypeEntity.getIdLoanType());
                    if(petitionEntity.getIdState() != null) {
                        log.info(Constants.LOG_REPO_STATE_PROVIDED, petitionEntity.getIdState(), traceId);
                        return stateReactiveRepository.findByIdState(petitionEntity.getIdState())
                                .switchIfEmpty(Mono.error(new RegisterNotFoundException(
                                        ExceptionUseCaseResponse.STATE_REGISTER_NOT_FOUND.getCode(),
                                        String.format(ExceptionUseCaseResponse.STATE_REGISTER_NOT_FOUND.getMessage())
                                )))

                                .flatMap(stateEntity -> {
                                    petitionEntity.setIdState(stateEntity.getIdState());
                                    log.info(Constants.LOG_REPO_SAVING_WITH_STATE, stateEntity.getIdState(), traceId);
                                    return repository.save(petitionEntity);
                                });
                    }
                    return stateReactiveRepository.findByName(StateEnum.PENDING_REVIEW.getStateName())
                            .switchIfEmpty(Mono.error(new RegisterNotFoundException(
                                    ExceptionUseCaseResponse.STATE_REGISTER_NOT_FOUND.getCode(),
                                    String.format(ExceptionUseCaseResponse.STATE_REGISTER_NOT_FOUND.getMessage())
                            )))
                            .flatMap(stateEntity -> {
                                petitionEntity.setIdState(stateEntity.getIdState());
                                log.info(Constants.LOG_REPO_SAVING_WITH_STATE, stateEntity.getIdState(), traceId);
                                return repository.save(petitionEntity);
                            });
                })
                .doOnSuccess(saved ->
                        log.info(Constants.LOG_REPO_SAVED_SUCCESS, saved.getIdPetition(), traceId))
                .doOnError(error ->
                        log.error(Constants.LOG_REPO_ERROR_SAVING, error.getMessage(), traceId, error))
                .map(e -> mapper.map(e, Petition.class));
    }

    @Override
    public Flux<Petition> findByIdState(Long stateId, PageRequest pageRequest, String traceId) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize()
        );
        log.info(Constants.LOG_REPO_START_SEARCH, stateId, pageRequest.getPage(), pageRequest.getSize(), traceId);
        return repository.findByIdState(stateId, pageable)
                .doOnComplete(() -> log.info(Constants.LOG_REPO_SEARCH_COMPLETED, traceId))
                .doOnError(error -> log.error(Constants.LOG_REPO_SEARCH_ERROR, error.getMessage(), traceId, error));
    }
}
