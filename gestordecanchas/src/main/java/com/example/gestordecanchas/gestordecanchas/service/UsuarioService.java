package com.example.gestordecanchas.gestordecanchas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;
import com.example.gestordecanchas.gestordecanchas.model.Rol;
import com.example.gestordecanchas.gestordecanchas.repository.RolRepository;
import com.example.gestordecanchas.gestordecanchas.repository.UsuarioRepository;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearUsuario;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearUsuario;

@Service
public class UsuarioService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Value("${rol.admin.password}")
    private String adminPassword;

    public DTOResponseCrearUsuario crearUsuario(DTOCrearUsuario dto) {
        Usuario usuario = newUsuario(dto);
        return crearResponseCrearUsuario(usuario);
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no fue encontrado o no existe"));
    }

    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        usuarioRepository.deleteById(id);
    }

    public void encontrarUsuarioPorCorreo(String correo) {
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
    }

    private Usuario newUsuario(DTOCrearUsuario dto) {
        Usuario usuario = new Usuario();
        Rol rol = rolRepository.findByNombre(dto.getRolNombre())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        if(dto.getRolNombre().equals("ADMIN")) {
            if(!dto.getRolPassword().equals(adminPassword)) {
                throw new IllegalArgumentException("Contraseña de administrador incorrecta");
            }
        }
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol(rol);
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        usuarioRepository.save(usuario);
        return usuario;
    }

    private DTOResponseCrearUsuario crearResponseCrearUsuario(Usuario usuario) {
        DTOResponseCrearUsuario response = new DTOResponseCrearUsuario();
        response.setNombre(usuario.getNombre());
        response.setCorreo(usuario.getCorreo());
        response.setTelefono(usuario.getTelefono());
        response.setRol(usuario.getRol().getNombre());
        return response;
    }

}
