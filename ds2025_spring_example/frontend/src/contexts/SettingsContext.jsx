import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { authService, userService } from '../services/api';

const SettingsContext = createContext();

export const useSettings = () => {
  const context = useContext(SettingsContext);
  if (!context) {
    throw new Error('useSettings must be used within a SettingsProvider');
  }
  return context;
};

export const SettingsProvider = ({ children }) => {
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

      if (updateUser) {
        updateUser({ 
          ...user, 
          name: updateData.name,
          address: updateData.address
        });
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

  const deleteAccount = async () => {
    if (!window.confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
      return;
    }


    
    // VEZI CA LA DELETE ACCOUNT FACE FIGURI CA NU GASESTE DEVICEURI
    setDeleteLoading(true);
    try {
      await authService.deleteAuth(user?.sub);
      console.log('Account deleted successfully');
      logout();
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

  const value = {
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
  };

  return (
    <SettingsContext.Provider value={value}>
      {children}
    </SettingsContext.Provider>
  );
};