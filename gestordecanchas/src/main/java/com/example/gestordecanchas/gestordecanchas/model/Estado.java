package com.example.gestordecanchas.gestordecanchas.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estado")
@Data
public class Estado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_id")
    private Integer id;
    private String descripcion;

    @OneToMany(mappedBy = "estado")
    private List<Reserva> reservas;

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser vacía o nula");
        }
        this.descripcion = descripcion;
    }
}
