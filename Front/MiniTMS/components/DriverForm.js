import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet } from 'react-native';
import { addDriver, updateDriver } from '../services/driverService';

const DriverForm = ({ fetchDrivers, initialDriver = null, onClose }) => {
    const [name, setName] = useState(initialDriver ? initialDriver.name : '');
    const [licenseNumber, setLicenseNumber] = useState(initialDriver ? initialDriver.licenseNumber : '');
    const [phoneNumber, setPhoneNumber] = useState(initialDriver ? initialDriver.phoneNumber : '');

    const handleSubmit = async () => {
        const driverData = { name, licenseNumber, phoneNumber };
        let success;
        if (initialDriver) {
            // Mise à jour
            success = await updateDriver(initialDriver.id, driverData);
        } else {
            // Ajout
            success = await addDriver(driverData);
        }

        if (success) {
            fetchDrivers(); // Rafraîchir la liste des conducteurs
            onClose(); // Fermer le formulaire
        }
    };

    return (
        <View style={styles.form}>
            <TextInput
                style={styles.input}
                placeholder="Nom"
                value={name}
                onChangeText={setName}
            />
            <TextInput
                style={styles.input}
                placeholder="Numéro de licence"
                value={licenseNumber}
                onChangeText={setLicenseNumber}
            />
            <TextInput
                style={styles.input}
                placeholder="Numéro de téléphone"
                value={phoneNumber}
                onChangeText={setPhoneNumber}
            />
            <Button title={initialDriver ? 'Mettre à jour' : 'Ajouter'} onPress={handleSubmit} />
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
