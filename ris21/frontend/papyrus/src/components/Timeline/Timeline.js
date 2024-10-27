import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../../App.css';
import './Timeline.css';
import { Link } from 'react-router-dom';

function Timeline() {
    const [data, setData] = useState([]);
    const [commentCounts, setCommentCounts] = useState({});
    const userId = localStorage.getItem('userId');


    // Fetch data from the backend
    useEffect(() => {
        axios
            .get('http://localhost:8080/objava')
            .then((response) => {
                setData(response.data);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, []);

    // Function to calculate comment counts for each post
    const calculateCommentCounts = () => {
        const counts = {};
        data.forEach((objava) => {
            const commentCount = objava.komentarCollection ? objava.komentarCollection.filter(comment => comment.content !== null).length : 0;
            counts[objava.id] = commentCount;
        });
        setCommentCounts(counts);
    };


    useEffect(() => {
        // Calculate comment counts when the component mounts and when data changes
        calculateCommentCounts();
    }, [data]);

    // Function to handle grading button click
    const handleGradeClick = (grade, objavaId) => {

        const uporabnikId = localStorage.getItem('userId');



        console.log(`Clicked grade ${grade} for objavaId ${objavaId} by uporabnikId ${uporabnikId}`);

        // Send the selected grade and objavaId to the backend
        axios
            .post(`http://localhost:8080/ocena/add`, { grade, uporabnikId, objavaId })
            .then((response) => {
                // Handle success if needed
                // You can also fetch updated data from the backend here
            })
            .catch((error) => {
                console.error('Error grading post:', error);
            });
    };

    const calculateAverageOcene = (objava) => {
        if (!objava || !objava.ocena || objava.ocena.length === 0) {
            return 0.0; // Return 0 if there are no 'Ocene' for the 'Objava'
        }

        const totalGrade = objava.ocena.reduce((sum, ocena) => sum + ocena.grade, 0.0);
        const average = totalGrade / objava.ocena.length;
        return parseFloat(average.toFixed(2)); // Limit to two decimal places
    };


    return (
        <div className="timeline-container">
            {/* Display each 'objava' in its own div with padding */}
            {data.map((objava) => (
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
                        <p>Avg. Grade: {calculateAverageOcene(objava)}</p>
                        <div className="grading-buttons">
                            {[1, 2, 3, 4, 5].map((grade) => (
                                <button
                                    key={grade}
                                    className="individual-grade-button"
                                    onClick={() => handleGradeClick(grade, objava.id)} // Pass objava.id as objavaId
                                >
                                    {grade}
                                </button>
                            ))}
                        </div>

                        <Link to={`/comments/${objava.id}`} className="comments-button">
                            <button className="comments-button">Comments ({commentCounts[objava.id] || 0})</button>
                        </Link>

                    </div>
                </div>
            ))}
        </div>
    );
}

export default Timeline;
