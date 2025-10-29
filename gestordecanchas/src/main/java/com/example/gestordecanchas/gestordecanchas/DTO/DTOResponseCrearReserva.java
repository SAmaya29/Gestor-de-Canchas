package com.example.gestordecanchas.gestordecanchas.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DTOResponseCrearReserva {

    private LocalDateTime inicio;
    private LocalDateTime fin;
    private String usuarioNombre;
    private String canchaNombre;
    private Integer cantidadPersonas;
    private String estado;
}