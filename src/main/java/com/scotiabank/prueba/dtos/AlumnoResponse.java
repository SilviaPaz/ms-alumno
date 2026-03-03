package com.scotiabank.prueba.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoResponse implements Serializable {
    @JsonProperty("idalumno")
    private Integer id;
    private String nombre;
    private String apellido;
    private Boolean estado;
    private Integer edad;
}
