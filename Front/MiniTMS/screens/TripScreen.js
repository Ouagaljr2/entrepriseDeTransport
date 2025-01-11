import React from 'react';
import { SafeAreaView, StyleSheet, Text, FlatList } from 'react-native';

const sampleRoutes = [{ id: 1, route: 'Route 101' }, { id: 2, route: 'Route 202' }];

const TripScreen = () => (
    <SafeAreaView style={styles.container}>
        <Text>Gestion des Trajets</Text>
        <FlatList
            data={sampleRoutes}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => <Text>{item.route}</Text>}
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

export default TripScreen;
