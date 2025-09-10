package com.petition.r2dbc.repository.state;

import com.petition.model.state.State;
import com.petition.model.state.gateways.StateRepository;
import com.petition.r2dbc.entity.StateEntity;
import com.petition.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
@Transactional
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

    @Override
    public Mono<State> findByIdState(Long idState) {
        return repository.findByIdState(idState)
                .map(state -> mapper.map(state, State.class));
    }
}
