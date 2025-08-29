package com.example.gestordecanchas.gestordecanchas.model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tipo_cancha")
@Data
public class TipoDeCancha {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_id")
    private Integer id;
    private String nombre;
    @Column(name = "capacidad_max")
    private Integer capacidad_maxima;

    @OneToMany(mappedBy = "tipoDeCancha")
    @JsonManagedReference
    private List<Cancha> canchas;
}
