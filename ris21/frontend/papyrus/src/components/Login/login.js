// LoginForm.js
import React, { useState } from 'react';

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();

        const response = await fetch('http://localhost:8080/uporabnik/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password,
            }),
        });

        if (response.ok) {
            // Login successful
            console.log('Login successful');
            const userData = await response.json(); // Pretvori odgovor v JSON format
            localStorage.setItem('userId', userData.id); // Shrani userId v localStorage
            window.location.href = 'http://localhost:3000';
        } else if (response.status === 401) {
            // Invalid password
            console.log('Invalid password');
        } else if (response.status === 404) {
            // User not found
            console.log('User not found');
        } else {
            // Other errors
            console.error('Error:', response.statusText);
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit">Login</button>
        </form>
    );
};

export default LoginForm;