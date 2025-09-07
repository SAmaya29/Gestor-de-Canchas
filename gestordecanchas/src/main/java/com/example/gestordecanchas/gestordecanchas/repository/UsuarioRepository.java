package com.example.gestordecanchas.gestordecanchas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestordecanchas.gestordecanchas.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByCorreo(String correo);

}