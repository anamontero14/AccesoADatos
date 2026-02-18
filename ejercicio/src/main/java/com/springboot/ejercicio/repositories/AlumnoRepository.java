package com.springboot.ejercicio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.ejercicio.entities.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}
