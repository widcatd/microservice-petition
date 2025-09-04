package com.petition.r2dbc.repository.state;

import com.petition.model.loantype.LoanType;
import com.petition.model.state.State;
import com.petition.model.state.gateways.StateRepository;
import com.petition.r2dbc.entity.StateEntity;
import com.petition.r2dbc.helper.ReactiveAdapterOperations;
import com.petition.r2dbc.repository.loantype.LoanTypeReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;

public class StateReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        State/* change for domain model */,
        StateEntity/* change for adapter model */,
        Long,
        StateReactiveRepository
        > implements StateRepository {
    public StateReactiveRepositoryAdapter(StateReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, State.class/* change for domain model */));
    }
}
