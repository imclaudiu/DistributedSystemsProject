console.log("Script is running and binding form.");

document.getElementById('loginForm').addEventListener('submit', function(event) {
    
    // 1. Prevent the default browser form submission (no page refresh)
    event.preventDefault(); 

    // 2. Get the values from the input fields
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // 3. Prepare the data payload as a JSON object
    const data = {
        username: username,
        password: password
    };

    // 4. Send the data to the server using the fetch API
    fetch(this.action, { // 'this.action' refers to the form's action URL
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Tell the server the data is JSON
        },
        body: JSON.stringify(data), // Convert JS object to JSON string
    })
    .then(response => {
        // Check if the response status is OK (e.g., 200-299)
        if (response.ok) {
            return response.json(); // If successful, parse the JSON response
        }
        // If not OK (e.g., 401 Unauthorized), throw an error
        throw new Error('Login failed. Check credentials.'); 
    })
    .then(data => {
        const jwtToken = data.token;
        localStorage.setItem('authToken', jwtToken);
        document.getElementById('message').textContent = 'Login successful!';

        window.location.href = 'file:///D:/Learning/frontend/dashboard.html';
    })
    .catch((error) => {
        // Handle any network or login failure errors
        document.getElementById('message').textContent = `Error: ${error.message}`;
        console.error('Error:', error);
    });
});