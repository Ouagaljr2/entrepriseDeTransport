import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { deleteVehicle } from '../services/vehicleService';
import VehicleForm from './VehicleForm';

const VehicleList = ({ vehicles, fetchVehicles }) => {
    const [editingVehicle, setEditingVehicle] = useState(null);

    const handleDelete = async (id) => {
        const success = await deleteVehicle(id);
        if (success) fetchVehicles();
    };

    const handleEdit = (vehicle) => {
        setEditingVehicle(vehicle);
    };

    const closeEditForm = () => {
        setEditingVehicle(null);
    };

    return (
        <View>
            {editingVehicle ? (
                <VehicleForm
                    fetchVehicles={fetchVehicles}
                    initialVehicle={editingVehicle}
                    onClose={closeEditForm}
                />
            ) : (
                <View style={styles.listContainer}>
                    {vehicles.map((item) => (
                        <View key={item.id} style={styles.vehicleItem}>
                            <View style={styles.vehicleDetails}>
                                <Text style={styles.title}>Véhicule #{item.id}</Text>
                                <Text>Numéro d'immatriculation: {item.registrationNumber}</Text>
                                <Text>Modèle: {item.model}</Text>
                                <Text>Statut: {item.status}</Text>
                            </View>
                            <View style={styles.vehicleActions}>
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
    listContainer: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'space-between',
        padding: 10,
    },
    vehicleItem: {
        width: '48%', // Ajuster la largeur pour deux éléments par ligne
        backgroundColor: '#fff',
        borderRadius: 8,
        padding: 15,
        marginBottom: 10,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
        elevation: 3, // Ajoute une ombre pour Android
    },
    vehicleDetails: {
        marginBottom: 10,
    },
    title: {
        fontSize: 16,
        fontWeight: 'bold',
        marginBottom: 5,
    },
    vehicleActions: {
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

export default VehicleList;
