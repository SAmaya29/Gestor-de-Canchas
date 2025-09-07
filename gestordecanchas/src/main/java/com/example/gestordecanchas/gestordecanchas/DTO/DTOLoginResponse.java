package com.example.gestordecanchas.gestordecanchas.DTO;

import lombok.Data;

@Data
public class DTOLoginResponse {
    
    private String token;
    private String tipo = "Bearer";
    private String nombre;
    private String correo;
    private String rol;
    private Long expiracion;
}
