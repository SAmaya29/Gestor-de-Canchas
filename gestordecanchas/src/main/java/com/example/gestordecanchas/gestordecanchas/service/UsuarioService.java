package com.example.gestordecanchas.gestordecanchas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;
import com.example.gestordecanchas.gestordecanchas.repository.UsuarioRepository;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearUsuario;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearUsuario;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public DTOResponseCrearUsuario crearUsuario(DTOCrearUsuario dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol());

        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        usuarioRepository.save(usuario);

        DTOResponseCrearUsuario response = new DTOResponseCrearUsuario();
        response.setNombre(usuario.getNombre());
        response.setCorreo(usuario.getCorreo());
        response.setTelefono(usuario.getTelefono());
        response.setRol(usuario.getRol());
        return response;
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
    }

    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        usuarioRepository.deleteById(id);
    }

}
