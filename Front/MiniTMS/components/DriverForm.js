import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text } from 'react-native';
import { addDriver, updateDriver } from '../services/driverService';

const DriverForm = ({ fetchDrivers, initialDriver = null, onClose }) => {
    const [name, setName] = useState(initialDriver ? initialDriver.name : '');
    const [licenseNumber, setLicenseNumber] = useState(initialDriver ? initialDriver.licenseNumber : '');
    const [phoneNumber, setPhoneNumber] = useState(initialDriver ? initialDriver.phoneNumber : '');
    const [email, setEmail] = useState(initialDriver ? initialDriver.email : '');
    const [status] = useState('Disponible'); // Statut fixe, non modifiable

    const handleSubmit = async () => {
        const driverData = { name, licenseNumber, phoneNumber, email, status };
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
        <View style={styles.container}>
            <View style={styles.form}>
                <Text style={styles.title}>{initialDriver ? 'Modifier Conducteur' : 'Ajouter Conducteur'}</Text>
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
                <TextInput
                    style={styles.input}
                    placeholder="Email"
                    value={email}
                    onChangeText={setEmail}
                />
                <Text style={styles.statusText}>Statut: {status}</Text>
                <Button title={initialDriver ? 'Mettre à jour' : 'Ajouter'} onPress={handleSubmit} />
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
        backgroundColor: '#f0f0f0', // Couleur de fond pour mieux distinguer la boîte
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
        elevation: 5, // Pour un effet de relief supplémentaire sur Android
    },
    title: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 15,
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
        textAlign: 'center',
        fontSize: 16,
    },
});

export default DriverForm;
