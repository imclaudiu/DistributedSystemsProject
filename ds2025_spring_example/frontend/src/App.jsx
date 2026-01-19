import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import { DashboardProvider } from './contexts/DashboardContext';
import { WebSocketProvider } from './contexts/WebSocketContext';
import Login from './components/Login';
import Dashboard from './components/Dashboard.jsx';
import './styles/App.css';
import Register from "./components/Register.jsx";
import Settings from "./components/Settings.jsx";
import { SettingsProvider } from './contexts/SettingsContext.jsx';

function ProtectedRoute({ children }) {
    const { token, loading } = useAuth();

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    return token ? children : <Navigate to="/login" />;
}

function PublicRoute({ children }) {
    const { token } = useAuth();
    return !token ? children : <Navigate to="/dashboard" />;
}

function App() {
    return (
        <AuthProvider>
            <WebSocketProvider>
                <DashboardProvider>
                    <SettingsProvider>
                        <Router>
                            <div className="App">
                                <Routes>
                                    <Route
                                        path="/login"
                                        element={
                                            <PublicRoute>
                                                <Login />
                                            </PublicRoute>
                                        }
                                    />
                                    <Route path="/register" element={<Register />} />
                                    <Route 
                                        path="/settings" 
                                        element={
                                            <ProtectedRoute>
                                                <Settings />
                                            </ProtectedRoute>
                                        } 
                                    />
                                    <Route
                                        path="/dashboard"
                                        element={
                                            <ProtectedRoute>
                                                <Dashboard />
                                            </ProtectedRoute>
                                        }
                                    />
                                    <Route path="/" element={<Navigate to="/dashboard" />} />
                                    <Route path="*" element={<Navigate to="/dashboard" />} />
                                </Routes>
                            </div>
                        </Router>
                    </SettingsProvider> 
                </DashboardProvider>
            </WebSocketProvider>
        </AuthProvider>
    );
}

export default App;