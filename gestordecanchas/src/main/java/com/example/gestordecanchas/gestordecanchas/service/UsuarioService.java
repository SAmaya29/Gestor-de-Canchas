package com.example.gestordecanchas.gestordecanchas.service;
import org.springframework.beans.factory.annotation.Autowired;
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
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    public DTOResponseCrearUsuario crearUsuario(DTOCrearUsuario dto) {
        Rol rol = rolRepository.findById(dto.getRolId())
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Usuario usuario = newUsuario(dto, rol);
        return crearResponseCrearUsuario(usuario);
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario no fue encontrado o no existe"));
    }

    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        usuarioRepository.deleteById(id);
    }

    private Usuario newUsuario(DTOCrearUsuario dto, Rol rol){
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(rol);
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        usuarioRepository.save(usuario);
        return usuario;
    }

    private DTOResponseCrearUsuario crearResponseCrearUsuario(Usuario usuario){
        DTOResponseCrearUsuario response = new DTOResponseCrearUsuario();
        response.setNombre(usuario.getNombre());
        response.setCorreo(usuario.getCorreo());
        response.setTelefono(usuario.getTelefono());
        response.setRol(usuario.getRol().getNombre());
        return response;
    }

}
