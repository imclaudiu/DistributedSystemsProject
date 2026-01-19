import React, { useState } from 'react';
import { useSimpleWebSocket } from '../contexts/SimpleWebSocketContext';

const SimpleWebSocketTest = () => {
  const { isConnected, lastMessage, sendMessage } = useSimpleWebSocket();
  const [inputMessage, setInputMessage] = useState('');

  const handleSend = () => {
    if (inputMessage.trim()) {
      const success = sendMessage(inputMessage);
      if (success) {
        console.log('📤 Mesaj trimis:', inputMessage);
        setInputMessage('');
      } else {
        alert('❌ Nu sunt conectat la WebSocket!');
      }
    }
  };

  return (
    <div style={{
      position: 'fixed',
      bottom: '20px',
      right: '20px',
      backgroundColor: 'white',
      padding: '15px',
      borderRadius: '8px',
      boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
      zIndex: 1000,
      width: '300px'
    }}>
      <h4 style={{ marginTop: 0 }}>🔌 WebSocket Test</h4>
      
      <div style={{ marginBottom: '10px' }}>
        Status: 
        <span style={{ 
          color: isConnected ? 'green' : 'red',
          fontWeight: 'bold',
          marginLeft: '5px'
        }}>
          {isConnected ? '✅ Conectat' : '❌ Deconectat'}
        </span>
      </div>

      {lastMessage && (
        <div style={{
          backgroundColor: '#f0f0f0',
          padding: '10px',
          borderRadius: '4px',
          marginBottom: '10px',
          fontSize: '14px'
        }}>
          <strong>Ultimul mesaj:</strong><br/>
          {lastMessage}
        </div>
      )}

      <div style={{ display: 'flex', gap: '10px' }}>
        <input
          type="text"
          value={inputMessage}
          onChange={(e) => setInputMessage(e.target.value)}
          placeholder="Scrie un mesaj..."
          style={{
            flex: 1,
            padding: '8px',
            border: '1px solid #ccc',
            borderRadius: '4px'
          }}
          onKeyPress={(e) => e.key === 'Enter' && handleSend()}
        />
        <button
          onClick={handleSend}
          disabled={!isConnected}
          style={{
            padding: '8px 15px',
            backgroundColor: isConnected ? '#007bff' : '#ccc',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: isConnected ? 'pointer' : 'not-allowed'
          }}
        >
          Trimite
        </button>
      </div>

      <div style={{ marginTop: '10px', fontSize: '12px', color: '#666' }}>
        <button
          onClick={() => sendMessage('PING')}
          style={{ marginRight: '10px', fontSize: '12px' }}
        >
          Ping
        </button>
        <button
          onClick={() => sendMessage('HELLO')}
          style={{ fontSize: '12px' }}
        >
          Hello
        </button>
      </div>
    </div>
  );
};

export default SimpleWebSocketTest;