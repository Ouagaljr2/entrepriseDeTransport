import React from 'react';
import { SafeAreaView, StyleSheet, Text, FlatList } from 'react-native';

const sampleVehicles = [{ id: 1, model: 'Truck A' }, { id: 2, model: 'Van B' }];

const VehiclesScreen = () => (
    <SafeAreaView style={styles.container}>
        <Text>Gestion des Véhicules</Text>
        <FlatList
            data={sampleVehicles}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => <Text>{item.model}</Text>}
        />
    </SafeAreaView>
);

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
        backgroundColor: '#f5f5f5',
    },
});

export default VehiclesScreen;
