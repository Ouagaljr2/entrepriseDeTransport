import React, { useState } from 'react';
import { View, TextInput, Text, Button, StyleSheet } from 'react-native';
import { addVehicle, updateVehicle } from '../services/vehicleService';

const VehicleForm = ({ fetchVehicles, initialVehicle = null, onClose }) => {
    const [registrationNumber, setRegistrationNumber] = useState(initialVehicle ? initialVehicle.registrationNumber : '');
    const [status] = useState('Disponible'); // Statut fixe, non modifiable
    const [model, setModel] = useState(initialVehicle ? initialVehicle.model : '');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        const vehicleData = { registrationNumber, status, model };
        let success;
        setLoading(true);
        if (initialVehicle) {
            // Mise à jour du véhicule existant
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
        <View style={styles.container}>
            <View style={styles.form}>
                <Text style={styles.title}>{initialVehicle ? 'Modifier Véhicule' : 'Ajouter Véhicule'}</Text>
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
                    placeholder="Modèle"
                    value={model}
                    onChangeText={setModel}
                />
                {/* Affichage du statut, mais non modifiable */}
                <Text style={styles.statusText}>Statut: {status}</Text>

                <Button
                    title={loading ? 'Chargement...' : (initialVehicle ? 'Mettre à jour' : 'Ajouter')}
                    onPress={handleSubmit}
                    disabled={loading}
                />
                {onClose && <Button title="Annuler" color="red" onPress={onClose} />}
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#f0f0f0', // Couleur de fond pour mettre en valeur la boîte
    },
    form: {
        width: '90%',
        maxWidth: 400,
        padding: 20,
        backgroundColor: '#fff',
        borderRadius: 10,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
        elevation: 5, // Effet de relief supplémentaire pour Android
    },
    title: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 15,
        textAlign: 'center',
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
    statusText: {
        marginBottom: 15,
        color: '#555',
        textAlign: 'center',
        fontSize: 16,
        backgroundColor: '#f4f4f4',
        padding: 10,
        borderRadius: 5,
    },
});

export default VehicleForm;
