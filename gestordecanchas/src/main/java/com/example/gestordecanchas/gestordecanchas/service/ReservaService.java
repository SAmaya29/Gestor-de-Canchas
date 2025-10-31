package com.example.gestordecanchas.gestordecanchas.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOCrearReserva;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOResponseCrearReserva;
import com.example.gestordecanchas.gestordecanchas.DTO.DTOReservaResponse;
import com.example.gestordecanchas.gestordecanchas.model.Cancha;
import com.example.gestordecanchas.gestordecanchas.model.Estado;
import com.example.gestordecanchas.gestordecanchas.model.Reserva;
import com.example.gestordecanchas.gestordecanchas.model.Usuario;
import com.example.gestordecanchas.gestordecanchas.repository.ReservaRepository;
import com.example.gestordecanchas.gestordecanchas.repository.UsuarioRepository;
import com.example.gestordecanchas.gestordecanchas.repository.CanchaRepository;
import com.example.gestordecanchas.gestordecanchas.repository.EstadoRepository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public DTOResponseCrearReserva crearReserva(DTOCrearReserva dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Cancha cancha = canchaRepository.findById(dto.getCanchaId())
                .orElseThrow(() -> new IllegalArgumentException("Cancha no encontrada"));
        Estado estado = estadoRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
        List<Reserva> reservas = cancha.getReservas();
        for (Reserva reserva : reservas) {
            if (dto.getInicioPedido().isBefore(reserva.getFin()) &&
                    dto.getFinPedido().isAfter(reserva.getInicio())) {
                throw new IllegalArgumentException("La cancha ya se encuentra reservada en este horario");
            }
        }
        if (dto.getCantidadPersonas() > cancha.getTipoDeCancha().getCapacidad_maxima()) {
            throw new IllegalArgumentException(
                    "La cantidad de personas en la reservas superan la capacidad de la cancha");
        }

        Reserva reserva = newReserva(usuario, cancha, estado, dto);

        return newResponseCrearReserva(reserva);

    }

    private Reserva newReserva(Usuario usuario, Cancha cancha, Estado estado, DTOCrearReserva dto) {
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setCancha(cancha);
        reserva.setInicio(dto.getInicioPedido());
        reserva.setFin(dto.getFinPedido());
        reserva.setEstado(estado);
        reserva.setCantidad_personas(dto.getCantidadPersonas());
        reservaRepository.save(reserva);
        return reserva;
    }

    private DTOResponseCrearReserva newResponseCrearReserva(Reserva reserva) {
        DTOResponseCrearReserva response = new DTOResponseCrearReserva();
        response.setInicio(reserva.getInicio());
        response.setFin(reserva.getFin());
        response.setEstado(reserva.getEstado().getDescripcion());
        response.setUsuarioNombre(reserva.getUsuario().getNombre());
        response.setCanchaNombre(reserva.getCancha().getNombre());
        response.setCantidadPersonas(reserva.getCantidad_personas());
        return response;
    }

    public List<DTOReservaResponse> getUserReservas(List<Reserva> reservas) {
        return reservas.stream()
                .filter(r -> !r.getEstado().getDescripcion().equals("Cancelada"))
                .map(this::convertReservaToReservaResponse)
                .collect(Collectors.toList());
    }

    private DTOReservaResponse convertReservaToReservaResponse(Reserva reserva) {
        DTOReservaResponse response = new DTOReservaResponse();
        response.setId(reserva.getId());
        response.setInicio(reserva.getInicio());
        response.setFin(reserva.getFin());
        response.setCantidadPersonas(reserva.getCantidad_personas());
        response.setCreatedAt(reserva.getCreated_at());
        // Información del estado
        response.setEstado(reserva.getEstado().getDescripcion());
        // Información de la cancha
        response.setCanchaNombre(reserva.getCancha().getNombre());
        response.setCanchaDireccion(reserva.getCancha().getDireccion());
        response.setTipoDeCancha(reserva.getCancha().getTipoDeCancha().getNombre());
        response.setPrecioHora(reserva.getCancha().getPrecio_hora());
        response.setCapacidadMaxima(reserva.getCancha().getTipoDeCancha().getCapacidad_maxima());
        // Información del usuario
        response.setUsuarioNombre(reserva.getUsuario().getNombre());
        return response;
    }

    @Transactional
    public void cancelarReserva(Integer reservaId, Integer usuarioId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        // Verificar que la reserva pertenece al usuario autenticado
        if (!reserva.getUsuario().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("No tienes permisos para cancelar esta reserva");
        }
        // Verificar que la reserva esté en estado "Pendiente" para poder cancelarla
        if (!"Pendiente".equalsIgnoreCase(reserva.getEstado().getDescripcion())) {
            throw new IllegalArgumentException("Solo se pueden cancelar reservas en estado 'Pendiente'");
        }

        // Modificar el estado de la reserva a "Cancelada"
        reserva.setEstado(estadoRepository.findByDescripcion("Cancelada")
                .orElseThrow(() -> new RuntimeException("Estado 'Cancelada' no encontrado")));
        reservaRepository.save(reserva);
    }
}
