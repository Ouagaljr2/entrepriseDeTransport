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
                <View style={styles.listContainer}>
                    {trips.map((item) => (
                        <View key={item.id} style={styles.tripItem}>
                            <View style={styles.tripDetails}>
                                <Text style={styles.title}>Tarajet #{item.id}</Text>
                                <Text>Origine: {item.origin}</Text>
                                <Text>Destination: {item.destination}</Text>
                                <Text>Distance: {item.distance} km</Text>
                                <Text>Date: {item.date}</Text>
                                <Text>Statut: {item.status}</Text>
                                <Text>Driver ID: {item.driver ? item.driver.id : 'Non assigné'}</Text>
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
    tripItem: {
        width: '48%', // Deux items par ligne (ajustez si nécessaire)
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
    title: {
        fontSize: 16,
        fontWeight: 'bold',
        marginBottom: 5,
    },
});

export default TripList;
