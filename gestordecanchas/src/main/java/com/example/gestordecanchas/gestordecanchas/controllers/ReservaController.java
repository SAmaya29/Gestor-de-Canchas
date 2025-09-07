package com.example.gestordecanchas.gestordecanchas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearReserva;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearReserva;
import com.example.gestordecanchas.gestordecanchas.service.ReservaService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/user/reserva")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @PostMapping("/crearReserva")
    public ResponseEntity<?> crearReserva(@RequestBody DTOCrearReserva dto) {
        try {
            DTOResponseCrearReserva reservaCreada = reservaService.crearReserva(dto);
            return ResponseEntity.ok(reservaCreada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
