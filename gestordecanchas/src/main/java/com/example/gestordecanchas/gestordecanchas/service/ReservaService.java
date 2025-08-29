package com.example.gestordecanchas.gestordecanchas.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearReserva;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearReserva;
import com.example.gestordecanchas.gestordecanchas.model.Cancha;
import com.example.gestordecanchas.gestordecanchas.model.Estado;
import com.example.gestordecanchas.gestordecanchas.model.Reserva;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;
import com.example.gestordecanchas.gestordecanchas.repository.ReservaRepository;
import com.example.gestordecanchas.gestordecanchas.repository.UsuarioRepository;
import com.example.gestordecanchas.gestordecanchas.repository.CanchaRepository;
import com.example.gestordecanchas.gestordecanchas.repository.EstadoRepository;

@Service
public class ReservaService {

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CanchaRepository canchaRepository;

    @Autowired
    EstadoRepository estadoRepository;

    public DTOResponseCrearReserva crearReserva(DTOCrearReserva dto) {

        List<Reserva> reservas = reservaRepository.findAll();
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        Cancha cancha = canchaRepository.findById(dto.getCanchaId()).orElse(null);
        Estado estado = estadoRepository.findById(dto.getEstadoId())
        .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        if (usuario == null || cancha == null || estado == null) {
            throw new IllegalArgumentException("Error en los datos ingresados");
        }
        for (Reserva reserva : reservas) {
            if (dto.getInicioPedido().isBefore(reserva.getFin()) && dto.getFinPedido().isAfter(reserva.getInicio())) {
                throw new IllegalArgumentException("La cancha ya se encuentra reservada en este horario");
            }
        }
        if(dto.getCantidadPersonas() > cancha.getTipoDeCancha().getCapacidad_maxima()){
            throw new IllegalArgumentException("La cantidad de personas en la reservas superan la capacidad de la cancha");
        }
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setCancha(cancha);
        reserva.setInicio(dto.getInicioPedido());
        reserva.setFin(dto.getFinPedido());
        reserva.setEstado(estado);
        reserva.setCantidad_personas(dto.getCantidadPersonas());
        reservaRepository.save(reserva);

        DTOResponseCrearReserva response = new DTOResponseCrearReserva();
        response.setInicio(reserva.getInicio());
        response.setFin(reserva.getFin());
        response.setEstado(reserva.getEstado().getDescripcion());
        response.setUsuarioNombre(reserva.getUsuario().getNombre());
        response.setCanchaNombre(reserva.getCancha().getNombre());
        response.setCantidadPersonas(reserva.getCantidad_personas());
        return response;
    }
}
