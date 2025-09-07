package com.example.gestordecanchas.gestordecanchas.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOLoginRequest;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOLoginResponse;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;
import com.example.gestordecanchas.gestordecanchas.repository.UsuarioRepository;
import com.example.gestordecanchas.gestordecanchas.security.JwtUtils;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public DTOLoginResponse login(DTOLoginRequest loginRequest) {
        try {
            // Buscar usuario por correo
            Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo())
                    .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

            // Verificar contraseña
            if (!passwordEncoder.matches(loginRequest.getContrasena(), usuario.getContrasena())) {
                throw new BadCredentialsException("Credenciales inválidas");
            }

            // Generar token
            String token = jwtUtils.generateJwtToken(usuario.getCorreo(), usuario.getRol().getNombre());
            
            // Crear respuesta
            DTOLoginResponse response = new DTOLoginResponse();
            response.setToken(token);
            response.setNombre(usuario.getNombre());
            response.setCorreo(usuario.getCorreo());
            response.setRol(usuario.getRol().getNombre());
            response.setExpiracion(jwtUtils.getExpirationDateFromToken(token).getTime());

            return response;
        } catch (BadCredentialsException e) {
            // Re-lanzar las excepciones de credenciales
            throw e;
        } catch (Exception e) {
            // Log del error específico para debugging
            System.err.println("Error en login: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error interno del servidor", e);
        }
    }

    public Usuario getCurrentUser(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
