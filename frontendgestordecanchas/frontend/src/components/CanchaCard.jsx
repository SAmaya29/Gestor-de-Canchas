import React from 'react';
import '../css/CanchaCard.css';

export default function CanchaCard({ cancha, onSelect, isSelected }) {
    const handleClick = () => {
        console.log('🏟️ Cancha selected:', cancha.nombre);
        onSelect(cancha);
    };

    return (
        <div 
            className={`cancha-card ${isSelected ? 'selected' : ''}`}
            onClick={handleClick}
        >
            <div className="cancha-card-header">
                <h3 className="cancha-nombre">{cancha.nombre}</h3>
                <span className="cancha-tipo">{cancha.tipoDeCancha?.nombre || 'Tipo no disponible'}</span>
            </div>
            
            <div className="cancha-card-body">
                <div className="cancha-info">
                    <div className="info-item">
                        <span className="info-label">📍 Dirección:</span>
                        <span className="info-value">{cancha.direccion}</span>
                    </div>
                    
                    <div className="info-item">
                        <span className="info-label">⚽ Tipo:</span>
                        <span className="info-value">{cancha.tipoDeCancha?.nombre || 'Tipo no disponible'}</span>
                    </div>
                    
                    <div className="info-item">
                        <span className="info-label">💰 Precio:</span>
                        <span className="info-value precio">${cancha.precio_hora}/hora</span>
                    </div>
                </div>
            </div>
            
            <div className="cancha-card-footer">
                <button className="select-btn">
                    {isSelected ? 'Seleccionada' : 'Seleccionar'}
                </button>
            </div>
        </div>
    );
}