import React, { useState, useEffect } from 'react';
import Navbar from '../components/NavBar.jsx';
import CanchaCard from '../components/CanchaCard';
import CanchaDetail from '../components/CanchaDetail';
import { getCanchas } from '../api/canchaService';
import '../css/Reservar.css';

export default function Reservar() {
    const [canchas, setCanchas] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [selectedCancha, setSelectedCancha] = useState(null);
    const [showDetail, setShowDetail] = useState(false);

    useEffect(() => {
        const fetchCanchas = () => {
            setLoading(true);
            setError('');
            
            getCanchas()
                .then(response => {
                    console.log('✅ Canchas loaded:', response.data);
                    setCanchas(Array.isArray(response.data) ? response.data : []);
                })
                .catch(error => {
                    console.error('❌ Error loading canchas:', error);
                    setError('Error al cargar las canchas. Por favor intenta de nuevo.');
                })
                .finally(() => {
                    setLoading(false);
                });
        };

        fetchCanchas();
    }, []);

    const handleSelectCancha = (cancha) => {
        setSelectedCancha(cancha);
        setShowDetail(true);
    };

    const handleCloseDetail = () => {
        setShowDetail(false);
        setSelectedCancha(null);
    };

    const handleReservaSuccess = (nuevaReserva) => {
        console.log('🎉 Reserva successful:', nuevaReserva);
        // Aquí podrías mostrar un mensaje de éxito o redirigir
        alert('¡Reserva creada exitosamente!');
    };

    const handleRetry = () => {
        window.location.reload();
    };

    return (
        <div className="reservar-container">
            <Navbar />
            
            <div className="reservar-content">
                <div className="reservar-header">
                    <h1>Reservar Cancha</h1>
                    <p>Selecciona la cancha que más te guste y haz tu reserva</p>
                </div>

                {loading && (
                    <div className="loading-container">
                        <div className="loading-spinner"></div>
                        <p>Cargando canchas disponibles...</p>
                    </div>
                )}

                {error && (
                    <div className="error-container">
                        <p>{error}</p>
                        <button onClick={handleRetry}>Reintentar</button>
                    </div>
                )}

                {!loading && !error && canchas.length === 0 && (
                    <div className="no-canchas">
                        <h3>No hay canchas disponibles</h3>
                        <p>En este momento no hay canchas disponibles para reservar.</p>
                    </div>
                )}

                {!loading && !error && canchas.length > 0 && (
                    <div className="canchas-grid">
                        {canchas.map(cancha => (
                            <CanchaCard
                                key={cancha.id}
                                cancha={cancha}
                                onSelect={handleSelectCancha}
                                isSelected={selectedCancha?.id === cancha.id}
                            />
                        ))}
                    </div>
                )}
            </div>

            {showDetail && selectedCancha && (
                <CanchaDetail
                    cancha={selectedCancha}
                    onClose={handleCloseDetail}
                    onReservaSuccess={handleReservaSuccess}
                />
            )}
        </div>
    );
}