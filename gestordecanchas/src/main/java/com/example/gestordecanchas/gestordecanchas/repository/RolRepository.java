
package com.example.gestordecanchas.gestordecanchas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestordecanchas.gestordecanchas.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>   {
    
    Optional<Rol> findByNombre(String nombre);
}