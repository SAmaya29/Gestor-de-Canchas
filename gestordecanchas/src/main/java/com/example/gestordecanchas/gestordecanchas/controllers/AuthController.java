package com.example.gestordecanchas.gestordecanchas.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearUsuario;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOErrorResponse;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOLoginRequest;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOLoginResponse;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearUsuario;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;
import com.example.gestordecanchas.gestordecanchas.service.AuthService;
import com.example.gestordecanchas.gestordecanchas.service.UsuarioService;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DTOLoginRequest loginRequest) {
        try {
            DTOLoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new DTOErrorResponse(401, "Unauthorized", "Credenciales inválidas"));
    } catch (Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new DTOErrorResponse(500, "Error", "Ocurrió un error inesperado"));
    } 
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody DTOCrearUsuario crearUsuario) {
        try {
            DTOResponseCrearUsuario response = usuarioService.crearUsuario(crearUsuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new DTOErrorResponse(500, "Error", "Ocurrió un error inesperado"));
    }
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String correo = authentication.getName();
            Usuario usuario = authService.getCurrentUser(correo);
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extraer el token del header "Bearer token"
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                //String token = authHeader.substring(7);
                // Aquí podrías agregar el token a una blacklist si quisieras
                // Por ahora, simplemente confirmamos el logout
                return ResponseEntity.ok().body(Map.of("message", "Logout exitoso"));
            }
            return ResponseEntity.badRequest().body(Map.of("error", "Token no proporcionado"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error en logout"));
        }
    }
}
