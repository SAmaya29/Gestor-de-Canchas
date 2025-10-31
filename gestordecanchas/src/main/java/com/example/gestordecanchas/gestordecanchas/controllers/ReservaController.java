package com.example.gestordecanchas.gestordecanchas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearReserva;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearReserva;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOReservaResponse;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;
import com.example.gestordecanchas.gestordecanchas.service.AuthService;
import com.example.gestordecanchas.gestordecanchas.service.ReservaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @Autowired
    private AuthService authService;

    @PostMapping("/user/crearReserva")
    public ResponseEntity<?> crearReserva(@RequestBody DTOCrearReserva dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Validar autenticación primero
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        try {
            // Obtener usuario autenticado
            String correo = authentication.getName();
            Usuario usuario = authService.getCurrentUser(correo);
            // Asignar usuario al DTO
            dto.setUsuarioId(usuario.getId());
            // Crear reserva
            DTOResponseCrearReserva reservaCreada = reservaService.crearReserva(dto);
            return ResponseEntity.ok(reservaCreada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }

    @GetMapping("/user/reservas")
    public ResponseEntity<?> getUserReservas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String correo = authentication.getName();
            Usuario usuario = authService.getCurrentUser(correo);
            List<DTOReservaResponse> reservasResponse = reservaService.getUserReservas(usuario.getReservas());
            return ResponseEntity.ok(reservasResponse);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/user/cancelarReserva/{reservaId}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer reservaId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        try {
            String correo = authentication.getName();
            Usuario usuario = authService.getCurrentUser(correo);
            reservaService.cancelarReserva(reservaId, usuario.getId());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
}
