package com.petition.r2dbc;

import com.petition.model.petition.Petition;
import com.petition.model.petition.gateways.PetitionRepository;
import com.petition.r2dbc.entity.PetitionEntity;
import com.petition.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Petition/* change for domain model */,
        PetitionEntity/* change for adapter model */,
    Long,
    MyReactiveRepository
> implements PetitionRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Petition.class/* change for domain model */));
    }

    @Override
    public Mono<Petition> savePetition(Petition petition) {
        return Mono.just(petition)
                .map(p -> mapper.map(p, PetitionEntity.class))
                .flatMap(repository::save)
                .map(e -> mapper.map(e, Petition.class));
    }
}
