package com.example.gestordecanchas.gestordecanchas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestordecanchas.gestordecanchas.model.Cancha;

public interface CanchaRepository extends JpaRepository<Cancha, Integer>{
    boolean existsByNombre(String nombre);
}
