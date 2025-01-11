import React from 'react';
import { SafeAreaView, StyleSheet, Text, Button } from 'react-native';

const DashboardScreen = ({ navigation }) => (
    <SafeAreaView style={styles.container}>
        <Text>Tableau de Bord</Text>
        <Button title="Conducteurs" onPress={() => navigation.navigate('Drivers')} />
        <Button title="Véhicules" onPress={() => navigation.navigate('Vehicles')} />
        <Button title="Trajets" onPress={() => navigation.navigate('Routes')} />
    </SafeAreaView>
);

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#f5f5f5',
    },
});

export default DashboardScreen;
