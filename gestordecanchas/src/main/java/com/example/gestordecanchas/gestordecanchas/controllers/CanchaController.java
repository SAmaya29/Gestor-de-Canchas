package com.example.gestordecanchas.gestordecanchas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearCancha;
import com.example.gestordecanchas.gestordecanchas.model.Cancha;
import com.example.gestordecanchas.gestordecanchas.repository.CanchaRepository;
import com.example.gestordecanchas.gestordecanchas.service.CanchaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/cancha")
public class CanchaController {

    @Autowired
    CanchaService canchaService;

    @Autowired
    CanchaRepository canchaRepository;

    @PostMapping("/admin/crearCancha")
    public ResponseEntity<?> crearCancha(@RequestBody DTOCrearCancha dto) {
        try {
            Cancha nuevCancha = canchaService.crearCancha(dto);
            return ResponseEntity.ok(nuevCancha);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/canchas")
    public ResponseEntity<?> getCanchas() {
        try {
            List<Cancha> canchas = canchaRepository.findAllWithTipoDeCancha();
            return ResponseEntity.ok(canchas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/cancha/{id}")
    public ResponseEntity<?> getCanchaById(@PathVariable Integer id) {
        try {
            Optional<Cancha> cancha = canchaRepository.findById(id);
            return ResponseEntity.ok(cancha);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    

}
