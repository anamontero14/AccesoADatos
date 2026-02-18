package com.springboot.ejercicio.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlumnoDTO {

	@NotNull(message = "El nombre es requerido")
	@NotBlank(message = "El nombre no puede estar vacío")
	private String nombre;

	@NotNull(message = "El email es requerido")
	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "Formato de email inválido")
	private String email;

	private LocalDate fechaRegistro;

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}