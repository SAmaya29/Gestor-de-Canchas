package com.example.gestordecanchas.gestordecanchas.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reserva")
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "cancha_id")
    @JsonBackReference
    private Cancha cancha;
    
    private LocalDateTime inicio;
    private LocalDateTime fin;
    
    @ManyToOne
    @JoinColumn(name = "estado_id")
    @JsonBackReference
    private Estado estado;
    private Integer cantidad_personas;
    private LocalDateTime created_at;


    public Reserva(){
        this.created_at = LocalDateTime.now();
    }
    
}
