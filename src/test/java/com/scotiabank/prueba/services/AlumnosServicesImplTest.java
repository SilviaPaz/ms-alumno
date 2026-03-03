package com.scotiabank.prueba.services;

import com.scotiabank.prueba.dtos.AlumnoRequest;
import com.scotiabank.prueba.exceptions.CustomException;
import com.scotiabank.prueba.models.documents.Alumno;
import com.scotiabank.prueba.models.documents.repository.AlumnoRepository;
import com.scotiabank.prueba.services.imp.AlumnosServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

public class AlumnosServicesImplTest {

    private AlumnoRepository alumnoRepository;
    private AlumnosServicesImpl alumnosService;

    @BeforeEach
    void setUp() {
        alumnoRepository = Mockito.mock(AlumnoRepository.class);
        alumnosService = new AlumnosServicesImpl(alumnoRepository);
    }

    @Test
    void testCrearAlumno_IdYaExiste() {
        AlumnoRequest request = new AlumnoRequest(1, "Juan", "Pérez", true, 20);

        when(alumnoRepository.findByIdalumno(request.getId())).thenReturn(Mono.just(new Alumno()));

        StepVerifier.create(alumnosService.crearAlumno(request))
                .expectErrorMatches(error ->
                        error instanceof CustomException &&
                                ((CustomException) error).getStatus().equals(HttpStatus.BAD_REQUEST))
                .verify();
    }

    @Test
    void testCrearAlumno_NuevoAlumno() {
        AlumnoRequest request = new AlumnoRequest(2, "María", "García", true, 22);
        Alumno alumnoEntity = new Alumno(1, 2, "María", "García", true, 22);

        when(alumnoRepository.findByIdalumno(request.getId())).thenReturn(Mono.empty());
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(Mono.just(alumnoEntity));

        StepVerifier.create(alumnosService.crearAlumno(request))
                .verifyComplete();
    }

    @Test
    void testListarAlumnosActivos_ConResultados() {
        Alumno alumnoEntity = new Alumno(2, 1, "Juan", "Pérez", true, 20);

        when(alumnoRepository.findByEstado(true)).thenReturn(Flux.just(alumnoEntity));

        StepVerifier.create(alumnosService.listarAlumnosActivos())
                .expectNextMatches(response ->
                        response.getId().equals(1) &&
                                response.getNombre().equals("Juan"))
                .verifyComplete();
    }

    @Test
    void testListarAlumnosActivos_SinResultados() {
        when(alumnoRepository.findByEstado(true)).thenReturn(Flux.empty());

        StepVerifier.create(alumnosService.listarAlumnosActivos())
                .expectErrorMatches(error ->
                        error instanceof CustomException &&
                                ((CustomException) error).getStatus().equals(HttpStatus.NOT_FOUND))
                .verify();
    }
}
