package com.scotiabank.prueba.services;

import com.scotiabank.prueba.dtos.AlumnoResponse;
import com.scotiabank.prueba.dtos.AlumnoRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnosService {
    Mono<AlumnoResponse> crearAlumno(AlumnoRequest alumnoRequest);
    Flux<AlumnoResponse> listarAlumnosActivos();
    Mono<AlumnoResponse> eliminarAlumno(AlumnoRequest alumnoRequest);
    Mono<AlumnoResponse> modificarNombreAlumno(AlumnoRequest alumnoRequest);
}
