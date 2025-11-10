import React, { createContext, useState, useContext, useEffect } from 'react';
import {authService, parseJwt, userService} from '../services/api';

const AuthContext = createContext();

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
}

export function AuthProvider({ children }) {
    const [token, setToken] = useState(() => localStorage.getItem('token'));
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        console.log('AuthProvider mounted, token:', token);

        if (token) {
            try {
                const userData = parseJwt(token);
                console.log('Parsed user data:', userData);

                if (userData && userData.exp * 1000 > Date.now()) {
                    setUser(userData);
                } else {
                    console.log('Token expired, logging out');
                    logout();
                }
            } catch (error) {
                console.error('Error parsing token:', error);
                logout();
            }
        }
        setLoading(false);
    }, [token]);

    const register = async (registerData) => {
        try {
            console.log('Attempting registration with:', registerData);

            // 1. Register in auth service
            const authResponse = await authService.register({
                username: registerData.username,
                password: registerData.password,
                role: 'user'
            });

            console.log('Full auth response:', authResponse);

            // 2. Extract UUID from response body (new backend format)
            const uuid = authResponse.data?.id;
            console.log('UUID from response body:', uuid);

            if (!uuid) {
                throw new Error('No user ID returned from auth service');
            }

            // 3. Create user in person service with the same UUID
            try {
                const userResponse = await userService.createPerson({
                    id: uuid,
                    name: registerData.name,
                    address: registerData.address,
                    age: registerData.age,
                });
                console.log('User created in person service:', userResponse);
            } catch (userError) {
                console.error('Failed to create user in person service:', userError);
                // Don't fail registration - user can complete profile later
                // You might want to implement rollback here if needed
            }

            return {
                success: true,
                userId: uuid
            };

        } catch (error) {
            console.error('Registration error:', error);

            let errorMessage = 'Registration failed';

            if (error.response?.status === 409) {
                errorMessage = 'Username already exists';
            } else if (error.response?.data?.message) {
                errorMessage = error.response.data.message;
            } else if (error.message) {
                errorMessage = error.message;
            }

            return {
                success: false,
                error: errorMessage
            };
        }
    };

    const login = async (loginData) => {
        try {
            console.log('Attempting login with:', loginData);
            const response = await authService.login(loginData);
            const { token } = response.data;

            localStorage.setItem('token', token);
            setToken(token);

            const userData = parseJwt(token);
            setUser(userData);

            console.log('Login successful, user:', userData);
            return { success: true };
        } catch (error) {
            console.error('Login error:', error);
            return {
                success: false,
                error: error.response?.data?.message || 'Login failed'
            };
        }
    };

    const logout = () => {
        console.log('Logging out');
        localStorage.removeItem('token');
        setToken(null);
        setUser(null);
    };

    const isAdmin = () => {
        if (!user || !user.roles) {
            console.log('No user or roles found');
            return false;
        }

        // Handle different role formats
        let rolesArray = [];

        if (Array.isArray(user.roles)) {
            rolesArray = user.roles;
        } else if (typeof user.roles === 'string') {
            // If roles is a string, split by comma or space
            rolesArray = user.roles.split(/[, ]+/);
        } else {
            console.log('Unexpected roles format:', user.roles);
            return false;
        }

        // Check for admin role in various possible formats
        const isAdminUser = rolesArray.some(role =>
            role === 'ROLE_admin' ||
            role === 'admin' ||
            role === 'ADMIN' ||
            role.includes('admin')
        );

        console.log('isAdmin check - roles:', rolesArray, 'result:', isAdminUser);
        return isAdminUser;
    };

    const value = {
        token,
        user,
        login,
        logout,
        register,
        isAdmin,
        loading
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}