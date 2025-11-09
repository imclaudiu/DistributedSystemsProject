import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { userService } from '../services/api';
import { useNavigate } from 'react-router-dom';
import './../styles/Dashboard.css';

const Settings = () => {
    const [persons, setPersons] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [errorDetails, setErrorDetails] = useState('');
    const { user, logout, isAdmin } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (isAdmin()) {
            fetchPersons();
        }
    }, [isAdmin]);

    const fetchPersons = async () => {
        setLoading(true);
        setError('');
        setErrorDetails('');
        try {
            console.log('Fetching persons...');
            const response = await userService.getAllPersons();
            console.log('Persons response:', response);
            setPersons(response.data);
        } catch (error) {
            console.error('Error fetching persons:', error);

            let errorMessage = 'Failed to fetch persons. ';
            let details = '';

            if (error.response) {
                // Server responded with error status
                errorMessage += `Server error: ${error.response.status}`;
                details = `Status: ${error.response.status}\nData: ${JSON.stringify(error.response.data, null, 2)}`;
            } else if (error.request) {
                // Request was made but no response received
                errorMessage += 'No response from server.';
                details = 'The request was made but no response was received. Check if the backend is running.';
            } else {
                // Something else happened
                errorMessage += 'Unexpected error.';
                details = error.message;
            }

            setError(errorMessage);
            setErrorDetails(details);
        }
        setLoading(false);
    };

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    const handleSettings = () =>{
        navigate('/settings');
    }

    const displayRoles = () => {
        if (!user || !user.roles) return 'No roles';

        if (Array.isArray(user.roles)) {
            return user.roles.join(', ');
        } else if (typeof user.roles === 'string') {
            return user.roles;
        } else {
            return JSON.stringify(user.roles);
        }
    };

    if (!user) {
        return (
            <div className="dashboard-container">
                <div className="error-message">No user data available. Please log in again.</div>
                <button onClick={() => navigate('/login')}>Go to Login</button>
            </div>
        );
    }

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <h1>Dashboard</h1>
                <div className="user-info">
                    <span>Welcome, {user?.sub || user?.username || 'User'}!</span>
                    <button onClick={handleLogout} className="logout-button">
                        Logout
                    </button>
                    <button onClick={handleSettings} className={"settings-button"}>
                        Settings
                    </button>
                </div>

            </header>

            <div className="dashboard-content">
                <div className="user-info-panel">
                    <div className="roles-info">
                        <strong>Roles:</strong> {displayRoles()}
                    </div>
                    <div className="token-info">
                        <strong>Token Preview:</strong> {user?.sub ? 'Valid token' : 'Invalid token'}
                    </div>
                </div>

                {isAdmin() ? (
                    <div className="admin-section">
                        <h2>Person Management (Admin)</h2>

                        <div className="action-buttons">
                            <button
                                onClick={fetchPersons}
                                disabled={loading}
                                className="refresh-button"
                            >
                                {loading ? 'Loading...' : 'Refresh Persons'}
                            </button>
                            <button
                                onClick={() => {
                                    console.log('Current user:', user);
                                    console.log('Token:', localStorage.getItem('token'));
                                }}
                                className="debug-button"
                            >
                                Debug Info
                            </button>
                        </div>

                        {error && (
                            <div className="error-panel">
                                <div className="error-message">{error}</div>
                                {errorDetails && (
                                    <details className="error-details">
                                        <summary>Error Details</summary>
                                        <pre>{errorDetails}</pre>
                                    </details>
                                )}
                            </div>
                        )}

                        {persons.length > 0 ? (
                            <div className="persons-table">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {persons.map((person) => (
                                        <tr key={person.id}>
                                            <td>{person.id}</td>
                                            <td>{person.name || 'N/A'}</td>
                                            <td>{person.email || 'N/A'}</td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        ) : (
                            !loading && <p>No persons found. Click "Refresh Persons" to load data.</p>
                        )}
                    </div>
                ) : (
                    <div className="user-section">
                        <h2>Welcome to your Dashboard</h2>
                        <p>You don't have admin privileges to view the person management section.</p>
                        <p>Your roles: {displayRoles()}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default Settings;