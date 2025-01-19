import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, Button } from 'react-native';
import { getUserInfo, logoutUser, isAuthenticated } from '../services/userService';
import UserForm from '../components/UserForm';

const UserScreen = ({ onLoginSuccess, onLogout }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUserInfo = async () => {
            if (isAuthenticated()) {
                const userInfo = await getUserInfo();
                setUser(userInfo);
            } else {
                setUser(null);
            }
            setLoading(false);
        };

        fetchUserInfo();
    }, []);

    const handleLogout = () => {
        logoutUser();
        setUser(null); // Réinitialise l'état local de l'utilisateur
        onLogout(); // Notifie App que l'utilisateur s'est déconnecté
    };

    const handleLoginSuccess = async () => {
        const userInfo = await getUserInfo();
        setUser(userInfo);
        onLoginSuccess(); // Notifie App que l'utilisateur s'est connecté
    };

    if (loading) {
        return (
            <View style={styles.container}>
                <Text>Chargement...</Text>
            </View>
        );
    }

    return (
        <View style={styles.container}>
            {user ? (
                <>
                    <Text style={styles.title}>Bienvenue, {user.username}!</Text>
                    <Text style={styles.infoText}>Nom d'utilisateur: {user.username}</Text>
                    <Button title="Se déconnecter" onPress={handleLogout} />
                </>
            ) : (
                <UserForm onLoginSuccess={handleLoginSuccess} />
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#f5f5f5',
        padding: 20,
    },
    title: {
        fontSize: 22,
        fontWeight: 'bold',
        marginBottom: 20,
    },
    infoText: {
        fontSize: 16,
        marginBottom: 10,
    },
});

export default UserScreen;
