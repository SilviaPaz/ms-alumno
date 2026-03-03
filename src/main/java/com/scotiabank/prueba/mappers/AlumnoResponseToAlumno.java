package com.scotiabank.prueba.mappers;

import com.scotiabank.prueba.dtos.AlumnoResponse;
import com.scotiabank.prueba.models.documents.Alumno;

public class AlumnoResponseToAlumno {
    public static Alumno toAlumno(AlumnoResponse alumnoResponse) {
        return Alumno.builder()
                .id(alumnoResponse.getId())
                .nombre(alumnoResponse.getNombre())
                .apellido(alumnoResponse.getApellido())
                .edad(alumnoResponse.getEdad())
                .estado(alumnoResponse.getEstado()).build();
    }
}
