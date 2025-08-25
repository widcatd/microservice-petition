package com.petition.r2dbc.repository.loantype;

import com.petition.model.loantype.LoanType;
import com.petition.model.loantype.gateways.LoanTypeRepository;
import com.petition.r2dbc.entity.LoanTypeEntity;
import com.petition.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType/* change for domain model */,
        LoanTypeEntity/* change for adapter model */,
        Long,
        LoanTypeReactiveRepository
        > implements LoanTypeRepository {
    public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, LoanType.class/* change for domain model */));
    }

    @Override
    public Mono<LoanType> findByIdLoanType(Long idLoanType) {
        return repository.findByIdLoanType(idLoanType)
                .map(e -> mapper.map(e,LoanType.class));
    }
}
