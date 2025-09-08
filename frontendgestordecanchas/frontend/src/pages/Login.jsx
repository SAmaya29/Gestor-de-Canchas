import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import "../css/Login.css";
import Form from "../components/Form"
import Button from "../components/Button";
import { login as loginService } from "../api/userService";
import { AuthContext } from "../context/AuthContext";


export default function Login() {
    const [correo, setCorreo] = useState("");
    const [contrasena, setContrasena] = useState("");
    const [errors, setErrors] = useState({});
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const { login } = useContext(AuthContext);

    // Validación de email
    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    // Validar campos
    const validateFields = () => {
        const newErrors = {};

        if (!correo.trim()) {
            newErrors.correo = "El correo es requerido";
        } else if (!validateEmail(correo)) {
            newErrors.correo = "Por favor ingresa un correo válido";
        }

        if (!contrasena) {
            newErrors.contrasena = "La contraseña es requerida";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Limpiar errores previos
        setErrors({});

        // Validar campos
        if (!validateFields()) {
            return;
        }

        setIsLoading(true);

        loginService({ correo, contrasena })
            .then(response => {
                const { token, ...userData } = response.data;
                login(token, userData);
                console.log("Login successful");
                navigate("/home");
            })
            .catch(error => {
                console.error("Login failed:", error);

                // Manejar errores específicos del backend
                if (error.response?.status === 401) {
                    setErrors({ general: "Credenciales incorrectas. Verifica tu correo y contraseña." });
                } else if (error.response?.data?.message) {
                    setErrors({ general: error.response.data.message });
                } else if (error.response?.status === 404) {
                    setErrors({ correo: "No existe una cuenta con este correo" });
                } else {
                    setErrors({ general: "Error en el login. Por favor intenta de nuevo." });
                }
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <img src="/logo.png" alt="Logo" className="login-logo" />
                <h2 className="login-title">Bienvenido Otra Vez</h2>
                <p className="login-subtitle">Inicia sesión en tu cuenta</p>

                {errors.general && (
                    <div className="error-message">{errors.general}</div>
                )}

                <Form className="login-form" onSubmit={handleSubmit}>
                    <label htmlFor="correo">Correo Electrónico</label>
                    <input
                        id="correo"
                        type="email"
                        placeholder="tu.email@ejemplo.com"
                        value={correo}
                        onChange={e => setCorreo(e.target.value)}
                        className={errors.correo ? 'error' : ''}
                        required
                    />
                    {errors.correo && <div className="error-message">{errors.correo}</div>}

                    <label htmlFor="contrasena">Contraseña</label>
                    <input
                        id="contrasena"
                        type="password"
                        placeholder="••••••••"
                        value={contrasena}
                        onChange={e => setContrasena(e.target.value)}
                        className={errors.contrasena ? 'error' : ''}
                        required
                    />
                    {errors.contrasena && <div className="error-message">{errors.contrasena}</div>}

                    <Button type="submit" className="login-btn" disabled={isLoading}>
                        {isLoading && <span className="loading-spinner"></span>}
                        {isLoading ? 'Iniciando sesión...' : 'Iniciar Sesión'}
                    </Button>
                </Form>
                <p className="register-link">
                    ¿No tienes cuenta? <a href="/register">Regístrate aquí</a>
                </p>
            </div>
        </div>
    );
}