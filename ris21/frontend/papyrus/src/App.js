import React from 'react';
import './App.css';
import Routing from './components/routing/Routing';
import Navbar from './components/Navbar/Navbar';
import Background from './components/Background/Background';
import Footer from './components/Footer/Footer';
import { BrowserRouter } from 'react-router-dom';
import LoginForm from './components/Login/login';

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <Background /> {/* Include the Background component */}
                <Navbar /> {/* Include the Navbar component inside BrowserRouter */}
                <Routing />
                <Footer /> {/* Include the Footer component */}
            </div>
        </BrowserRouter>
    );
}

export default App;
