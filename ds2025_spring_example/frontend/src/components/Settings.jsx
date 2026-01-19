import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useSettings } from '../contexts/SettingsContext';
import { useNavigate } from 'react-router-dom';
import './../styles/Settings.css';

const Settings = () => {
  const { user, logout } = useAuth();
  const {
    updateData,
    loading,
    fetchLoading,
    error,
    success,
    deleteLoading,
    handleChange,
    handleSubmit,
    deleteAccount,
    fetchUserData
  } = useSettings();
  const navigate = useNavigate();

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
              onClick={deleteAccount}
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