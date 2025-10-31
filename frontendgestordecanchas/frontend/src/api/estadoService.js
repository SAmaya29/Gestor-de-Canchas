import  api  from './axiosConfig';

export const getEstados = () => {
    return api.get('/estado/user/estados');
};