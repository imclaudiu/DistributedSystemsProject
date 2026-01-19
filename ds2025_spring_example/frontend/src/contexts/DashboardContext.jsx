import React, {createContext, useContext, useState, useEffect} from 'react';
import {useAuth} from '../contexts/AuthContext';
import {deviceService, userService} from '../services/api';

const DashboardContext = createContext();

export const useDashboard = () => {
  const context = useContext(DashboardContext);
  if (!context) {
    throw new Error('useDashboard must be used within a DashboardProvider');
  }
  return context;
};

export const DashboardProvider = ({children}) => {
  const [persons, setPersons] = useState([]);
  const [personDetails, setPersonDetails] = useState(null);
  const [devices, setDevices] = useState([]);
  const [personLoading, setPersonLoading] = useState(false);
  const [deviceLoading, setDeviceLoading] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [errorDetails, setErrorDetails] = useState('');
  const {user, isAdmin} = useAuth();

  const userId = user?.id || user?.sub;

  // Fetch person details
  const fetchPersonDetails = async () => {
    if (!userId) return;
    
    setPersonLoading(true);
    try {
      console.log('Fetching person details for ID:', userId);
      const response = await userService.getPerson(userId);
      setPersonDetails(response.data);
      console.log('Person details found:', response.data);
    } catch (error) {
      console.error('Error fetching person details:', error);
      setPersonDetails(null);
      if (error.response?.status !== 404) {
        setError('Failed to load profile details');
      }
    }
    setPersonLoading(false);
  };

  // Fetch all persons (admin only)
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
        errorMessage += `Server error: ${error.response.status}`;
        details = `Status: ${error.response.status}\nData: ${JSON.stringify(error.response.data, null, 2)}`;
      } else if (error.request) {
        errorMessage += 'No response from server.';
        details = 'The request was made but no response was received. Check if the backend is running.';
      } else {
        errorMessage += 'Unexpected error.';
        details = error.message;
      }

      setError(errorMessage);
      setErrorDetails(details);
    }
    setLoading(false);
  };

  // Fetch devices for current user
  const fetchDevices = async () => {
    if (!userId) return;
    
    setDeviceLoading(true);
    setError('');
    setErrorDetails('');
    try {
      console.log('Fetching devices...');
      const response = await deviceService.findByOwnerId(userId);
      console.log('Device response:', response);
      setDevices(response.data);
    } catch (error) {
      console.error('Error fetching devices:', error);
      let errorMessage = 'Failed to fetch devices. ';
      let details = '';

      if (error.response) {
        errorMessage += `Server error: ${error.response.status}`;
        details = `Status: ${error.response.status}\nData: ${JSON.stringify(error.response.data, null, 2)}`;
      } else if (error.request) {
        errorMessage += 'No response from server.';
        details = 'The request was made but no response was received. Check if the backend is running.';
      } else {
        errorMessage += 'Unexpected error.';
        details = error.message;
      }

      setError(errorMessage);
      setErrorDetails(details);
    }
    setDeviceLoading(false);
  };

  // Device management functions
  const createDevice = async (deviceData) => {
    try {
      const response = await deviceService.createDevice(deviceData);
      await fetchDevices(); // Refresh devices list
      return response;
    } catch (error) {
      console.error('Error creating device:', error);
      throw error;
    }
  };

  const updateDevice = async (deviceId, updateData) => {
    try {
      const response = await deviceService.updateDevice(deviceId, updateData);
      await fetchDevices(); // Refresh devices list
      return response;
    } catch (error) {
      console.error('Error updating device:', error);
      throw error;
    }
  };

  const deleteDevice = async (deviceId) => {
    try {
      await deviceService.deleteDevice(deviceId);
      setDevices(devices.filter(device => device.id !== deviceId));
    } catch (error) {
      console.error('Error deleting device:', error);
      throw error;
    }
  };

  // Effects for initial data loading
  useEffect(() => {
    if (isAdmin()) {
      fetchPersons();
    }
  }, [isAdmin]);

  useEffect(() => {
    if (userId) {
      fetchPersonDetails();
    }
  }, [userId]);

  useEffect(() => {
    if (userId) {
      fetchDevices();
    }
  }, [userId]);

  const value = {
    // State
    persons,
    personDetails,
    devices,
    personLoading,
    deviceLoading,
    loading,
    error,
    errorDetails,
    
    // Actions
    fetchPersonDetails,
    fetchPersons,
    fetchDevices,
    createDevice,
    updateDevice,
    deleteDevice,
    
    // Setters
    setError,
    setErrorDetails
  };

  return (
    <DashboardContext.Provider value={value}>
      {children}
    </DashboardContext.Provider>
  );
};