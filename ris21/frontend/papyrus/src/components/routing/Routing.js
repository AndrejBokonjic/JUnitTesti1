import React from 'react';
import { Route } from 'react-router';
import { Routes } from 'react-router-dom';
import TestComponent from '../TestComponent/TestComponent';
import LoginForm from '../Login/login';
import Timeline from "../Timeline/Timeline";
import Comments from "../Comments/Comments";
import RegisterForm from '../Register/register';
import DodajTrgovinoForm from '../Trgovina/ustvariTrgovino';
import DodajStoritevForm from '../Trgovina/ustvariStoritev';
import ProfilPage from '../Profil/profil';
import Objava from '../Objava/objava';
import StoritvePage from '../Storitve/storitve';

function Routing() {
    return (
        <Routes>
            <Route path="/" element={<Timeline />} />
            <Route path="/test" element={<TestComponent />} />
            <Route path="/login" element={<LoginForm />} />
            <Route path="/comments/:objava_id" element={<Comments />} />
            <Route path="/register" element={<RegisterForm />} />
            <Route path="/novaTrgovina" element={<DodajTrgovinoForm />} />
            <Route path="/novaStoritev" element={<DodajStoritevForm />} />
            <Route path="/profil" element={<ProfilPage />} />
            <Route path="/objava" element={<Objava />} />
            <Route path="/storitve" element={<StoritvePage />} />

        </Routes>
    );
}

export default Routing;
