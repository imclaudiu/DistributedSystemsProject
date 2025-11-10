import axios from 'axios';

// Base URLs for different services
const AUTH_BASE_URL = 'http://localhost:8080/auth';
const USER_BASE_URL = 'http://localhost:8081/person';
const DEVICE_BASE_URL = 'http://localhost:8083/device';

// Create axios instances for different services
const authApi = axios.create({
    baseURL: AUTH_BASE_URL,
});

const userApi = axios.create({
    baseURL: USER_BASE_URL,
    timeout: 10000, // 10 second timeout
});

const deviceApi = axios.create({
    baseURL: DEVICE_BASE_URL,
});

// Add token to requests for user service
userApi.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
            console.log('Adding token to request:', token.substring(0, 20) + '...');
        } else {
            console.warn('No token found for request');
        }
        return config;
    },
    (error) => {
        console.error('Request interceptor error:', error);
        return Promise.reject(error);
    }
);

// Handle responses and errors
userApi.interceptors.response.use(
    (response) => {
        console.log('Response received:', response.status, response.config.url);
        return response;
    },
    (error) => {
        console.error('Response error:', {
            status: error.response?.status,
            statusText: error.response?.statusText,
            data: error.response?.data,
            url: error.config?.url
        });

        if (error.response?.status === 401 || error.response?.status === 403) {
            console.log('Authentication error, redirecting to login');
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

// Auth service methods
export const authService = {
    login: (loginData) => authApi.post('/login', loginData),
    register: (authData) => authApi.post('/add', authData),
    checkPassword: (loginData) => authApi.post('/checkPass', loginData),
    deleteAuth: (username) => authApi.delete(`/delete/${username}`),
    updateAuth: (id, authData) => authApi.patch(`/update/${id}`)
};

// User service methods
export const userService = {
    getAllPersons: () => userApi.get('/getAll'),
    getPerson: (id) => userApi.get(`/get/${id}`),
    createPerson: (personData) => userApi.post('/add', personData),
    updatePerson: (id, personData) => userApi.patch(`/update/${id}`, personData),
    deletePerson: (id) => userApi.delete(`/delete/${id}`),
};

//Device service methods
export const deviceService = {
    findByOwnerId: (id) => deviceApi.get(`/findByOwnerId/${id}`),
    createDevice: (deviceData) => deviceApi.post('/add', deviceData),
    // getAllPersons: () => userApi.get('/getAll'),
    // getPerson: (id) => userApi.get(`/get/${id}`),
    // createPerson: (personData) => userApi.post('/add', personData),
    updateDevice: (id, deviceData) => deviceApi.patch(`/update/${id}`, deviceData),
    deleteDevice: (id) => deviceApi.delete(`/delete/${id}`),
};

// Utility to parse JWT token
export const parseJwt = (token) => {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
            atob(base64)
                .split('')
                .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                .join('')
        );

        const parsed = JSON.parse(jsonPayload);
        console.log('JWT parsed successfully. Roles:', parsed.roles);
        return parsed;
    } catch (error) {
        console.error('Error parsing JWT:', error);
        return null;
    }
};