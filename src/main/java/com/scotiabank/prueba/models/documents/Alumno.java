package com.scotiabank.prueba.models.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("ALUMNOS")
public class Alumno implements Serializable {
    @Id
    private Integer id;

    private Integer idalumno;
    private String nombre;
    private String apellido;
    private Boolean estado;
    private Integer edad;
}
