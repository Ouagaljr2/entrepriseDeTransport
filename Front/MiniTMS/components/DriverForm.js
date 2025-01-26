import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text, Dimensions } from 'react-native';
import { addDriver, updateDriver } from '../services/driverService';

const { width } = Dimensions.get('window');

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
            success = await updateDriver(initialDriver.id, driverData);
        } else {
            success = await addDriver(driverData);
        }

        if (success) {
            fetchDrivers();
            onClose();
        }
    };

    return (
        <View style={styles.overlay}>
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
    overlay: {
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        zIndex: 10,
    },
    form: {
        width: '90%',
        maxWidth: 400,
        padding: 20,
        backgroundColor: '#fff',
        borderRadius: 10,
        shadowColor: '#000',
        shadowOpacity: 0.2,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
        elevation: 5,
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
    },
});
export default DriverForm;
