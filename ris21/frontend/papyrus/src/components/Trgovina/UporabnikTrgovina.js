import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UporabnikTrgovina = ({ userId }) => {
    const [trgovinaId, setTrgovinaId] = useState(null);

    useEffect(() => {
        if (userId) { // Preveri, ali je userId veljaven
            async function fetchTrgovinaId() {
                try {
                    const response = await axios.get(`http://localhost:8080/trgovinaUporabnika/uporabnik/${userId}/trgovina`);
                    setTrgovinaId(response.data.id);
                } catch (error) {
                    console.error('Napaka pri pridobivanju trgovinaId:', error);
                }
            }

            fetchTrgovinaId();
        }
    }, [userId]);

    return trgovinaId;
};

export default UporabnikTrgovina;
