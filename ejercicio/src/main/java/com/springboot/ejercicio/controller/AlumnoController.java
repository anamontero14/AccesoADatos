package com.springboot.ejercicio.controller;

import com.springboot.ejercicio.dto.AlumnoDTO;
import com.springboot.ejercicio.entities.Alumno;
import com.springboot.ejercicio.service.AlumnoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
@Tag(name = "Alumnos", description = "Operaciones CRUD de alumnos")
public class AlumnoController {

	@Autowired
	private AlumnoService service;

	@Operation(summary = "Listar todos los alumnos")
	@ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
	@GetMapping
	public List<Alumno> listar() {
		return service.listarTodos();
	}

	@Operation(summary = "Buscar alumno por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Alumno encontrado"),
			@ApiResponse(responseCode = "404", description = "Alumno no encontrado") })
	@GetMapping("/{id}")
	public ResponseEntity<Alumno> buscarPorId(@PathVariable Long id) {
		Optional<Alumno> alumno = service.buscarPorId(id);
		if (alumno.isPresent()) {
			return ResponseEntity.ok(alumno.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Crear un nuevo alumno")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Alumno creado correctamente"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos") })
	@PostMapping
	public ResponseEntity<Alumno> crear(@Valid @RequestBody AlumnoDTO dto) {
		Alumno alumno = new Alumno();
		alumno.setNombre(dto.getNombre());
		alumno.setEmail(dto.getEmail());
		alumno.setFechaRegistro(dto.getFechaRegistro());
		Alumno guardado = service.guardar(alumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
	}

	@Operation(summary = "Actualizar un alumno existente")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Alumno actualizado correctamente"),
			@ApiResponse(responseCode = "404", description = "Alumno no encontrado") })
	@PutMapping("/{id}")
	public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @Valid @RequestBody AlumnoDTO dto) {
		Optional<Alumno> alumnoExistente = service.buscarPorId(id);
		if (alumnoExistente.isPresent()) {
			Alumno alumno = alumnoExistente.get();
			alumno.setNombre(dto.getNombre());
			alumno.setEmail(dto.getEmail());
			alumno.setFechaRegistro(dto.getFechaRegistro());
			service.guardar(alumno);
			return ResponseEntity.ok(alumno);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Eliminar un alumno")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Alumno eliminado correctamente"),
			@ApiResponse(responseCode = "404", description = "Alumno no encontrado") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (service.existePorId(id)) {
			service.eliminar(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}