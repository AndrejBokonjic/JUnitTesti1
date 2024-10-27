import React, { useState } from 'react';
import axios from 'axios'; // Import Axios

const RegistrationForm = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/uporabnik/register', {
        name: name,
        email: email,
        password: password,
      });

      if (response.status === 200) {
        console.log('User registered successfully');
        window.location.href = 'http://localhost:3000';
        // Add any additional logic here upon successful registration
      }
    } catch (error) {
      if (error.response && error.response.status === 400) {
        console.log('Email is already in use');
      } else {
        console.error('Error:', error.message);
      }
    }
  };

  return (
    <form onSubmit={handleRegister}>
      <input
        type="text"
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
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
      <button type="submit">Register</button>
    </form>
  );
};

export default RegistrationForm;
