package com.springboot.ejercicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ejercicio.Alumno;
import com.springboot.ejercicio.AlumnoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository repo;

    public List<Alumno> listarTodos() {
        return repo.findAll();
    }

    public Optional<Alumno> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Alumno guardar(Alumno alumno) {
        return repo.save(alumno);
    }

    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}