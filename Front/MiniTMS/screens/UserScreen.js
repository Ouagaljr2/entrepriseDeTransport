import React from 'react';
import { SafeAreaView, StyleSheet, Text, Button } from 'react-native';

const UserScreen = ({ navigation }) => (
    <SafeAreaView style={styles.container}>
        <Text>Authentification</Text>
        <Button title="Login" onPress={() => navigation.navigate('Dashboard')} />
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

export default UserScreen;
