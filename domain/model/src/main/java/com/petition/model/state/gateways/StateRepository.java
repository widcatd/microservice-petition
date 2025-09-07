package com.petition.model.state.gateways;

import com.petition.model.state.State;
import reactor.core.publisher.Mono;

public interface StateRepository {
    Mono<State> findByIdState(Long idState);
}
