import api from "./axiosConfig"

export const crearUsuario = (data) => api.post("/usuario/crearUsuario", data);
