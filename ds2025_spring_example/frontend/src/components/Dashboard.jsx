import React, {useState} from 'react';
import {useDashboard} from '../contexts/DashboardContext';
import {useAuth} from '../contexts/AuthContext';
import {useNavigate} from 'react-router-dom';
import './../styles/Dashboard.css';

const Dashboard = () => {
    const {
        persons,
        personDetails,
        devices,
        personLoading,
        deviceLoading,
        loading,
        error,
        errorDetails,
        fetchPersons,
        createDevice,
        updateDevice,
        deleteDevice,
        setError
    } = useDashboard();

    const {user, logout, isAdmin} = useAuth();
    const navigate = useNavigate();
    
    const [showCreateDeviceModal, setShowCreateDeviceModal] = useState(false);
    const [showUpdateDeviceModal, setShowUpdateDeviceModal] = useState(false);
    const [newDevice, setNewDevice] = useState({
        name: '',
        maxConsumption: '',
        ownerId: ''
    });
    const [updateDeviceData, setUpdateDeviceData] = useState({
        id: '',
        name: '',
        maxConsumption: '',
    });

    const handleDeleteDevice = async (deviceId) => {
        if (!window.confirm('Are you sure you want to delete this device? This action cannot be undone.')) {
            return;
        }

        try {
            await deleteDevice(deviceId);
            alert('Device deleted successfully!');
        } catch (error) {
            console.error('Error deleting device:', error);
            let errorMessage = 'Failed to delete device. ';

            if (error.response?.status === 401) {
                errorMessage = 'Unauthorized. Please log in again.';
            } else if (error.response?.status === 403) {
                errorMessage = 'You do not have permission to delete this device.';
            } else if (error.response?.status === 404) {
                errorMessage = 'Device not found.';
            }

            setError(errorMessage);
        }
    };

    const handleUpdateDeviceClick = (device) => {
        setUpdateDeviceData({
            id: device.id,
            name: device.name || '',
            maxConsumption: device.maxConsumption || '',
        });
        setShowUpdateDeviceModal(true);
    };

    const handleUpdateDeviceSubmit = async () => {
        if (!updateDeviceData.name && !updateDeviceData.maxConsumption) {
            alert('Please fill in at least one field to update');
            return;
        }

        try {
            const updatePayload = {};
            if (updateDeviceData.name) updatePayload.name = updateDeviceData.name;
            if (updateDeviceData.maxConsumption) updatePayload.maxConsumption = parseInt(updateDeviceData.maxConsumption);

            await updateDevice(updateDeviceData.id, updatePayload);
            alert('Device updated successfully!');
            handleCloseUpdateModal();
        } catch (error) {
            console.error('Error updating device:', error);
            alert('Failed to update device');
        }
    };

    const handleCloseUpdateModal = () => {
        setShowUpdateDeviceModal(false);
        setUpdateDeviceData({
            id: '',
            name: '',
            maxConsumption: '',
        });
    };

    const handleUpdateInputChange = (e) => {
        const { name, value } = e.target;
        setUpdateDeviceData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleCreateDeviceClick = () => {
        setShowCreateDeviceModal(true);
    };

    const handleCloseModal = () => {
        setShowCreateDeviceModal(false);
        setNewDevice({ name: '', maxConsumption: '', ownerId: '' });
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewDevice(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleCreateDeviceSubmit = async () => {
        if (!newDevice.name || !newDevice.maxConsumption) {
            alert('Please fill in all fields');
            return;
        }

        try {
            await createDevice({
                ...newDevice,
                maxConsumption: parseInt(newDevice.maxConsumption),
                ownerID: user.id
            });
            alert('Device created successfully!');
            handleCloseModal();
        } catch (error) {
            console.error('Error creating device:', error);
            alert('Failed to create device');
        }
    };

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    const handleSettings = () => {
        navigate('/settings');
    }

    const displayRoles = () => {
        if (!user || !user.roles) return 'No roles';
        return user.roles;
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
                    <button onClick={handleSettings} className="settings-button">
                        Settings
                    </button>
                </div>
            </header>

            <div className="dashboard-content">
                <div className="user-info-panel">
                    <div className="roles-info">
                        <div className="person-details-section">
                            <h3>Profile Details</h3>
                            {personLoading ? (
                                <p>Loading profile details...</p>
                            ) : personDetails ? (
                                <div>
                                    <p><strong>Name:</strong> {personDetails.name}</p>
                                    <p><strong>Address:</strong> {personDetails.address}</p>
                                    <p><strong>Age:</strong> {personDetails.age}</p>
                                    <strong>Roles:</strong> {displayRoles()}
                                </div>
                            ) : (
                                <p>No profile details found. Please complete your profile.</p>
                            )}
                        </div>
                    </div>
                </div>

                <div className="user-section">
                    <h2>Welcome to your Dashboard</h2>

                    {/* Create Device Modal */}
                    {showCreateDeviceModal && (
                        <div className="modal-overlay">
                            <div className="modal-content">
                                <h3>Create New Device</h3>
                                <div className="input-group">
                                    <label>Device Name:</label>
                                    <input
                                        type="text"
                                        name="name"
                                        value={newDevice.name}
                                        onChange={handleInputChange}
                                        placeholder="Enter device name"
                                    />
                                </div>
                                <div className="input-group">
                                    <label>Max Consumption:</label>
                                    <input
                                        type="number"
                                        name="maxConsumption"
                                        value={newDevice.maxConsumption}
                                        onChange={handleInputChange}
                                        placeholder="Enter max consumption"
                                    />
                                </div>
                                <div className="modal-buttons">
                                    <button onClick={handleCreateDeviceSubmit} className="submit-button">
                                        Create Device
                                    </button>
                                    <button onClick={handleCloseModal} className="cancel-button">
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        </div>
                    )}

                    {/* Update Device Modal */}
                    {showUpdateDeviceModal && (
                        <div className="modal-overlay">
                            <div className="modal-content">
                                <h3>Update Device</h3>
                                <div className="input-group">
                                    <label>Device Name:</label>
                                    <input
                                        type="text"
                                        name="name"
                                        value={updateDeviceData.name}
                                        onChange={handleUpdateInputChange}
                                        placeholder="Enter new device name (optional)"
                                    />
                                </div>
                                <div className="input-group">
                                    <label>Max Consumption:</label>
                                    <input
                                        type="number"
                                        name="maxConsumption"
                                        value={updateDeviceData.maxConsumption}
                                        onChange={handleUpdateInputChange}
                                        placeholder="Enter new max consumption (optional)"
                                    />
                                </div>
                                <div className="modal-buttons">
                                    <button onClick={handleUpdateDeviceSubmit} className="submit-button">
                                        Update Device
                                    </button>
                                    <button onClick={handleCloseUpdateModal} className="cancel-button">
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        </div>
                    )}

                    <button
                        onClick={handleCreateDeviceClick}
                        className="create-device-button"
                    >
                        Create new device
                    </button>
                    {devices.length > 0 ? (
                        <div className="device-table">
                            <table>
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>MaxConsumption</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                {devices.map((deviceItem) => (
                                    <tr key={deviceItem.id}>
                                        <td>{deviceItem.name || 'N/A'}</td>
                                        <td>{deviceItem.maxConsumption || 'N/A'}</td>
                                        <td>
                                            <button
                                                onClick={() => handleUpdateDeviceClick(deviceItem)}
                                                className="update-device-button"
                                                title="Update Device"
                                            >
                                                Update
                                            </button>
                                            <button
                                                onClick={() => handleDeleteDevice(deviceItem.id)}
                                                className="delete-device-button"
                                                title="Delete Device"
                                            >
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    ) : (
                        !deviceLoading && <p>No devices found!</p>
                    )}
                </div>

                {isAdmin() && (
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
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        ) : (
                            !loading && <p>No persons found. Click "Refresh Persons" to load data.</p>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
};

export default Dashboard;