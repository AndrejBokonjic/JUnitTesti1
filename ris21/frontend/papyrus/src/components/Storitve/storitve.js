import React, { useState, useEffect } from 'react';
import axios from 'axios';

const StoritvePage = () => {
    const [storitve, setStoritve] = useState([]);

    useEffect(() => {
        async function fetchStoritve() {
            try {
                const response = await axios.get('http://localhost:8080/storitve');
                setStoritve(response.data);
            } catch (error) {
                console.error('Napaka pri pridobivanju storitev:', error);
            }
        }

        fetchStoritve();
    }, []);

    const handleAddToCart = (storitevId) => {
        // Tukaj lahko dodate logiko za dodajanje storitve v košarico
        console.log(`Storitev z ID ${storitevId} je bila dodana v košarico.`);
    };

    return (
        <div>
            <h2>Seznam storitev</h2>
            <ul>
                {storitve.map(storitev => (
                    <li key={storitev.id}>
                        <div>
                            <h3>{storitev.naziv}</h3>
                            {/* Dodaj gumb za dodajanje v košarico */}
                            <button onClick={() => handleAddToCart(storitev.id)}>Dodaj v košarico</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default StoritvePage;
