package com.scotiabank.prueba.models.documents.repository;

import com.scotiabank.prueba.models.documents.Alumno;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AlumnoRepository extends R2dbcRepository<Alumno, Integer> {

    Flux<Alumno> findByEstado(Boolean estado);

    Mono<Alumno> findByIdalumno(Integer idalumno);
}