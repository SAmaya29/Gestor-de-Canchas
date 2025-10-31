package com.example.gestordecanchas.gestordecanchas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.gestordecanchas.gestordecanchas.model.Cancha;

public interface CanchaRepository extends JpaRepository<Cancha, Integer>{
    boolean existsByNombre(String nombre);
    
    @Query("SELECT c FROM Cancha c LEFT JOIN FETCH c.tipoDeCancha")
    List<Cancha> findAllWithTipoDeCancha();
}
