// src/routes/AppRoutes.jsx
import { Routes, Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Inicio from "../pages/Inicio";
import Reservar from "../pages/Reservar"

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Inicio />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route
                path="/home"
                element={
                    <PrivateRoute>
                        <Home />
                    </PrivateRoute>
                }
            />
            <Route
                path="/reservar"
                element={
                    <PrivateRoute>
                        <Reservar />
                    </PrivateRoute>
                }
            />
        </Routes>
    );
}

export default AppRoutes;