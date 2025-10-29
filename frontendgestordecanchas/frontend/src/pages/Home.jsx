import React, { useState, useEffect, useContext } from 'react';
import Navbar from '../components/Navbar';
import ReservaCard from '../components/ReservaCard';
import { getUserReservas } from '../api/reservaService';
import { AuthContext } from '../context/AuthContext';
import '../css/Home.css';

export default function Home() {
    const [reservas, setReservas] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { user } = useContext(AuthContext);

    useEffect(() => {
        if (!user) return;

        const fetchReservas = () => {
            setLoading(true);
            setError(null);
            
            getUserReservas()
                .then(response => {
                    console.log('✅ Reservas loaded:', response.data);
                    setReservas(Array.isArray(response.data) ? response.data : []);
                })
                .catch(error => {
                    console.error('❌ Error loading reservas:', error);
                    setError('Error al cargar las reservas. Por favor intenta de nuevo.');
                })
                .finally(() => {
                    setLoading(false);
                });
        };

        fetchReservas();
    }, [user]);

    // Función para reintentar cargar reservas
    const handleRetry = () => {
        setError(null);
        setLoading(true);
        
        getUserReservas()
            .then(response => {
                setReservas(Array.isArray(response.data) ? response.data : []);
            })
            .catch(error => {
                console.error('❌ Retry failed:', error);
                setError('Error al cargar las reservas. Por favor intenta de nuevo.');
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <div className="home-container">
            <Navbar />
            <div className="home-content">
                <div className="home-header">
                    <h1>Mis Reservas</h1>
                    <p>Bienvenido de vuelta, {user?.nombre}</p>
                </div>

                {loading && (
                    <div className="loading-container">
                        <div className="loading-spinner"></div>
                        <p>Cargando reservas...</p>
                    </div>
                )}

                {error && (
                    <div className="error-container">
                        <p>{error}</p>
                        <button onClick={handleRetry}>Reintentar</button>
                    </div>
                )}

                {!loading && !error && reservas.length === 0 && (
                    <div className="no-reservas">
                        <h3>No tienes reservas aún</h3>
                        <p>
                            ¡Haz tu primera reserva para empezar! 
                            <a href="/reservar" style={{ marginLeft: '5px' }}>Reservar</a>
                        </p>
                    </div>
                )}

                {!loading && !error && reservas.length > 0 && (
                    <div className="reservas-grid">
                        {reservas.map(reserva => (
                            <ReservaCard key={reserva.id} reserva={reserva} />
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

