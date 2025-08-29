package com.example.gestordecanchas.gestordecanchas.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DTOResponseCrearReserva {

    private LocalDateTime inicio;
    private LocalDateTime fin;
    //private Integer usuario_id;
    //private Integer cancha_id;
    private String usuarioNombre;
    private String canchaNombre;
    private Integer cantidadPersonas;
    private String estado;
}