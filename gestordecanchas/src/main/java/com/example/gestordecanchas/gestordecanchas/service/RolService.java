package com.example.gestordecanchas.gestordecanchas.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestordecanchas.gestordecanchas.DTO.DTORolResponse;
import com.example.gestordecanchas.gestordecanchas.model.Rol;
import com.example.gestordecanchas.gestordecanchas.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public List<DTORolResponse> obtenerRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                .map(this::rolToRolResponse)
                .collect(Collectors.toList());
    }

    private DTORolResponse rolToRolResponse(Rol rol) {
        DTORolResponse dto = new DTORolResponse();
        dto.setNombreRol(rol.getNombre());
        return dto;
    }
}
