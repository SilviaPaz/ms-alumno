package com.scotiabank.prueba.handlers;

import com.scotiabank.prueba.dtos.AlumnoRequest;
import com.scotiabank.prueba.dtos.AlumnoResponse;
import com.scotiabank.prueba.services.AlumnosService;
import com.scotiabank.prueba.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlumnoHandler {

    private final AlumnosService alumnosService;

    private final ObjectValidator objectValidator;

    private static final Logger LOG = LoggerFactory.getLogger(AlumnoHandler.class);

    public Mono<ServerResponse> listAll(ServerRequest request) {
        Flux<AlumnoResponse> products = alumnosService.listarAlumnosActivos();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, AlumnoResponse.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<AlumnoRequest> clientRequest = request.bodyToMono(AlumnoRequest.class)
                .doOnNext(objectValidator::validate);
        return clientRequest.flatMap((alum -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(alumnosService.crearAlumno(alum
                        ), AlumnoResponse.class)
                )
        );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Mono<AlumnoRequest> clientRequest = request.bodyToMono(AlumnoRequest.class)
                .doOnNext(objectValidator::validate);
        return clientRequest.flatMap((alum -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(alumnosService.eliminarAlumno(alum
                        ), AlumnoResponse.class)
                )
        );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<AlumnoRequest> clientRequest = request.bodyToMono(AlumnoRequest.class)
                .doOnNext(objectValidator::validate);
        return clientRequest.flatMap((alum -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(alumnosService.modificarNombreAlumno(alum
                        ), AlumnoResponse.class)
                )
        );
    }
}