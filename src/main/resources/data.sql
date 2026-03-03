DROP TABLE IF EXISTS alumnos;

CREATE TABLE alumnos (
     id INT PRIMARY KEY AUTO_INCREMENT,
     idalumno INT,
     nombre VARCHAR(255),
     apellido VARCHAR(255),
     estado BOOLEAN,
     edad INT
);