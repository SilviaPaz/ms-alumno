

import com.scotiabank.prueba.dtos.AlumnoRequest;
import com.scotiabank.prueba.dtos.AlumnoResponse;
import com.scotiabank.prueba.handlers.AlumnoHandler;
import com.scotiabank.prueba.services.AlumnosService;
import com.scotiabank.prueba.validation.ObjectValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AlumnoHandlerTest {

    private final AlumnosService alumnosService = Mockito.mock(AlumnosService.class);
    private final ObjectValidator objectValidator = Mockito.mock(ObjectValidator.class);

    private final AlumnoHandler handler = new AlumnoHandler(alumnosService, objectValidator);

    @Test
    void testListAll() {
        AlumnoResponse alumno = new AlumnoResponse(1, "Juan", "Pérez", true, 20);

        when(alumnosService.listarAlumnosActivos()).thenReturn(Flux.just(alumno));

        ServerRequest request = Mockito.mock(ServerRequest.class);

        Mono<ServerResponse> response = handler.listAll(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().equals(HttpStatus.OK) &&
                                serverResponse.headers().getContentType().equals(MediaType.APPLICATION_JSON))
                .verifyComplete();
    }

    @Test
    void testCreate() {
        AlumnoRequest requestBody = new AlumnoRequest(0, "María", "García", true, 22);
        AlumnoResponse responseBody = new AlumnoResponse(1, "María", "García", true, 22);

        when(alumnosService.crearAlumno(any(AlumnoRequest.class))).thenReturn(Mono.just(responseBody));

        ServerRequest request = Mockito.mock(ServerRequest.class);
        when(request.bodyToMono(AlumnoRequest.class)).thenReturn(Mono.just(requestBody));

        Mono<ServerResponse> response = handler.create(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().equals(HttpStatus.CREATED) &&
                                serverResponse.headers().getContentType().equals(MediaType.APPLICATION_JSON))
                .verifyComplete();
    }
}
