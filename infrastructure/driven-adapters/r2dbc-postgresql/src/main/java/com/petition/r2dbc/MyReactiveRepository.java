package com.petition.r2dbc;

import com.petition.model.petition.Petition;
import com.petition.model.petition.PetitionSearchResponse;
import com.petition.r2dbc.entity.PetitionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MyReactiveRepository extends ReactiveCrudRepository<PetitionEntity, Long>, ReactiveQueryByExampleExecutor<PetitionEntity> {

    @Query("""
        SELECT s.monto         AS amount,
                   s.plazo         AS term,
                   s.email         AS email,
                   tp.nombre       AS loan_type,
                   tp.tasa_interes AS interest_rate,
                   e.nombre        AS petition_state
        FROM solicitud s
        JOIN tipo_prestamo tp ON s.id_tipo_prestamo = tp.id_tipo_prestamo
        JOIN estado e ON s.id_estado = e.id_estado
        WHERE (:email IS NULL OR LOWER(s.email) = LOWER(:email))
        LIMIT :size OFFSET :skip
    """)
    Flux<PetitionSearchResponse> searchPetitions(
            @Param("email") String email,
            @Param("size") Integer size,
            @Param("skip") Integer skip
    );

    Flux<Petition> findByIdState(Long idState, Pageable pageable);
}
