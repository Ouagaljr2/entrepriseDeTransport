// src/components/DriverForm.js
import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet } from 'react-native';
import { addDriver } from '../services/driverService';

const DriverForm = ({ fetchDrivers }) => {
    const [newDriver, setNewDriver] = useState({ name: '', licenseNumber: '', phoneNumber: '' });

    const handleAddDriver = async () => {
        const success = await addDriver(newDriver);
        if (success) {
            setNewDriver({ name: '', licenseNumber: '', phoneNumber: '' });
            fetchDrivers(); // Recharger la liste des conducteurs
        }
    };

    return (
        <View style={styles.form}>
            <TextInput
                style={styles.input}
                placeholder="Nom"
                value={newDriver.name}
                onChangeText={(text) => setNewDriver({ ...newDriver, name: text })}
            />
            <TextInput
                style={styles.input}
                placeholder="Numéro de Licence"
                value={newDriver.licenseNumber}
                onChangeText={(text) => setNewDriver({ ...newDriver, licenseNumber: text })}
            />
            <TextInput
                style={styles.input}
                placeholder="Numéro de Téléphone"
                value={newDriver.phoneNumber}
                onChangeText={(text) => setNewDriver({ ...newDriver, phoneNumber: text })}
            />
            <Button title="Ajouter" onPress={handleAddDriver} />
        </View>
    );
};

const styles = StyleSheet.create({
    form: {
        marginBottom: 20,
        padding: 10,
        backgroundColor: '#fff',
        borderRadius: 8,
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

export default DriverForm;
