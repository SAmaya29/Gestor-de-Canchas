import React, { createContext, useState, useEffect } from "react";
import api from "../api/axiosConfig";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    // Se ejecuta cuando la app arranca
    useEffect(() => {
        const checkAuth = async () => {
            try {
                const token = localStorage.getItem("token");
                console.log("🔑 Checking auth with token:", token ? "EXISTS" : "NO TOKEN");
                
                if (token) {
                    console.log("📡 Calling /auth/user...");
                    const response = await api.get("/auth/user");
                    console.log("✅ Auth check successful:", response.data);
                    setUser(response.data);
                } else {
                    console.log("❌ No token found");
                    setUser(null);
                }
            } catch (error) {
                console.error("❌ Error verificando autenticación:", error);
                console.log("🧹 Cleaning token and user data");
                // Si hay error, limpiar token y usuario
                localStorage.removeItem("token");
                setUser(null);
            } finally {
                setLoading(false);
            }
        };

        checkAuth();
    }, []);

    const login = (token, userData) => {
        localStorage.setItem("token", token);
        setUser(userData);
    };

    const logout = () => {
        localStorage.removeItem("token");
        setUser(null);
    };

    // Añadir protección contra renderizado si hay errores
    if (loading) {
        return (
            <div style={{ 
                display: 'flex', 
                justifyContent: 'center', 
                alignItems: 'center', 
                height: '100vh' 
            }}>
                Cargando...
            </div>
        );
    }

    return (
        <AuthContext.Provider value={{ user, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
};
