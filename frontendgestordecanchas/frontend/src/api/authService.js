import api from "./axiosConfig";


export const login = async ({ correo, contrasena }) => {
    return api.post("/auth/login", { correo, contrasena });
};

export const register = async ({ nombre, correo, telefono, contrasena }) => {
    return api.post("/auth/registro", { nombre, correo, telefono, contrasena });
};
