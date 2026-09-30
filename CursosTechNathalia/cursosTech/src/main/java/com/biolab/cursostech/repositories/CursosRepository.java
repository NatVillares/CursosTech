package com.biolab.cursostech.repositories;

import com.biolab.cursostech.entities.Cursos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CursosRepository extends JpaRepository<Cursos, Long> { }
