package com.example.gestordecanchas.gestordecanchas.DTO;

import lombok.Data;

@Data
public class DTOResponseCrearUsuario {
    private String nombre;
    private String correo;
    private String telefono;
    private String rol;
}
