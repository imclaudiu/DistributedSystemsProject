import React, { createContext, useContext, useState, useEffect } from 'react';

const SimpleWebSocketContext = createContext();

export const useSimpleWebSocket = () => {
  return useContext(SimpleWebSocketContext);
};

// EXPORT NAMED CORECT!
export const SimpleWebSocketProvider = ({ children }) => {
  const [socket, setSocket] = useState(null);
  const [lastMessage, setLastMessage] = useState(null);
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    // Conectează direct la WebSocket
    const ws = new WebSocket('ws://localhost:8083/ws');
    
    ws.onopen = () => {
      console.log('✅ WebSocket conectat!');
      setIsConnected(true);
    };

    ws.onmessage = (event) => {
      console.log('📩 Mesaj primit:', event.data);
      setLastMessage(event.data);
      
      // Afișează direct alertă
      try {
        const data = JSON.parse(event.data);
        alert(`🔔 ${data.message || event.data}`);
      } catch {
        alert(`🔔 ${event.data}`);
      }
    };

    ws.onerror = (error) => {
      console.log('❌ Eroare WebSocket:', error);
    };

    ws.onclose = () => {
      console.log('🔌 WebSocket deconectat');
      setIsConnected(false);
    };

    setSocket(ws);

    // Deconectează la unmount
    return () => {
      ws.close();
    };
  }, []);

  const sendMessage = (message) => {
    if (socket && socket.readyState === WebSocket.OPEN) {
      socket.send(message);
      return true;
    }
    return false;
  };

  const value = {
    socket,
    lastMessage,
    isConnected,
    sendMessage
  };

  return (
    <SimpleWebSocketContext.Provider value={value}>
      {children}
    </SimpleWebSocketContext.Provider>
  );
};

// Export default dacă vrei
// export default SimpleWebSocketProvider;