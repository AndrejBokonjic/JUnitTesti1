import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../../App.css';
import './Comments.css'; // You can create a separate CSS file for Comments
import { useParams } from 'react-router-dom';

function Comments() {
    const [data, setData] = useState([]);
    const [newComment, setNewComment] = useState(''); // State for new comment input
    const { objava_id } = useParams(); // Access the objava_id from the URL
    const userId = localStorage.getItem('userId');

    useEffect(() => {
        // Use the objava_id from the URL in the API request
        axios
            .get(`http://localhost:8080/objava/komentar/${objava_id}`)
            .then((response) => {
                setData(response.data);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, [objava_id]); // Add objava_id to the dependency array

    // Function to handle submission of new comments
    const handleCommentSubmit = () => {
        // Send the new comment to the backend
        axios
            .post(`http://localhost:8080/komentar/add`, {

                userId: localStorage.getItem('userId'), // Replace with the appropriate user ID
                postId: objava_id, // Pass the objava_id from the URL as postId
                content: newComment, // Pass the new comment content
            })
            .then((response) => {
                // Handle success if needed
                // You can also update the comments list by fetching data again
                // after the comment is successfully added
                setNewComment(''); // Clear the comment input field
            })
            .catch((error) => {
                console.error('Error adding comment:', error);
            });
    };

    // Function to handle removal of a comment
    const handleCommentRemove = async (objavaId) => {
        try {
            // Pošlji zahtevek za izbris objave
            await axios.delete(`http://localhost:8080/komentar/delete/${objavaId}`);
            console.log(`Objava z ID ${objavaId} je bila uspešno izbrisana.`);
        } catch (error) {
            console.error(`Napaka pri brisanju objave z ID ${objavaId}:`, error);
        }
    };


    return (
        <div className="timeline-container"> {/* Apply CSS styling similar to Timeline */}
            {/* Comment input field and submit button */}
            <div className="comment-input-container">
                <textarea
                    placeholder="Add a comment..."
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    className="comment-input-field"
                    rows="1"
                    style={{
                        minHeight: '60px',  // Set a minimum height
                        width: '100%',      // Set a fixed width
                        resize: 'vertical', // Allow vertical resizing only
                    }}
                />
                <button onClick={handleCommentSubmit} className="comment-submit-button">Submit</button>
            </div>

            {/* Display each 'komentar' (comment) in its own div with padding */}
            {data.map((comment) => (
                <div key={comment.id} className="objava-div">
                    <div className="objava-content">
                        <h3 className="objava-name">{comment.userName}</h3>
                        <p className="objava-text">{comment.content}</p>
                        {/*{console.log('comment.uporabnikId:', comment.userId)}*/}
                        {/*{console.log('userId:', userId)}*/}
                        {comment.userId == userId && ( // Check if the comment belongs to the logged-in user
                            <button
                                onClick={() => {
                                    console.log(`Removing comment with id: ${comment.id}`);
                                    handleCommentRemove(comment.id);
                                }}
                                className="comment-remove-button"
                            >
                                Remove
                            </button>
                        )}
                    </div>
                </div>
            ))}


        </div>
    );
}

export default Comments;
