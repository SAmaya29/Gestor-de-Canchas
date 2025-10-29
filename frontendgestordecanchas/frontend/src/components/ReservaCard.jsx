import React from 'react';
import '../css/ReservaCard.css';
import Button from './Button';

export default function ReservaCard({ reserva }) {
    // Función para formatear la fecha
    const formatFecha = (fecha) => {
        return new Date(fecha).toLocaleDateString('es-ES', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    };

    // Función para formatear la hora
    const formatHora = (hora) => {
        return new Date(`1970-01-01T${hora}`).toLocaleTimeString('es-ES', {
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    // Función para obtener el color del estado
    const getEstadoColor = (estado) => {
        switch (estado?.toLowerCase()) {
            case 'confirmada':
            case 'activa':
                return '#4caf50';
            case 'pendiente':
                return '#ff9800';
            case 'cancelada':
                return '#f44336';
            case 'completada':
                return '#2196f3';
            default:
                return '#9e9e9e';
        }
    };

    return (
        <div className="reserva-card">
            <div className="reserva-card-header">
                <h3 className="cancha-nombre">{reserva.cancha?.nombre || 'Cancha sin nombre'}</h3>
                <span
                    className="estado-badge"
                    style={{ backgroundColor: getEstadoColor(reserva.estado) }}
                >
                    {reserva.estado || 'Sin estado'}
                </span>
            </div>

            <div className="reserva-card-body">
                <div className="info-row">
                    <div className="info-item">
                        <span className="info-label">📍 Dirección:</span>
                        <span className="info-value">{reserva.cancha?.direccion || 'No especificada'}</span>
                    </div>
                </div>

                <div className="info-row">
                    <div className="info-item">
                        <span className="info-label">⚽ Tipo:</span>
                        <span className="info-value">{reserva.cancha?.tipoCancha || 'No especificado'}</span>
                    </div>
                    <div className="info-item">
                        <span className="info-label">👥 Capacidad:</span>
                        <span className="info-value">{reserva.cancha?.capacidadMaxima || 'N/A'} personas</span>
                    </div>
                </div>

                <div className="info-row">
                    <div className="info-item">
                        <span className="info-label">📅 Fecha:</span>
                        <span className="info-value">{formatFecha(reserva.fecha)}</span>
                    </div>
                </div>

                <div className="info-row">
                    <div className="info-item">
                        <span className="info-label">⏰ Horario:</span>
                        <span className="info-value">
                            {formatHora(reserva.horaInicio)} - {formatHora(reserva.horaFin)}
                        </span>
                    </div>
                </div>
            </div>

            <div className="reserva-card-footer">
                <Button className="btn-detalle">Ver Detalles</Button>
                {reserva.estado?.toLowerCase() === 'pendiente' && (
                    <Button className="btn-cancelar">Cancelar</Button>
                )}
            </div>
        </div>
    );
}
