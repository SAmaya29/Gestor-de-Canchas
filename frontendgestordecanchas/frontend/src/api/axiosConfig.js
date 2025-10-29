import axios from "axios";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

const api = axios.create({
  baseURL: backendUrl,
  headers: {
    "Content-Type": "application/json",
  },
});


// Interceptor para añadir el token automáticamente
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    console.log("🔑 Token from localStorage:", token ? `${token.substring(0, 20)}...` : "NO TOKEN");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
      console.log("✅ Authorization header set:", config.headers["Authorization"].substring(0, 30) + "...");
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;