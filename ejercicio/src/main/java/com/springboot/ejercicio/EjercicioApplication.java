package com.springboot.ejercicio;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API Alumnos",
        version = "1.0",
        description = "API REST para gestión de alumnos"
    )
)
@SpringBootApplication
public class EjercicioApplication {
    public static void main(String[] args) {
        SpringApplication.run(EjercicioApplication.class, args);
    }
}