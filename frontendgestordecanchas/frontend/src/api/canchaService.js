import api from './axiosConfig';

// Obtener todas las canchas disponibles
export const getCanchas = () => {
    console.log('📡 Fetching canchas...');
    return api.get('/cancha/user/canchas');
};

// Obtener detalles de una cancha específica
export const getCanchaById = (id) => {
    console.log(`📡 Fetching cancha ${id}...`);
    return api.get(`/cancha/user/cancha/${id}`);
};