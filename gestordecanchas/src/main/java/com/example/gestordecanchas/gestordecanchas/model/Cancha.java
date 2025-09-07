package com.example.gestordecanchas.gestordecanchas.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cancha")
@Data
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancha_id")
    private Integer id;
    private String nombre;
    private String direccion;
    private Float precio_hora;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    @JsonBackReference
    private TipoDeCancha tipoDeCancha;

    @OneToMany(mappedBy = "cancha")
    @JsonManagedReference
    private List<Reserva> reservas;

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío o nulo");
        }
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede ser vacía o nula");
        }
        this.direccion = direccion;
    }

}
