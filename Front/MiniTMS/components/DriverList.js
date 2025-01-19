import React, { useState } from 'react';
import { View, Text, TouchableOpacity, FlatList, StyleSheet } from 'react-native';
import { deleteDriver } from '../services/driverService';
import DriverForm from './DriverForm';

const DriverList = ({ drivers, fetchDrivers }) => {
    const [editingDriver, setEditingDriver] = useState(null);

    const handleDelete = async (id) => {
        const success = await deleteDriver(id);
        if (success) fetchDrivers();
    };

    const handleEdit = (driver) => {
        setEditingDriver(driver); // Définir le conducteur à modifier
    };

    const closeEditForm = () => {
        setEditingDriver(null); // Fermer le formulaire d'édition
    };

    return (
        <View>
            {editingDriver ? (
                <DriverForm
                    fetchDrivers={fetchDrivers}
                    initialDriver={editingDriver}
                    onClose={closeEditForm}
                />
            ) : (
                <FlatList
                    data={drivers}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.driverItem}>
                            <View style={styles.driverDetails}>
                                <Text>ID: {item.id}</Text>
                                <Text>Nom: {item.name}</Text>
                                <Text>Licence: {item.licenseNumber}</Text>
                                <Text>Téléphone: {item.phoneNumber}</Text>
                            </View>
                            <View style={styles.driverActions}>
                                <TouchableOpacity style={styles.button} onPress={() => handleDelete(item.id)}>
                                    <Text style={styles.buttonText}>Supprimer</Text>
                                </TouchableOpacity>
                                <TouchableOpacity style={styles.button} onPress={() => handleEdit(item)}>
                                    <Text style={styles.buttonText}>Mettre à jour</Text>
                                </TouchableOpacity>
                            </View>
                        </View>
                    )}
                />
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    driverItem: {
        padding: 10,
        marginBottom: 10,
        backgroundColor: '#fff',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
    },
    driverDetails: {
        marginBottom: 10,
    },
    driverActions: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    button: {
        backgroundColor: '#007BFF',
        padding: 10,
        borderRadius: 5,
        marginHorizontal: 5,
    },
    buttonText: {
        color: '#fff',
        fontWeight: 'bold',
    },
});

export default DriverList;
