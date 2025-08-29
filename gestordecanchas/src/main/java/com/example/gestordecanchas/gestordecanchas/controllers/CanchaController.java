package com.example.gestordecanchas.gestordecanchas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearCancha;
import com.example.gestordecanchas.gestordecanchas.model.Cancha;
import com.example.gestordecanchas.gestordecanchas.service.CanchaService;

@RestController
@RequestMapping("/cancha")
public class CanchaController {

    @Autowired
    CanchaService canchaService;

    @PostMapping("/crearCancha")
    public ResponseEntity<?> crearCancha(@RequestBody DTOCrearCancha dto) {
        try {
            Cancha nuevCancha = canchaService.crearCancha(dto);
            return ResponseEntity.ok(nuevCancha);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
