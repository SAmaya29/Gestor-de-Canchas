import api from "./axiosConfig";

export const getUserReservas = async () => {
    return api.get("/reserva/user/reservas");
}

export const crearReserva = (reservaData) => {
    return api.post('/reserva/user/crearReserva', reservaData);
};

export const cancelarReserva = (reservaId) => {
    return api.post(`/reserva/user/cancelarReserva/${reservaId}`);
};