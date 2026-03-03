package com.scotiabank.prueba.mappers;

import com.scotiabank.prueba.dtos.AlumnoResponse;
import com.scotiabank.prueba.models.documents.Alumno;

public class AlumnoToAlumnoResponse {
    public static AlumnoResponse toAlumnoDto(Alumno alumno) {
        return AlumnoResponse.builder()
                .id(alumno.getIdalumno())
                .nombre(alumno.getNombre())
                .apellido(alumno.getApellido())
                .edad(alumno.getEdad())
                .estado(alumno.getEstado()).build();
    }
}
