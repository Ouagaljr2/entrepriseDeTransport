import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/', // Remplacez par l'URL de votre backend
});

export default api;
