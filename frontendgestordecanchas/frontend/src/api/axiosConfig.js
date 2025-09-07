import axios from "axios";

const backendUrl = process.env.BACKEND_URL;

const api = axios.create({
  baseURL: backendUrl, // tu backend
});

export default api;