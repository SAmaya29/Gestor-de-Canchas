import api from "./axiosConfig";

export const getUserReservas = async () => {
    return api.get("/reserva/user/reservas");
}