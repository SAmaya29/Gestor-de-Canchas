package com.example.gestordecanchas.gestordecanchas.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearCancha;
import com.example.gestordecanchas.gestordecanchas.model.Cancha;
import com.example.gestordecanchas.gestordecanchas.model.TipoDeCancha;
import com.example.gestordecanchas.gestordecanchas.repository.CanchaRepository;
import com.example.gestordecanchas.gestordecanchas.repository.TipoCanchaRepository;

@Service
public class CanchaService {

    @Autowired
    CanchaRepository canchaRepository;

    @Autowired
    TipoCanchaRepository tipoCanchaRepository;

    public Cancha crearCancha(DTOCrearCancha dto) {
        if (canchaRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("El nombre de esta cancha ya fue registrado");
        }
        TipoDeCancha tipo = tipoCanchaRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
        return newCancha(dto, tipo);     
    }


    private Cancha newCancha(DTOCrearCancha dto,  TipoDeCancha tipo){
        Cancha cancha = new Cancha();
        cancha.setNombre(dto.getNombre());
        cancha.setDireccion(dto.getDireccion());
        cancha.setTipoDeCancha(tipo);
        canchaRepository.save(cancha);
        return cancha;
    }

}
