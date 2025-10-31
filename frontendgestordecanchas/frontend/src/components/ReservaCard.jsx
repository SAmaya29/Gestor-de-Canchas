import React, { useState } from 'react';
import { cancelarReserva } from '../api/reservaService';
import '../css/ReservaCard.css';

export default function ReservaCard({ reserva, onReservaCancelada }) {
    const [loading, setLoading] = useState(false);

    const handleCancelarReserva = async () => {
        const confirmar = window.confirm(
            `¿Estás seguro de que quieres cancelar la reserva de "${reserva.canchaNombre}"?\n\nFecha: ${formatFecha(reserva.inicio)}\nHora: ${formatHora(reserva.inicio)} - ${formatHora(reserva.fin)}`
        );

        if (!confirmar) return;

        setLoading(true);
        try {
            await cancelarReserva(reserva.id);
            alert('Reserva cancelada exitosamente');
            // Llamar callback para actualizar la lista en el componente padre
            if (onReservaCancelada) {
                onReservaCancelada(reserva.id);
            }
        } catch (error) {
            console.error('Error al cancelar reserva:', error);
            if (error.response && error.response.data) {
                alert(`Error: ${error.response.data}`);
            } else {
                alert('Error al cancelar la reserva. Por favor intenta de nuevo.');
            }
        } finally {
            setLoading(false);
        }
    };
    // Función para formatear la fecha y hora desde LocalDateTime
    const formatFecha = (fechaHora) => {
        return new Date(fechaHora).toLocaleDateString('es-ES', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    };

    // Función para formatear la hora desde LocalDateTime
    const formatHora = (fechaHora) => {
        return new Date(fechaHora).toLocaleTimeString('es-ES', {
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
            case 'finalizada':
                return '#2196f3';
            default:
                return '#9e9e9e';
        }
    };

    return (
        <div className="reserva-card">
            <div className="reserva-card-header">
                <h3 className="cancha-nombre">{reserva.canchaNombre || 'Cancha sin nombre'}</h3>
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
                        <span className="info-value">{reserva.canchaDireccion || 'No especificada'}</span>
                    </div>
                </div>

                <div className="info-row">
                    <div className="info-item">
                        <span className="info-label">⚽ Tipo:</span>
                        <span className="info-value">{reserva.tipoDeCancha || 'No especificado'}</span>
                    </div>
                    <div className="info-item">
                        <span className="info-label">👥 Personas:</span>
                        <span className="info-value">{reserva.cantidadPersonas || 'N/A'} personas</span>
                    </div>
                </div>

                <div className="info-row">
                    <div className="info-item">
                        <span className="info-label">📅 Fecha:</span>
                        <span className="info-value">{formatFecha(reserva.inicio)}</span>
                    </div>
                    <div className="info-item">
                        <span className="info-label">💰 Precio/hora:</span>
                        <span className="info-value">${reserva.precioHora || 'N/A'}</span>
                    </div>
                </div>

                <div className="info-row">
                    <div className="info-item">
                        <span className="info-label">⏰ Horario:</span>
                        <span className="info-value">
                            {formatHora(reserva.inicio)} - {formatHora(reserva.fin)}
                        </span>
                    </div>
                </div>
            </div>

            <div className="reserva-card-footer">
                {reserva.estado?.toLowerCase() === 'pendiente' && (
                    <button 
                        className="btn-cancelar" 
                        onClick={handleCancelarReserva}
                        disabled={loading}
                    >
                        {loading ? 'Cancelando...' : 'Cancelar Reserva'}
                    </button>
                )}
            </div>
        </div>
    );
}
