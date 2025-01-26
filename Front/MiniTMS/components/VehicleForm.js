import React, { useState } from 'react';
import { View, TextInput, Text, Button, StyleSheet } from 'react-native';
import { addVehicle, updateVehicle } from '../services/vehicleService';

const VehicleForm = ({ fetchVehicles, initialVehicle = null, onClose }) => {
    const [registrationNumber, setRegistrationNumber] = useState(initialVehicle ? initialVehicle.registrationNumber : '');
    const [status] = useState('Disponible'); // Statut fixe
    const [model, setModel] = useState(initialVehicle ? initialVehicle.model : '');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        const vehicleData = { registrationNumber, status, model };
        let success;
        setLoading(true);
        if (initialVehicle) {
            // Mise à jour du véhicule
            success = await updateVehicle(initialVehicle.id, vehicleData);
        } else {
            // Ajout d'un nouveau véhicule
            success = await addVehicle(vehicleData);
        }
        setLoading(false);

        if (success) {
            fetchVehicles(); // Rafraîchir la liste des véhicules
            onClose(); // Fermer le formulaire
        }
    };

    return (
        <View style={styles.formContainer}>
            <Text style={styles.formTitle}>{initialVehicle ? 'Modifier Véhicule' : 'Ajouter Véhicule'}</Text>
            <TextInput
                style={styles.input}
                placeholder="Numéro d'immatriculation"
                value={registrationNumber}
                onChangeText={setRegistrationNumber}
            />
            <TextInput
                style={styles.input}
                placeholder="Modèle"
                value={model}
                onChangeText={setModel}
            />
            {/* Affichage du statut, non modifiable */}
            <Text style={styles.statusText}>Statut: {status}</Text>

            <Button
                title={loading ? 'Chargement...' : (initialVehicle ? 'Mettre à jour' : 'Ajouter')}
                onPress={handleSubmit}
                disabled={loading}
            />
            <Button title="Annuler" color="red" onPress={onClose} />
        </View>
    );
};

const styles = StyleSheet.create({
    formContainer: {
        padding: 20,
        backgroundColor: '#fff',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.2,
        shadowRadius: 4,
        elevation: 5,
        maxWidth: 400,
        margin: 20,
    },
    formTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 10,
        textAlign: 'center',
    },
    input: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        marginBottom: 10,
        paddingLeft: 10,
        borderRadius: 5,
    },
    statusText: {
        marginBottom: 15,
        color: '#555',
        fontSize: 16,
        textAlign: 'center',
    },
});

export default VehicleForm;
