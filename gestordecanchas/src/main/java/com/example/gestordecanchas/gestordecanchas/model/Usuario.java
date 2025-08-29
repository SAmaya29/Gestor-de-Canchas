package com.example.gestordecanchas.gestordecanchas.model;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private LocalDateTime fecha_registro;
    private String rol;

    public Usuario(){
        this.fecha_registro = LocalDateTime.now();
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede ser vacio o nulo");
        }
        this.nombre = nombre;
    }

    public void setCorreo_electronico(String correo) {
        if (correo == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", correo)) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        Pbkdf2PasswordEncoder encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        this.contrasena = encoder.encode(contrasena);
    }
}
