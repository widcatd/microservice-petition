package com.petition.r2dbc;

import com.petition.model.petition.Petition;
import com.petition.model.petition.PetitionData;
import com.petition.model.petition.PetitionResponseCalculate;
import com.petition.r2dbc.entity.PetitionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MyReactiveRepository extends ReactiveCrudRepository<PetitionEntity, Long>, ReactiveQueryByExampleExecutor<PetitionEntity> {

    Flux<PetitionEntity> findByIdState(Long idState, Pageable pageable);

    Mono<PetitionEntity> findByIdPetition(Long idPetition);
    @Query("""
        SELECT p.* FROM solicitud p
        INNER JOIN estado e ON p.id_estado = e.id_estado
        WHERE p.email = :email AND e.nombre = 'APROBADO'
    """)
    Flux<PetitionEntity> findApprovedPetitionsByEmail(String email);
}
