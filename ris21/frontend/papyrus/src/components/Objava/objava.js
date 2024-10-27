import React, { useState } from 'react';
import axios from 'axios';

function DodajObjavo() {
    const [text, setText] = useState('');
    const [image, setImage] = useState(null);

    const handleTextChange = (e) => {
        setText(e.target.value);
    };

    const handleImageChange = (e) => {
        setImage(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const formData = new FormData();
            formData.append('text', text);

            // Če je bila izbrana slika, jo dodaj v FormData
            if (image) {
                formData.append('file', image);
            }

            const userId = localStorage.getItem('userId');
            // Najprej ustvarimo objavo
            const objavaResponse = await axios.post(`http://localhost:8080/objava/${userId}`, { text });
            const objavaId = objavaResponse.data.id;

            // Nato naložimo sliko za ustvarjeno objavo
            if (image) {
                const imageResponse = await axios.post(`http://localhost:8080/objava/imageUpload/${objavaId}`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
                console.log('Slika naložena:', imageResponse.data);
            }

            console.log('Objava ustvarjena:', objavaResponse.data);
            // Dodajte kodo za preusmeritev na drugo stran ali prikaz uspešnega sporočila itd.
        } catch (error) {
            console.error('Napaka pri ustvarjanju objave:', error);
            // Dodajte kodo za obdelavo napake, prikaz sporočila o napaki itd.
        }
    };

    return (
        <div>
            <h2>Dodaj objavo</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Besedilo:
                    <input type="text" value={text} onChange={handleTextChange} />
                </label>
                <label>
                    Slika:
                    <input type="file" onChange={handleImageChange} />
                </label>
                <button type="submit">Ustvari objavo</button>
            </form>
        </div>
    );
}

export default DodajObjavo;
