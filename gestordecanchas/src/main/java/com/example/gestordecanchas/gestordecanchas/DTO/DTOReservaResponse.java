package com.example.gestordecanchas.gestordecanchas.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DTOReservaResponse {
    private Integer id;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Integer cantidadPersonas;
    private LocalDateTime createdAt;
    // Información del estado
    private String estado;  
    // Información de la cancha
    private String canchaNombre;
    private String canchaDireccion;
    private String tipoDeCancha;
    private Float precioHora;
    private Integer capacidadMaxima;   
    // Información del usuario
    private String usuarioNombre;
}