import api from "./axiosConfig";

export const getRoles = () => {
    return api.get("/rol/roles");
}