package com.example.gestordecanchas.gestordecanchas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestordecanchas.gestordecanchas.model.Estado;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    Optional<Estado> findByDescripcion(String string);

}
