document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    // Simple validation
    if (!username || !password) {
        alert('Please fill in both fields');
        return;
    }
    
    if (password.length < 6) {
        alert('Password must be at least 6 characters');
        return;
    }
    
    // Simulate login
    console.log('Login attempt:', { username, password });
    alert('Login successful!');
    
    // In real app, you would send data to server
    // Example: fetch('/login', { method: 'POST', body: JSON.stringify({username, password}) })
});

// Google button functionality
document.querySelector('.google-btn').addEventListener('click', function() {
    alert('Google login would be implemented here');
});
