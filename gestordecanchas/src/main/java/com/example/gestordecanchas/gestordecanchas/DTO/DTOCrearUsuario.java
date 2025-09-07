package com.example.gestordecanchas.gestordecanchas.DTO;
import lombok.Data;

@Data
public class DTOCrearUsuario {
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private Integer rolId;

    public DTOCrearUsuario() {
        this.rolId = 2; // Asignar el rol de "usuario" por defecto
    }
}
