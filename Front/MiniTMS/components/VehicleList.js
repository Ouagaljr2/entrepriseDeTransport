import React, { useState } from 'react';
import { View, Text, TouchableOpacity, FlatList, StyleSheet } from 'react-native';
import { deleteVehicle } from '../services/vehicleService';
import VehicleForm from './VehicleForm'; // Assurez-vous d'avoir un formulaire de véhicule

const VehicleList = ({ vehicles, fetchVehicles }) => {
    const [editingVehicle, setEditingVehicle] = useState(null);

    const handleDelete = async (id) => {
        const success = await deleteVehicle(id);
        if (success) fetchVehicles();
    };

    const handleEdit = (vehicle) => {
        setEditingVehicle(vehicle); // Définir le véhicule à modifier
    };

    const closeEditForm = () => {
        setEditingVehicle(null); // Fermer le formulaire d'édition
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
                <FlatList
                    data={vehicles}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.vehicleItem}>
                            <View style={styles.vehicleDetails}>
                                <Text>ID: {item.id}</Text>
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
                    )}
                />
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    vehicleItem: {
        padding: 10,
        marginBottom: 10,
        backgroundColor: '#fff',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
    },
    vehicleDetails: {
        marginBottom: 10,
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
