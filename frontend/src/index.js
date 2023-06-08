import React from 'react';
import ReactDOM from 'react-dom/client';
import { RecoilRoot } from 'recoil';
import App from './App';
import axios from "axios";
import {baseUrl} from "./Atoms"

axios.defaults.baseURL = baseUrl;
axios.defaults.withCredentials = true;

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RecoilRoot>
      <App />
    </RecoilRoot>
);