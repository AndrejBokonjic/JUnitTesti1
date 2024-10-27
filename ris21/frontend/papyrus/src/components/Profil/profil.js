import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ProfilPage = () => {
    const [userData, setUserData] = useState(null);
    const [storitve, setStoritve] = useState([]);
    const [objave, setObjave] = useState([]);

    useEffect(() => {
        // Pridobi uporabnikov ID iz localStorage
        const userId = localStorage.getItem('userId');

        // Izvedi poizvedbo v bazo, da pridobiš podatke o uporabniku in objave
        async function fetchData() {
            try {
                const response = await axios.get(`http://localhost:8080/uporabnik/${userId}`);
                setUserData(response.data); // Nastavi stanje z dobljenimi podatki o uporabniku
                // Če uporabnik ima trgovino, pridobi seznam storitev
                if (response.data.trgovinaUmetnika) {
                    const responseStoritve = await axios.get(`http://localhost:8080/trgovinaUporabnika/${response.data.trgovinaUmetnika.id}/storitve`);
                    setStoritve(responseStoritve.data);
                }
                // Pridobi tudi objave uporabnika
                const responseObjave = await axios.get(`http://localhost:8080/uporabnik/${userId}/objave`);
                setObjave(responseObjave.data);
            } catch (error) {
                console.error('Napaka pri pridobivanju podatkov:', error);
            }
        }
        

        fetchData(); // Izvedi poizvedbo ob prvem renderiranju komponente
    }, []); // Uporabi prazen seznam odvisnosti, da se useEffect izvede samo ob prvem renderiranju

    const handleDelete = async (objavaId) => {
        try {
            // Pošlji zahtevek za izbris objave
            await axios.delete(`http://localhost:8080/objava/${objavaId}`);
            // Po uspešnem izbrisu, osveži seznam objav
            setObjave(objave.filter(objava => objava.id !== objavaId));
            console.log(`Objava z ID ${objavaId} je bila uspešno izbrisana.`);
        } catch (error) {
            console.error(`Napaka pri brisanju objave z ID ${objavaId}:`, error);
        }
    };

    return (
        <div>
            <h1>Profil</h1>
            {userData ? (
                <div>
                    <p>Email: {userData.email}</p>
                    <p>Ime: {userData.name}</p>
                    {userData.trgovinaUmetnika ? (
                        <div>
                            <p>Trgovina: {userData.trgovinaUmetnika.name}</p>
                            {/* Prikaz seznam storitev */}
                            {storitve.length > 0 ? (
                                <div>
                                    <h2>Storitve</h2>
                                    <ul>
                                        {storitve.map(storitev => (
                                            <li key={storitev.id}>{storitev.naziv}</li>
                                        ))}
                                    </ul>
                                </div>
                            ) : (
                                <p>Trgovina nima storitev.</p>
                            )}
                        </div>
                    ) : (
                        <button>Ustvari novo trgovino</button>
                    )}
                    {/* Prikaz objav uporabnika */}
                    {objave.length > 0 ? (
                        <div className="timeline-container">
                        {/* Display each 'objava' in its own div with padding */}
                        {objave.map((objava) => (
                            <div key={objava.id} className="objava-div">
                                <div className="objava-content">
                                    <div className="objava-header">
                                        <h3 className="objava-name">{objava.name}</h3>
                                    </div>
                                    <p className="objava-text">{objava.text}</p>
                                    {objava.imageData !== null && (
                                        <img src={`data:image/png;base64,${objava.imageData}`} alt="Objava Image"
                                             className="objava-image"/>
                                    )}
                                    {/* Add more fields as needed */}

                                </div>
                                <div className="objava-buttons">
                                {/* Dodaj gumb za brisanje objave */}
                                <button onClick={() => handleDelete(objava.id)} className="delete-button">Delete</button>
                            </div>
            
                            </div>
                        ))}
                    </div>
                    ) : (
                        <p>Uporabnik še nima objav.</p>
                    )}
                </div>
            ) : (
                <p>Nalaganje podatkov...</p>
            )}
        </div>
    );
};

export default ProfilPage;
