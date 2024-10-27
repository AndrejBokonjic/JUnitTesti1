import React, { useState, useEffect } from 'react';
import axios from 'axios';

const DodajStoritevForm = () => {
    const [naziv, setNaziv] = useState('');
    const [trgovinaId, setTrgovinaId] = useState(null); // Dodal sem stanje za trgovinaId

    useEffect(() => {
        const uporabnikId = localStorage.getItem('userId');

        // Izvedi poizvedbo v bazi, da poiščeš trgovinaId za dani uporabnikId
        async function fetchTrgovinaId() {
            try {
                const response = await axios.get(`http://localhost:8080/trgovinaUporabnika/uporabnik/${uporabnikId}/trgovina`);
                // Nastavi trgovinaId s pridobljenimi podatki iz odgovora
                setTrgovinaId(response.data.id);
            } catch (error) {
                console.error('Napaka pri pridobivanju trgovinaId:', error);
            }
        }

        fetchTrgovinaId();
         // Izvedi poizvedbo ob prvem renderiranju komponente
    }, []); // Uporabi prazen seznam odvisnosti, da se useEffect izvede samo ob prvem renderiranju
    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const storitev = {
            naziv: naziv,
            trgovina_umetnika_id: trgovinaId
        };

        try {
            const response = await axios.post(`http://localhost:8080/trgovinaUporabnika/${trgovinaId}/storitve`, storitev);
            console.log(response.data);
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
                placeholder="Naziv storitve"
                value={naziv}
                onChange={(e) => setNaziv(e.target.value)}
            />
            <button type="submit">Dodaj storitev</button>
        </form>
    );
};

export default DodajStoritevForm;
