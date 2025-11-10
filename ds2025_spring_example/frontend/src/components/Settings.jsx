import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import {authService, userService} from '../services/api';
import { useNavigate } from 'react-router-dom';
import './../styles/Settings.css';

const Settings = () => {
    const [updateData, setUpdateData] = useState({
        username: '',
        name: '',
        address: '',
        age: '',
        password: '',
        confirmPassword: ''
    });
    const [loading, setLoading] = useState(false);
    const [fetchLoading, setFetchLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const { user, logout, updateUser } = useAuth();
    const [deleteLoading, setDeleteLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        fetchUserData();
    }, [user]);

    const fetchUserData = async () => {
        if (!user?.id && !user?.sub) return;

        setFetchLoading(true);
        try {
            const userId = user?.id || user?.sub;
            const response = await userService.getPerson(userId);

            setUpdateData({
                name: response.data.name || '',
                address: response.data.address || '',
                age: response.data.age || '',
            });
        } catch (error) {
            console.error('Error fetching user data:', error);
            if (error.response?.status !== 404) {
                setError('Failed to load user data');
            }
        }
        setFetchLoading(false);
    };

    const handleChange = (e) => {
        setUpdateData({
            ...updateData,
            [e.target.name]: e.target.value
        });
        // Clear messages when user starts typing
        setError('');
        setSuccess('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        setSuccess('');

        try {
            const userId = user?.id || user?.sub;
            const response = await userService.updatePerson(userId, {
                name: updateData.name,
                address: updateData.address,
            });

            setSuccess('Profile updated successfully!');

            // Update auth context if needed
            if (updateUser) {
                updateUser({ ...user, name: updateData.name,
                address: updateData.address});
            }

        } catch (error) {
            console.error('Error updating profile:', error);
            let errorMessage = 'Failed to update profile. ';

            if (error.response?.status === 400) {
                errorMessage = 'Invalid data. Please check your inputs.';
            } else if (error.response?.status === 401) {
                errorMessage = 'Unauthorized. Please log in again.';
            } else if (error.response?.status === 403) {
                errorMessage = 'You do not have permission to update this profile.';
            } else if (error.response?.status === 404) {
                errorMessage = 'Profile not found.';
            }

            setError(errorMessage);
        }
        setLoading(false);
    };

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    const handleBackToDashboard = () => {
        navigate('/dashboard');
    };

    const displayRoles = () => {
        if (!user || !user.roles) return 'No roles';
        return Array.isArray(user.roles) ? user.roles.join(', ') : user.roles;
    };

    const deleteAccount = async () => {
        if (!window.confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
            return;
        }

        console.log('Current user object:', user);
        console.log('Available user properties:', Object.keys(user || {}));
        console.log('User sub:', updateData.name);
        console.log('User id:', user?.roles);
        console.log('User username:', user.sub);

        setDeleteLoading(true);
        try {

            // Option B: If above fails, try with the user ID from your token
            const userId = user?.sub;
            await authService.deleteAuth(user?.sub);
            await userService.deletePerson(user?.id);

            console.log('Account deleted successfully');
            logout();
            navigate('/login');
            alert('Your account has been deleted successfully.');

        } catch (error) {
            console.error('Error deleting account:', error);

            let errorMessage = 'Failed to delete account. ';
            if (error.response?.status === 401) {
                errorMessage = 'Unauthorized. Please log in again.';
            } else if (error.response?.status === 403) {
                errorMessage = 'You do not have permission to delete this account.';
            } else if (error.response?.status === 404) {
                errorMessage = 'Account not found.';
            }

            setError(errorMessage);
        }
        setDeleteLoading(false);
    };

    if (!user) {
        return (
            <div className="settings-container">
                <div className="error-message">No user data available. Please log in again.</div>
                <button onClick={() => navigate('/login')} className="btn btn-primary">
                    Go to Login
                </button>
            </div>
        );
    }

    return (
        <div className="settings-container">
            <header className="settings-header">
                <h1>Settings</h1>
                <div className="user-info">
                    <span>Welcome, {user?.name || user?.sub || user?.username || 'User'}!</span>
                    <button onClick={handleBackToDashboard} className="btn btn-secondary">
                        Back to Dashboard
                    </button>
                    <button onClick={handleLogout} className="btn btn-logout">
                        Logout
                    </button>
                </div>
            </header>

            <div className="settings-content">
                <div className="user-info-panel">
                    <div className="roles-info">
                        <strong>Roles:</strong> {displayRoles()}
                    </div>
                    <div className="user-id">
                        <strong>User ID:</strong> {user?.sub || user?.id}
                    </div>
                </div>

                <div className="settings-form-section">
                    <h2>Update Your Profile</h2>

                    {error && <div className="error-message">{error}</div>}
                    {success && <div className="success-message">{success}</div>}

                    {fetchLoading ? (
                        <div className="loading-message">Loading your profile...</div>
                    ) : (
                        <form onSubmit={handleSubmit} className="settings-form">
                            <div className="form-group">
                                <label htmlFor="name">Name:</label>
                                <input
                                    type="text"
                                    id="name"
                                    name="name"
                                    value={updateData.name}
                                    onChange={handleChange}
                                    placeholder="Enter your name"
                                    disabled={loading}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="address">Address:</label>
                                <input
                                    type="text"
                                    id="address"
                                    name="address"
                                    value={updateData.address}
                                    onChange={handleChange}
                                    placeholder="Enter your address"
                                    disabled={loading}
                                />
                            </div>

                            <div className="form-actions">
                                <button
                                    type="submit"
                                    disabled={loading}
                                    className="btn btn-primary"
                                >
                                    {loading ? 'Updating...' : 'Update Profile'}
                                </button>

                                <button
                                    type="button"
                                    onClick={fetchUserData}
                                    disabled={loading}
                                    className="btn btn-secondary"
                                >
                                    Reset Changes
                                </button>
                            </div>
                        </form>
                    )}
                </div>

                <div className="danger-zone">
                    <h3>Danger Zone</h3>
                    <div className="danger-zone-content">
                        <p>Once you delete your account, there is no going back. Please be certain.</p>
                        <button
                            onClick={(deleteAccount)}
                            className="btn btn-danger"
                        >
                            Delete Account
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Settings;