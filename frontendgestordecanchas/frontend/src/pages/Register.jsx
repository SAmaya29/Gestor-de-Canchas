import React, { useState, useContext, useRef, useEffect } from "react";
import "../css/Register.css";
import Button from "../components/Button";
import Form from "../components/Form";
import { register as registerService } from "../api/authService";
import { getRoles } from "../api/rolService";
import { useNavigate } from "react-router-dom";

const Register = () => {
    const [nombre, setNombre] = useState("");
    const [correo, setCorreo] = useState("");
    const [telefono, setTelefono] = useState("");
    const [contrasena, setContrasena] = useState("");
    const [confirmar, setConfirmar] = useState("");
    const [roles, setRoles] = useState([]);
    const [rolSeleccionado, setRolSeleccionado] = useState("");
    const [rolPassword, setRolPassword] = useState("");
    const [errors, setErrors] = useState({});
    const [isLoading, setIsLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const navigate = useNavigate();
    const isMountedRef = useRef(true);

    // Cleanup al desmontar el componente
    useEffect(() => {
        isMountedRef.current = true;
        
        const fetchRoles = async () => {
            try {
                const response = await getRoles();
                if (isMountedRef.current) {
                    setRoles(response.data || []);
                }
            } catch (error) {
                console.error("Error cargando roles:", error);
            }
        };

        fetchRoles();

        return () => {
            isMountedRef.current = false;
        };
    }, []);

    // Validación de email
    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    // Validación de teléfono (solo números, 8-15 dígitos)
    const validatePhone = (phone) => {
        const phoneRegex = /^\d{8,15}$/;
        return phoneRegex.test(phone);
    };

    // Validación de contraseña (mínimo 6 caracteres)
    const validatePassword = (password) => {
        return password.length >= 6 && password.length <= 30;
    };

    // Validar todos los campos
    const validateFields = () => {
        const newErrors = {};

        if (!nombre.trim()) {
            newErrors.nombre = "El nombre es requerido";
        }

        if (!correo.trim()) {
            newErrors.correo = "El correo es requerido";
        } else if (!validateEmail(correo)) {
            newErrors.correo = "Por favor ingresa un correo válido";
        }

        if (!telefono.trim()) {
            newErrors.telefono = "El teléfono es requerido";
        } else if (!validatePhone(telefono)) {
            newErrors.telefono = "El teléfono debe tener entre 8 y 15 dígitos";
        }

        if (!contrasena) {
            newErrors.contrasena = "La contraseña es requerida";
        } else if (!validatePassword(contrasena)) {
            newErrors.contrasena = "La contraseña debe tener al menos 6 caracteres";
        }

        if (!confirmar) {
            newErrors.confirmar = "Confirma tu contraseña";
        } else if (contrasena !== confirmar) {
            newErrors.confirmar = "Las contraseñas no coinciden";
        }

        if (!rolSeleccionado) {
            newErrors.rol = "El rol es requerido";
        }

        if (rolSeleccionado === "ADMIN" && !rolPassword) {
            newErrors.rolPassword = "La contraseña del rol es requerida";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Verificar si el componente sigue montado
        if (!isMountedRef.current) return;

        // Limpiar mensajes previos
        setErrors({});
        setSuccessMessage("");

        // Validar campos
        if (!validateFields()) {
            return;
        }

        setIsLoading(true);

        try {
            const response = await registerService({ 
                nombre, 
                correo, 
                telefono, 
                contrasena, 
                rolNombre: rolSeleccionado, 
                rolPassword 
            });

            // Solo proceder si el componente sigue montado
            if (isMountedRef.current) {
                console.log("Registro exitoso:");
                setSuccessMessage("¡Registro exitoso! Redirigiendo al login...");
                // Limpiar formulario
                setNombre("");
                setCorreo("");
                setTelefono("");
                setContrasena("");
                setConfirmar("");
                setRolSeleccionado("");
                setRolPassword("");
                // Redirigir después de un momento para mostrar el mensaje
                setTimeout(() => {
                    if (isMountedRef.current) {
                        navigate("/login", { replace: true });
                    }
                }, 1500);
            }
        } catch (error) {
            // Solo actualizar estado si el componente sigue montado
            if (isMountedRef.current) {
                console.error("Error en el registro:", error);
                // Manejar errores específicos del backend
                if (error.response?.data?.message) {
                    setErrors({ general: error.response.data.message });
                } else if (error.response?.status === 409) {
                    setErrors({ correo: "Este correo ya está registrado" });
                } else {
                    setErrors({ general: "Error en el registro. Por favor intenta de nuevo." });
                }
                setIsLoading(false);
            }
        }
    };

    return (
        <div className="register-main">
            <div className="register-card">
                <h2 className="register-title">Crea tu cuenta</h2>
                <p className="register-subtitle">Únete hoy y desbloquea beneficios exclusivos diseñados para ti.</p>

                {successMessage && (
                    <div className="success-message">{successMessage}</div>
                )}

                {errors.general && (
                    <div className="error-message">{errors.general}</div>
                )}

                <Form className="register-form" onSubmit={handleSubmit}>
                    <label htmlFor="nombre">Nombre Completo</label>
                    <input
                        id="nombre"
                        type="text"
                        placeholder="John Doe"
                        value={nombre}
                        onChange={e => setNombre(e.target.value)}
                        className={errors.nombre ? 'error' : ''}
                        required
                    />
                    {errors.nombre && <div className="error-message">{errors.nombre}</div>}

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

                    <label htmlFor="telefono">Teléfono</label>
                    <input
                        id="telefono"
                        type="tel"
                        placeholder="3123456789"
                        value={telefono}
                        onChange={e => setTelefono(e.target.value)}
                        className={errors.telefono ? 'error' : ''}
                        required
                    />
                    {errors.telefono && <div className="error-message">{errors.telefono}</div>}

                    <label htmlFor="contrasena">Contraseña</label>
                    <input
                        id="contrasena"
                        type="password"
                        placeholder="********"
                        value={contrasena}
                        onChange={e => setContrasena(e.target.value)}
                        className={errors.contrasena ? 'error' : ''}
                        required
                    />
                    {errors.contrasena && <div className="error-message">{errors.contrasena}</div>}

                    <label htmlFor="confirmar">Confirmar Contraseña</label>
                    <input
                        id="confirmar"
                        type="password"
                        placeholder="********"
                        value={confirmar}
                        onChange={e => setConfirmar(e.target.value)}
                        className={errors.confirmar ? 'error' : ''}
                        required
                    />
                    {errors.confirmar && <div className="error-message">{errors.confirmar}</div>}

                    <label htmlFor="rolNombre">Rol</label>
                    <select
                        id="rolNombre"
                        value={rolSeleccionado}
                        onChange={(e) => setRolSeleccionado(e.target.value)}
                        className={errors.rol ? 'error' : ''}
                        required
                    >
                        <option value="">Seleccione un rol...</option>
                        {roles.map((rol) => (
                            <option key={rol.nombreRol} value={rol.nombreRol}>
                                {rol.nombreRol}
                            </option>
                        ))}
                    </select>
                    {errors.rol && <div className="error-message">{errors.rol}</div>}

                    {rolSeleccionado === "ADMIN" && (
                        <div className="admin-password-field">
                            <label htmlFor="rolPassword">Contraseña de Administrador</label>
                            <input
                                id="rolPassword"
                                type="password"
                                placeholder="Ingresa la contraseña de administrador"
                                value={rolPassword}
                                onChange={(e) => setRolPassword(e.target.value)}
                                className={errors.rolPassword ? 'error' : ''}
                                required
                            />
                            {errors.rolPassword && <div className="error-message">{errors.rolPassword}</div>}
                        </div>
                    )}
                    <Button type="submit" className="register-btn" disabled={isLoading}>
                        {isLoading ? 'Registrando...' : 'Registrar'}
                    </Button>
                </Form>
                <p className="register-login-link">
                    ¿Ya tienes una cuenta? <a href="/login">Iniciar sesión</a>
                </p>
            </div>
            <div className="register-img">
                <img src="/team-illustration.png" alt="Equipo trabajando juntos" />
            </div>
        </div>
    );
};

export default Register;