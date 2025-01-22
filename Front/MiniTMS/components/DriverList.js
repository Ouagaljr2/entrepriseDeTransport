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
        setEditingDriver(driver);
    };

    const closeEditForm = () => {
        setEditingDriver(null);
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
                <View style={styles.driverListContainer}>
                    {drivers.map((item) => (
                        <View key={item.id} style={styles.driverItem}>
                            <View style={styles.driverDetails}>
                                <Text style={styles.title}>Conducteur #{item.id}</Text>
                                <Text>Nom: {item.name}</Text>
                                <Text>Licence: {item.licenseNumber}</Text>
                                <Text>Téléphone: {item.phoneNumber}</Text>
                                <Text>Email: {item.email}</Text>
                                <Text>Statut: {item.status}</Text>
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
                    ))}
                </View>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    driverListContainer: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'space-between',
    },
    driverItem: {
        width: '48%', // Ajustez la largeur pour que deux éléments tiennent sur une ligne
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
    title: {
        fontSize: 16,
        fontWeight: 'bold',
        marginBottom: 5,
    },
});

export default DriverList;
