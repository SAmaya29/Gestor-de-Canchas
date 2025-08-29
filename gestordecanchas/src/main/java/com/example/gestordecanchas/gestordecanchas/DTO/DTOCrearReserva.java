package com.example.gestordecanchas.gestordecanchas.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DTOCrearReserva {
    private Integer usuarioId;
    private Integer canchaId;
    private LocalDateTime inicioPedido;
    private LocalDateTime finPedido;
    private Integer estadoId;
    private Integer cantidadPersonas;
    /*@Data
    public static class DTOCrearEstado{
        private String descripcion;
    } */

}