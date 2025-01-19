import api from './api'; // Importer l'instance Axios

// Fonction pour créer un utilisateur (POST)
export const createUser = async (user) => {
    try {
        const response = await api.post('/users/register', user);
        return response.data; // Retourner l'utilisateur créé
    } catch (error) {
        console.error('Error creating user:', error);
        return null;
    }
};

// Fonction pour se connecter (POST)
export const loginUser = async (username, password) => {
    try {
        console.log('dans loginuser Envoi de la requête de connexion avec les données suivantes:', { username, password });
        const response = await api.post('/users/login', { username, password });
        console.log('Réponse de la requête de connexion:', response);
        if (response.status === 200) {
            // Sauvegarder le token JWT dans le localStorage (ou sessionStorage)
            localStorage.setItem('authToken', response.data.token);

            // Sauvegarder le username dans le localStorage
            localStorage.setItem('username', username);

            return response.data; // Retourner les informations de l'utilisateur connecté
        }
    } catch (error) {
        console.error('Error logging in:', error);
        return null;
    }
};


// Fonction pour récupérer les informations de l'utilisateur connecté
export const getUserInfo = async (username = null) => {
    try {
        const token = localStorage.getItem('authToken');
        if (!username) {
            // Récupérer le username depuis le localStorage si non fourni
            username = localStorage.getItem('username');
        }

        if (token && username) {
            const response = await api.get('/users/getUser', {
                headers: { Authorization: `Bearer ${token}` },
                params: { username }, // Transmettre le paramètre `username`
            });
            console.log('User info recuperer apres connexion:', response.data);
            return response.data; // Retourner les informations de l'utilisateur
        } else {
            console.error('No token or username found in localStorage.');
            return null;
        }
    } catch (error) {
        console.error('Error fetching user info:', error);
        return null;
    }
};



// Fonction pour se déconnecter (supprimer le token)
export const logoutUser = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('username'); // Supprimer le username
};


// Fonction pour vérifier si un utilisateur est authentifié
// userService.js
export const isAuthenticated = () => {
    // Vérifie si un token est stocké dans le localStorage (ou dans AsyncStorage pour React Native)
    const token = localStorage.getItem('token'); // ou AsyncStorage
    return token !== null;
};
;
