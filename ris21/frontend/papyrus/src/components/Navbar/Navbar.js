import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'; // Import the CSS file for your Navbar styles
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faBasketShopping, faUser} from '@fortawesome/free-solid-svg-icons'; // Example profile icon
import {faPlus} from '@fortawesome/free-solid-svg-icons'; // Example add icon

function Navbar() {
    return (
        <nav className="navbar">
            <ul className="nav-list">
                <li className="nav-item">
                    <Link to="/" className="nav-link">Timeline</Link>
                </li>
                <li className="nav-item">
                    <Link to="/shop" className="nav-link">Shop</Link>
                </li>
                <li className="nav-item">
                    <Link to="/lists" className="nav-link">Lists</Link>
                </li>
                <li className="nav-item">
                    <Link to="/test" className="nav-link">Test</Link>
                </li>
            </ul>
            <div className="icons">
                <Link to="/basket">
                    <FontAwesomeIcon icon={faBasketShopping} className="tertiary-color"/>
                </Link>
                <Link to="/profil">
                    <FontAwesomeIcon icon={faUser} className="primary-color"/>
                </Link>
                <Link to="/objava"> {/* Modified this line */}
                    <button className="add-button"> {/* Added Link wrapping the button */}
                <FontAwesomeIcon icon={faPlus} className="primary-color" />
                    </button>
                </Link> {/* Added this closing tag */}
            </div>
        </nav>
    );
}

export default Navbar;
