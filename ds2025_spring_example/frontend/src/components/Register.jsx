import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import './../styles/Register.css';

const Register = () => {
    const [registerData, setRegisterData] = useState({
        username: '',
        name: '',
        address: '',
        age: '',
        password: '',
        confirmPassword: ''
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const { register } = useAuth();
    const navigate = useNavigate();

    const handleChange = (e) => {
        setRegisterData({
            ...registerData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        // Basic validation
        if (registerData.password !== registerData.confirmPassword) {
            setError('Passwords do not match');
            setLoading(false);
            return;
        }

        if (registerData.password.length < 6) {
            setError('Password must be at least 6 characters long');
            setLoading(false);
            return;
        }

        if (registerData.username.length < 3) {
            setError('Username must be at least 3 characters long');
            setLoading(false);
            return;
        }

        if(registerData.name.length < 3){
            setError('Name must be at least 3 characters long');
            setLoading(false);
            return;
        }

        if(registerData.age <= 18){
            setError('At least age 18!');
            setLoading(false);
            return;
        }

        if(registerData.address.length < 1){
            setError('Introduce address!');
            setLoading(false);
            return;
        }

        try {
            // Call register function with "user" role always
            const result = await register({
                username: registerData.username,
                name: registerData.name,
                address: registerData.address,
                age: registerData.age,
                password: registerData.password,

                role: 'user' // Always set role to 'user'
            });

            if (result.success) {
                // Registration successful, redirect to login
                navigate('/login', {
                    state: {
                        message: 'Registration successful! Please log in.'
                    }
                });
            } else {
                setError(result.error);
            }
        } catch (error) {
            setError('Registration failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="register-container">
            <div className="register-form">
                <h2>Create Account</h2>
                {error && <div className="error-message">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            value={registerData.username}
                            onChange={handleChange}
                            required
                            minLength="3"
                            placeholder="Enter your username"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="name">Name:</label>
                        <input
                            type="text"
                            id="name"
                            name="name"
                            value={registerData.name}
                            onChange={handleChange}
                            required
                            minLength="3"
                            placeholder="Enter your name"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="address">Address:</label>
                        <input
                            type="text"
                            id="address"
                            name="address"
                            value={registerData.address}
                            onChange={handleChange}
                            required
                            minLength="1"
                            placeholder="Enter your address"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="age">Age:</label>
                        <input
                            type="number"
                            id="age"
                            name="age"
                            value={registerData.age}
                            onChange={handleChange}
                            required
                            minLength="2"
                            placeholder="Enter age"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={registerData.password}
                            onChange={handleChange}
                            required
                            minLength="6"
                            placeholder="Enter your password"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="confirmPassword">Confirm Password:</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            value={registerData.confirmPassword}
                            onChange={handleChange}
                            required
                            minLength="6"
                            placeholder="Confirm your password"
                        />
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="register-button"
                    >
                        {loading ? 'Creating Account...' : 'Register'}
                    </button>

                    <div className="login-link">
                        Already have an account? <a href="/login">Login here</a>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Register;