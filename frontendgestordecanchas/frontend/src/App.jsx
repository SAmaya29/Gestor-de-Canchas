import React from "react";
import { BrowserRouter as Router } from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import { AuthProvider } from "./context/AuthContext";
import ErrorBoundary from "./components/ErrorBoundary";

export default function App() {
    return (
        <Router>
            <AuthProvider>
                <ErrorBoundary>
                    <AppRoutes />
                </ErrorBoundary>
            </AuthProvider>
        </Router>
    );
}