import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import "../css/Navbar.css";

export default function Navbar() {
    const navigate = useNavigate();
    const { logout, user } = useContext(AuthContext);

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <h1 onClick={() => navigate('/home')}>⚽ GestorCanchas</h1>
            </div>
            <div className="navbar-menu">
                <button className="navbar-btn" onClick={() => navigate('/home')}>
                    Mis Reservas
                </button>
                <button className="navbar-btn" onClick={() => navigate('/reservar')}>
                    Reservar
                </button>
            </div>
            <div className="navbar-user">
                <span className="user-name">Hola, {user?.nombre}</span>
                <button className="navbar-btn logout-btn" onClick={handleLogout}>
                    Cerrar sesión
                </button>
            </div>
        </nav>
    );
}
