package com.scotiabank.prueba.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AlumnoRequest implements Serializable {
    @NotNull
    @JsonProperty("idalumno")
    private int id;
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{1,255}$", message = "Formato incorrecto")
    private String nombre;
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{1,255}$", message = "Formato incorrecto")
    private String apellido;
    private Boolean estado;
    @NotNull
    private Integer edad;
}
