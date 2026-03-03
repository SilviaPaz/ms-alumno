package com.scotiabank.prueba.mappers;

import com.scotiabank.prueba.dtos.AlumnoRequest;
import com.scotiabank.prueba.models.documents.Alumno;

public class AlumnoRequestToAlumno {
    public static Alumno toAlumno(AlumnoRequest alumnoRequest) {
        return Alumno.builder()
                .idalumno(alumnoRequest.getId())
                .nombre(alumnoRequest.getNombre())
                .apellido(alumnoRequest.getApellido())
                .edad(alumnoRequest.getEdad())
                .estado(alumnoRequest.getEstado()).build();
    }
}
