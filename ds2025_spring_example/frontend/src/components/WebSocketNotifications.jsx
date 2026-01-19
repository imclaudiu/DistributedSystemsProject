import React, { useState } from 'react';
import { useWebSocket } from '../contexts/WebSocketContext';
import './../styles/WebSocketNotifications.css';

const WebSocketNotifications = () => {
  const { messages, isConnected, clearMessages } = useWebSocket();
  const [isExpanded, setIsExpanded] = useState(false);

  const handleNotificationClick = () => {
    setIsExpanded(!isExpanded);
  };

  const unreadCount = messages.length;

  return (
    <div className={`websocket-notifications ${isExpanded ? 'expanded' : ''}`}>
      <div className="notification-bell" onClick={handleNotificationClick}>
        <span className="bell-icon">🔔</span>
        {unreadCount > 0 && (
          <span className="notification-badge">{unreadCount}</span>
        )}
        <span className="connection-status">
          {isConnected ? '🟢' : '🔴'}
        </span>
      </div>

      {isExpanded && (
        <div className="notifications-panel">
          <div className="notifications-header">
            <h3>Notifications</h3>
            <div className="header-actions">
              <button onClick={clearMessages} className="clear-btn">
                Clear All
              </button>
              <button onClick={() => setIsExpanded(false)} className="close-btn">
                ×
              </button>
            </div>
          </div>

          <div className="notifications-list">
            {messages.length === 0 ? (
              <div className="empty-notifications">
                No notifications yet
              </div>
            ) : (
              messages.slice().reverse().map((msg) => (
                <div key={msg.id} className="notification-item">
                  <div className="notification-header">
                    <span className="notification-time">{msg.timestamp}</span>
                    {msg.type && (
                      <span className={`notification-type ${msg.type}`}>
                        {msg.type}
                      </span>
                    )}
                  </div>
                  <div className="notification-content">
                    {msg.title && <strong>{msg.title}</strong>}
                    {msg.message && <p>{msg.message}</p>}
                    {msg.content && <p>{msg.content}</p>}
                    {!msg.message && !msg.content && JSON.stringify(msg)}
                  </div>
                </div>
              ))
            )}
          </div>

          <div className="notifications-footer">
            <small>Connection: {isConnected ? 'Connected' : 'Disconnected'}</small>
          </div>
        </div>
      )}
    </div>
  );
};

export default WebSocketNotifications;