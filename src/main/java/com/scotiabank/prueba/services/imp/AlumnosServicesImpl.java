package com.scotiabank.prueba.services.imp;

import com.scotiabank.prueba.dtos.AlumnoResponse;
import com.scotiabank.prueba.dtos.AlumnoRequest;
import com.scotiabank.prueba.exceptions.CustomException;
import com.scotiabank.prueba.mappers.AlumnoRequestToAlumno;
import com.scotiabank.prueba.mappers.AlumnoToAlumnoResponse;
import com.scotiabank.prueba.models.documents.repository.AlumnoRepository;
import com.scotiabank.prueba.services.AlumnosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AlumnosServicesImpl implements AlumnosService {

    private final AlumnoRepository alumnosRepository;

    private static final String  NF_MESSAGE = "No se encuentra el alumno";

    @Override
    public Mono<AlumnoResponse> crearAlumno(AlumnoRequest alumnoRequest) {
        Mono<Boolean> alumnoExist = alumnosRepository.findByIdalumno(alumnoRequest.getId()).hasElement();

        return alumnoExist.flatMap(
                exist -> exist ?
                        Mono.error(new CustomException(HttpStatus.BAD_REQUEST,
                                "Id ya existe"))
                        : alumnosRepository.save(AlumnoRequestToAlumno.toAlumno(alumnoRequest))
                        .flatMap(t -> Mono.empty())
                        //.map(AlumnoToAlumnoDto::toAlumnoDto)
        );
    }

    @Override
    public Flux<AlumnoResponse> listarAlumnosActivos() {
        return alumnosRepository.findByEstado(true)
                .switchIfEmpty(Flux.error(
                        new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)))
                .map(al -> AlumnoResponse.builder()
                        .id(al.getIdalumno())
                        .nombre(al.getNombre())
                        .apellido(al.getApellido())
                        .edad(al.getEdad())
                        .estado(al.getEstado())
                        .build()
                );
    }

    @Override
    public Mono<AlumnoResponse> eliminarAlumno(AlumnoRequest alumnoRequest) {
        Mono<Boolean> alumnoExist = alumnosRepository.findByIdalumno(alumnoRequest.getId()).hasElement();

        return alumnoExist.flatMap(
                exist -> !exist ?
                        Mono.error(new CustomException(HttpStatus.BAD_REQUEST,
                                "Id no existe"))
                        : alumnosRepository.deleteById(AlumnoRequestToAlumno.toAlumno(alumnoRequest).getIdalumno())
                        .flatMap(t -> Mono.empty())
                //.map(AlumnoToAlumnoDto::toAlumnoDto)
        );
    }

    @Override
    public Mono<AlumnoResponse> modificarNombreAlumno(AlumnoRequest alumnoRequest) {
        Mono<Boolean> alumnoExist = alumnosRepository.findByIdalumno(alumnoRequest.getId()).hasElement();

        return alumnoExist.flatMap(
                exist -> !exist ?
                        Mono.error(new CustomException(HttpStatus.BAD_REQUEST,
                                "Id no existe"))
                        : alumnosRepository.findByIdalumno(alumnoRequest.getId())
                                .flatMap(al ->
                                        {al.setNombre(alumnoRequest.getNombre());
                                         al.setApellido(alumnoRequest.getApellido());
                                        al.setEdad((alumnoRequest.getEdad()));
                                        return alumnosRepository.save(al);})
                        .map(AlumnoToAlumnoResponse::toAlumnoDto)

                        //.map(AlumnoToAlumnoResponse::toAlumnoDto)
                //        .flatMap(t -> Mono.empty())

        );
    }
}
