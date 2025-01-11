// src/services/driverService.js
import api from './api'; // Importer l'instance Axios

export const fetchDrivers = async () => {
    try {
        const response = await api.get('/drivers');
        return response.data; // Renvoyer les données des conducteurs
    } catch (error) {
        console.error('Error fetching drivers:', error);
        return [];
    }
};

export const searchDrivers = async (name) => {
    try {
        const response = await api.get(`/drivers/search?name=${name}`);
        return response.data; // Renvoyer les données des conducteurs trouvés
    } catch (error) {
        console.error('Error searching drivers:', error);
        return [];
    }
};

export const addDriver = async (driver) => {
    try {
        const response = await api.post('/drivers', driver);
        return response.status === 200; // Retourne vrai si l'ajout a réussi
    } catch (error) {
        console.error('Error adding driver:', error);
        return false;
    }
};

export const updateDriver = async (id, driver) => {
    try {
        const response = await api.put(`/drivers/${id}`, driver);
        return response.status === 200; // Retourne vrai si la mise à jour a réussi
    } catch (error) {
        console.error('Error updating driver:', error);
        return false;
    }
};

export const deleteDriver = async (id) => {
    try {
        const response = await api.delete(`/drivers/${id}`);
        return response.status === 200; // Retourne vrai si la suppression a réussi
    } catch (error) {
        console.error('Error deleting driver:', error);
        return false;
    }
};
