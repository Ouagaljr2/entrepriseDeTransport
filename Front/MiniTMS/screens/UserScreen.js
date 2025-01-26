import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert, Modal, TouchableOpacity } from 'react-native';
import { getUserInfo, logoutUser, isAuthenticated } from '../services/userService';
import UserForm from '../components/UserForm';
import { createUser } from '../services/userService';

const UserScreen = ({ onLoginSuccess, onLogout }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [showCreateUser, setShowCreateUser] = useState(false);
    const [newUsername, setNewUsername] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [role, setRole] = useState('');
    const [error, setError] = useState('');


    useEffect(() => {
        const fetchUserInfo = async () => {
            setError('');
            if (await isAuthenticated()) { // Attendre la réponse de isAuthenticated
                console.log('User is authenticated dans le useEffect de UserScreen');
                const userInfo = await getUserInfo();
                setUser(userInfo);
            } else {
                console.log('User is not authenticated dans le useEffect de UserScreen');
                setUser(null);
            }
            setLoading(false);
        };

        fetchUserInfo();
    }, []);


    const handleLogout = () => {
        logoutUser();
        setUser(null);
        onLogout();
    };

    const handleLoginSuccess = async () => {
        const userInfo = await getUserInfo();
        setUser(userInfo);
        onLoginSuccess();
    };

    const toggleCreateUserForm = () => {
        setShowCreateUser(!showCreateUser);
    };

    const handleCreateUser = async () => {
        const user = { username: newUsername, password: newPassword, role };
        const createdUser = await createUser(user);
        if (createdUser) {
            Alert.alert('Utilisateur créé avec succès');
            setShowCreateUser(false);
            setNewUsername('');
            setNewPassword('');
            setRole('');
            if (createdUser.error) {
                setError(true);
            }
        } else {
            Alert.alert('Erreur', 'La création de l\'utilisateur a échoué');
        }
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
                    <Text style={styles.infoText}>Roles de l'utilisateur: {user.role}</Text>

                    {user.role === 'Admin' && (
                        <>
                            <Button title="Ajouter un utilisateur" onPress={toggleCreateUserForm} />
                            <Modal
                                visible={showCreateUser}
                                transparent={true}
                                animationType="fade"
                                onRequestClose={() => setShowCreateUser(false)}
                            >
                                <View style={styles.modalOverlay}>
                                    <View style={styles.modalContent}>
                                        <Text style={styles.modalTitle}>Créer un nouvel utilisateur</Text>
                                        <TextInput
                                            style={styles.input}
                                            placeholder="Nom d'utilisateur"
                                            value={newUsername}
                                            onChangeText={setNewUsername}
                                        />
                                        <TextInput
                                            style={styles.input}
                                            placeholder="Mot de passe"
                                            secureTextEntry
                                            value={newPassword}
                                            onChangeText={setNewPassword}
                                        />
                                        <TextInput
                                            style={styles.input}
                                            placeholder="Role"
                                            value={role}
                                            onChangeText={setRole}
                                        />
                                        <View style={styles.buttonContainer}>
                                            <TouchableOpacity
                                                style={styles.cancelButton}
                                                onPress={() => setShowCreateUser(false)}
                                            >
                                                <Text style={styles.buttonText}>Annuler</Text>
                                            </TouchableOpacity>
                                            <TouchableOpacity
                                                style={styles.createButton}
                                                onPress={handleCreateUser}
                                            >
                                                <Text style={styles.buttonText}>Créer</Text>
                                            </TouchableOpacity>
                                            {error ? <Text style={styles.errorText}>{error}</Text> : null}

                                        </View>
                                    </View>
                                </View>
                            </Modal>
                        </>
                    )}
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
        fontSize: 26,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
        color: '#333',
    },
    infoText: {
        fontSize: 20,
        marginBottom: 10,
        textAlign: 'center',
        color: '#555',
    },
    modalOverlay: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
    },
    modalContent: {
        width: '80%',
        backgroundColor: 'white',
        padding: 20,
        borderRadius: 10,
        alignItems: 'center',
    },
    modalTitle: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
    },
    input: {
        width: '100%',
        padding: 10,
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 5,
        marginBottom: 15,
    },
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        width: '100%',
    },
    cancelButton: {
        backgroundColor: '#ff4d4d',
        padding: 10,
        borderRadius: 5,
        flex: 1,
        marginRight: 5,
        alignItems: 'center',
    },
    createButton: {
        backgroundColor: '#4caf50',
        padding: 10,
        borderRadius: 5,
        flex: 1,
        marginLeft: 5,
        alignItems: 'center',
    },
    buttonText: {
        color: 'white',
        fontWeight: 'bold',
    },
    errorText: {
        color: 'red',
        marginBottom: 15,
    },

});

export default UserScreen;
