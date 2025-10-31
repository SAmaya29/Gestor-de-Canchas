import React, { useState } from 'react';
import { crearReserva } from '../api/reservaService';
import '../css/CanchaDetail.css';

export default function CanchaDetail({ cancha, onClose, onReservaSuccess }) {
    const [formData, setFormData] = useState({
        fecha: '',
        horaInicio: '',
        horaFin: '',
        cantidadPersonas: 1
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('📝 Submitting reserva:', formData);
        
        setError('');
        setSuccess('');
        setLoading(true);

        // Convertir fecha + hora a LocalDateTime format para el backend
        const inicioPedido = `${formData.fecha}T${formData.horaInicio}:00`;
        const finPedido = `${formData.fecha}T${formData.horaFin}:00`;

        const reservaData = {
            canchaId: cancha.id,
            inicioPedido: inicioPedido,
            finPedido: finPedido,
            cantidadPersonas: parseInt(formData.cantidadPersonas),
            estadoId: 1 // Estado "Pendiente" por defecto
        };

        console.log('📡 Sending reserva data:', reservaData);

        crearReserva(reservaData)
            .then(response => {
                console.log('✅ Reserva created:', response.data);
                onReservaSuccess(response.data);
                onClose();
            })
            .catch(error => {
                console.error('❌ Error creating reserva:', error);
                console.error('❌ Error response:', error.response);
                
                // Manejar errores específicos del backend
                if (error.response && error.response.data) {
                    // Si el backend envía un mensaje específico, mostrarlo
                    if (typeof error.response.data === 'string') {
                        setError(error.response.data);
                    } else if (error.response.data.message) {
                        setError(error.response.data.message);
                    } else {
                        setError('Error al crear la reserva. Por favor intenta de nuevo.');
                    }
                } else {
                    setError('Error de conexión. Por favor intenta de nuevo.');
                }
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const calcularPrecioTotal = () => {
        if (!formData.horaInicio || !formData.horaFin) return 0;
        
        const inicio = new Date(`2000-01-01T${formData.horaInicio}`);
        const fin = new Date(`2000-01-01T${formData.horaFin}`);
        const horas = (fin - inicio) / (1000 * 60 * 60);
        
        return horas > 0 ? (horas * cancha.precio_hora).toFixed(2) : 0;
    };

    return (
        <div className="cancha-detail-overlay" onClick={onClose}>
            <div className="cancha-detail-modal" onClick={e => e.stopPropagation()}>
                <div className="modal-header">
                    <h2>{cancha.nombre}</h2>
                    <button className="close-btn" onClick={onClose}>×</button>
                </div>

                <div className="modal-body">
                    <div className="cancha-info-detailed">
                        <div className="info-grid">
                            <div className="info-item">
                                <strong>📍 Dirección:</strong>
                                <span>{cancha.direccion}</span>
                            </div>
                            <div className="info-item">
                                <strong>⚽ Tipo:</strong>
                                <span>{cancha.tipoDeCancha?.nombre || 'Tipo no especificado'}</span>
                            </div>
                            <div className="info-item">
                                <strong>💰 Precio por hora:</strong>
                                <span>${cancha.precio_hora}</span>
                            </div>
                        </div>
                        
                        {cancha.descripcion && (
                            <div className="descripcion">
                                <strong>📝 Descripción:</strong>
                                <p>{cancha.descripcion}</p>
                            </div>
                        )}
                    </div>

                    <form onSubmit={handleSubmit} className="reserva-form">
                        <h3>Hacer Reserva</h3>
                        
                        <div className="form-row">
                            <div className="form-group">
                                <label htmlFor="fecha">Fecha:</label>
                                <input
                                    type="date"
                                    id="fecha"
                                    name="fecha"
                                    value={formData.fecha}
                                    onChange={handleInputChange}
                                    min={new Date().toISOString().split('T')[0]}
                                    required
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label htmlFor="horaInicio">Hora inicio:</label>
                                <input
                                    type="time"
                                    id="horaInicio"
                                    name="horaInicio"
                                    value={formData.horaInicio}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="horaFin">Hora fin:</label>
                                <input
                                    type="time"
                                    id="horaFin"
                                    name="horaFin"
                                    value={formData.horaFin}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label htmlFor="cantidadPersonas">Cantidad de personas:</label>
                                <input
                                    type="number"
                                    id="cantidadPersonas"
                                    name="cantidadPersonas"
                                    value={formData.cantidadPersonas}
                                    onChange={handleInputChange}
                                    min="1"
                                    max={cancha.tipoDeCancha?.capacidad_maxima || 50}
                                    required
                                />
                            </div>
                        </div>

                        {calcularPrecioTotal() > 0 && (
                            <div className="precio-total">
                                <strong>Total a pagar: ${calcularPrecioTotal()}</strong>
                            </div>
                        )}

                        {error && (
                            <div className="error-message">{error}</div>
                        )}

                        {success && (
                            <div className="success-message">{success}</div>
                        )}

                        <div className="form-actions">
                            <button type="button" onClick={onClose} className="cancel-btn">
                                Cancelar
                            </button>
                            <button type="submit" disabled={loading} className="submit-btn">
                                {loading ? 'Creando reserva...' : 'Confirmar Reserva'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}