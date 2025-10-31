package com.example.gestordecanchas.gestordecanchas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestordecanchas.gestordecanchas.model.Estado;
import com.example.gestordecanchas.gestordecanchas.repository.EstadoRepository;

@RestController
@RequestMapping("/estado")
public class EstadoController {
    
    @Autowired
    EstadoRepository estadoRepository;

    @GetMapping("/user/estados")
    public ResponseEntity<?> getEstados() {
        try {
            List<Estado> estados = estadoRepository.findAll();
            return ResponseEntity.ok(estados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
