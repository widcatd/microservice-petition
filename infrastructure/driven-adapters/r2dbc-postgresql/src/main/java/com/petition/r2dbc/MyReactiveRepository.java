package com.petition.r2dbc;

import com.petition.r2dbc.entity.PetitionEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MyReactiveRepository extends ReactiveCrudRepository<PetitionEntity, Long>, ReactiveQueryByExampleExecutor<PetitionEntity> {

}
