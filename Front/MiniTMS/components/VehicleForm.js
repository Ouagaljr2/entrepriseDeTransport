import React, { useState } from 'react';
import { View, TextInput, Text, Button, StyleSheet, Alert } from 'react-native';
import { addVehicle, updateVehicle } from '../services/vehicleService';

const VehicleForm = ({ fetchVehicles, initialVehicle = null, onClose }) => {
    const [registrationNumber, setRegistrationNumber] = useState(initialVehicle ? initialVehicle.registrationNumber : '');
    const [status, setStatus] = useState(initialVehicle ? initialVehicle.status : '');
    const [model, setModel] = useState(initialVehicle ? initialVehicle.model : '');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {

        const vehicleData = { registrationNumber, status, model };
        let success;
        if (initialVehicle) {
            // Mise à jour du véhicule existant
            success = await updateVehicle(initialVehicle.id, vehicleData);
        } else {
            // Ajout d'un nouveau véhicule
            success = await addVehicle(vehicleData);
        }

        if (success) {
            fetchVehicles(); // Rafraîchir la liste des véhicules
            onClose(); // Fermer le formulaire

        }
    };

    return (
        <View style={styles.form}>
            {initialVehicle && (
                <View style={styles.idContainer}>
                    <Text style={styles.label}>ID: {initialVehicle.id}</Text>
                </View>
            )}
            <TextInput
                style={styles.input}
                placeholder="Numéro d'immatriculation"
                value={registrationNumber}
                onChangeText={setRegistrationNumber}
            />
            <TextInput
                style={styles.input}
                placeholder="Statut"
                value={status}
                onChangeText={setStatus}
            />
            <TextInput
                style={styles.input}
                placeholder="Modèle"
                value={model}
                onChangeText={setModel}
            />
            <Button
                title={loading ? 'Chargement...' : (initialVehicle ? 'Mettre à jour' : 'Ajouter')}
                onPress={handleSubmit}
                disabled={loading}
            />
            {onClose && <Button title="Annuler" color="red" onPress={onClose} />}
        </View>
    );
};

const styles = StyleSheet.create({
    form: {
        marginVertical: 20,
        padding: 20,
        backgroundColor: '#fff',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
    },
    idContainer: {
        marginBottom: 10,
    },
    label: {
        fontWeight: 'bold',
    },
    input: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        marginBottom: 10,
        paddingLeft: 10,
        borderRadius: 5,
    },
});

export default VehicleForm;
