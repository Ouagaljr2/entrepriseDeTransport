import React, { useState } from 'react';
import { View, Text, TouchableOpacity, FlatList, StyleSheet } from 'react-native';
import { deleteTrip } from '../services/tripService';
import TripForm from './TripForm';

const TripList = ({ trips, fetchTrips }) => {
    const [editingTrip, setEditingTrip] = useState(null);

    const handleDelete = async (id) => {
        const success = await deleteTrip(id);
        if (success) fetchTrips();
    };

    const handleEdit = (trip) => {
        setEditingTrip(trip);
    };

    const closeEditForm = () => {
        setEditingTrip(null);
    };

    return (
        <View>
            {editingTrip ? (
                <TripForm
                    fetchTrips={fetchTrips}
                    initialTrip={editingTrip}
                    onClose={closeEditForm}
                />
            ) : (
                <FlatList
                    data={trips}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.tripItem}>
                            <View style={styles.tripDetails}>
                                <Text>ID: {item.id}</Text>
                                <Text>Origine: {item.origin}</Text>
                                <Text>Destination: {item.destination}</Text>
                                <Text>Distance: {item.distance} km</Text>
                                <Text>Date: {item.date}</Text>
                                <Text>Statut: {item.status}</Text>

                                {/* Affichage de l'ID du conducteur */}
                                <Text>Driver ID: {item.driver ? item.driver.id : 'Non assigné'}</Text>

                                {/* Affichage de l'ID du véhicule */}
                                <Text>Vehicle ID: {item.vehicle ? item.vehicle.id : 'Non assigné'}</Text>
                            </View>
                            <View style={styles.tripActions}>
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
    tripItem: {
        padding: 10,
        marginBottom: 10,
        backgroundColor: '#fff',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
    },
    tripDetails: {
        marginBottom: 10,
    },
    tripActions: {
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

export default TripList;
