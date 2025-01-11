// src/screens/DriverScreen.js
import React, { useState, useEffect } from 'react';
import { View, TextInput, Button, StyleSheet } from 'react-native';
import DriverForm from '../components/DriverForm';
import DriverList from '../components/DriverList';
import { fetchDrivers, searchDrivers } from '../services/driverService';

const DriverScreen = () => {
    const [drivers, setDrivers] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');

    const fetchDriverList = async () => {
        const data = await fetchDrivers();
        setDrivers(data);
    };

    const handleSearch = async () => {
        if (searchQuery.trim() === '') {
            fetchDriverList();
        } else {
            const data = await searchDrivers(searchQuery);
            setDrivers(data);
        }
    };

    useEffect(() => {
        fetchDriverList();
    }, []);

    return (
        <View style={styles.container}>
            <DriverForm fetchDrivers={fetchDriverList} />
            <TextInput
                style={styles.searchInput}
                placeholder="Rechercher par nom"
                value={searchQuery}
                onChangeText={setSearchQuery}
                onSubmitEditing={handleSearch}
            />
            <Button title="Rechercher" onPress={handleSearch} />
            <DriverList drivers={drivers} fetchDrivers={fetchDriverList} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#f5f5f5',
    },
    searchInput: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        marginBottom: 10,
        paddingLeft: 10,
        borderRadius: 5,
    },
});

export default DriverScreen;
