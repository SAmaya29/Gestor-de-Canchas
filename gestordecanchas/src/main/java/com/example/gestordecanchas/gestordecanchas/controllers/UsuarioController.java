package com.example.gestordecanchas.gestordecanchas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearUsuario;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearUsuario;
import com.example.gestordecanchas.gestordecanchas.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody DTOCrearUsuario dto) {
        try {
            DTOResponseCrearUsuario usuarioCreado = usuarioService.crearUsuario(dto);
            return ResponseEntity.ok(usuarioCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
