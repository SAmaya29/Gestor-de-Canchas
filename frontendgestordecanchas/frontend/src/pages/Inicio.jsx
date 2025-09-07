import React from 'react'
import Button from '../components/Button'
import { useNavigate } from "react-router-dom";
import "../css/Inicio.css"
import imagenInicio from "../assets/Imagen_Inicio.jpg";

export default function Inicio() {

    const navigate = useNavigate();
    return (
        <div className='inicio-container'>
            <div className='inicio-card'>
                <div className='inicio-left'>
                    <h1>Bienvenido a Reserva tu Cancha</h1>
                    <p>Administra tus reservas de canchas de manera fácil y eficiente.</p>
                    <div className="inicio-buttons">
                        <Button className="btn-login" onClick={() => navigate("/login")}>Iniciar Sesión</Button>
                        <Button className="btn-register" onClick={() => navigate("/register")}>Registrarse</Button>
                    </div>
                </div>
                <div className="inicio-right">
                    <img src={imagenInicio} alt="imagen de inicio" />
                </div>
            </div>
        </div>
    )
}
