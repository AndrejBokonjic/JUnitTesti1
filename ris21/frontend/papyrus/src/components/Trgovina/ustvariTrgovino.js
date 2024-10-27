import React, { useState } from 'react';
import axios from 'axios';

const DodajTrgovinoForm = () => {
    const [name, setName] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const userId = localStorage.getItem('userId'); // Preberi id uporabnika iz local storage

        const trgovinaUmetnika = {
            name: name,
        };

        try {
            const response = await axios.post(`http://localhost:8080/trgovinaUporabnika/${userId}`, trgovinaUmetnika);
            console.log(response.data);
            window.location.href = 'http://localhost:3000';
            // Dodaj kodo za obdelavo uspešnega odgovora, če je potrebno
        } catch (error) {
            console.error('Napaka pri pošiljanju zahtevka:', error);
            // Dodaj kodo za obdelavo napake, če je potrebno
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Ime trgovine"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <button type="submit">Dodaj trgovino umetnika</button>
        </form>
    );
};

export default DodajTrgovinoForm;
