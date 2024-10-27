import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Tekmovanja = () => {
  const [tekmovanja, setTekmovanja] = useState([]);

  useEffect(() => {
    // Funkcija za pridobivanje podatkov iz backend-a
    const pridobiTekmovanja = async () => {
      try {
        const response = await axios.get('http://localhost:8080/tekmovanje');
        setTekmovanja(response.data);
      } catch (error) {
        console.error('Napaka pri pridobivanju podatkov:', error);
      }
    };

    // Kličemo funkcijo za pridobivanje podatkov ob naložitvi komponente
    pridobiTekmovanja();
  }, []); // Uporaba praznega polja kot odvisnost, da se useEffect izvede samo ob prvem renderiranju

  return (
    <div>
      <h1>Seznam tekmovanj</h1>
      <ul>
        {tekmovanja.map((tekmovanje) => (
          <li key={tekmovanje.id}>{tekmovanje.naziv}</li>
        ))}
      </ul>
    </div>
  );
};

export default Tekmovanja;
