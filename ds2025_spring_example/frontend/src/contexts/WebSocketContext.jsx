import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';

const WebSocketContext = createContext();

export const useWebSocket = () => {
  const context = useContext(WebSocketContext);
  if (!context) {
    throw new Error('useWebSocket must be used within a WebSocketProvider');
  }
  return context;
};

export const WebSocketProvider = ({ children }) => {
  const [socket, setSocket] = useState(null);
  const [messages, setMessages] = useState([]);
  const [isConnected, setIsConnected] = useState(false);

  const connectWebSocket = useCallback(() => {
    // CORECTEAZĂ PORTUL AICI: 8083
    const wsUrl = 'ws://localhost:8083/ws';
    
    console.log('Connecting to WebSocket:', wsUrl);
    
    const newSocket = new WebSocket(wsUrl);

    newSocket.onopen = () => {
      console.log('WebSocket connection established');
      setIsConnected(true);
      setSocket(newSocket);
    };

    newSocket.onmessage = (event) => {
      console.log('Message from server (RAW):', event.data);
      
      // IMPORTANT: Serverul tău trimite text simplu, nu JSON!
      // Așa că tratează ca text
      const newMessage = {
        id: Date.now(),
        timestamp: new Date().toLocaleTimeString(),
        content: event.data,
        type: 'text'
      };
      
      setMessages(prev => [...prev, newMessage]);

      // Opțional: notificare browser
      if (Notification.permission === 'granted') {
        new Notification('Mesaj nou de la server', {
          body: event.data,
          icon: '/favicon.ico'
        });
      }
    };

    newSocket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    newSocket.onclose = (event) => {
      console.log('WebSocket connection closed:', event.code, event.reason);
      setIsConnected(false);
      setSocket(null);
      
      // Reconectare după 3 secunde
      setTimeout(() => {
        console.log('Attempting to reconnect...');
        connectWebSocket();
      }, 3000);
    };

    return newSocket;
  }, []);

  useEffect(() => {
    const ws = connectWebSocket();

    return () => {
      if (ws) {
        ws.close();
      }
    };
  }, [connectWebSocket]);

  const value = {
    socket,
    messages,
    isConnected,
    clearMessages: () => setMessages([])
  };

  return (
    <WebSocketContext.Provider value={value}>
      {children}
    </WebSocketContext.Provider>
  );
};