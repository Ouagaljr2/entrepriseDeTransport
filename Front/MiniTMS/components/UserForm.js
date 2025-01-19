import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { loginUser, createUser } from '../services/userService';

const UserForm = ({ onLoginSuccess }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [showCreateUser, setShowCreateUser] = useState(false);
    const [newUsername, setNewUsername] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [email, setEmail] = useState('');

    const handleLogin = async () => {
        setLoading(true);
        setError('');

        const success = await loginUser(username, password);
        setLoading(false);

        if (success) {
            onLoginSuccess();
            console.log('on lgin success a fini avec succès et onLoginSuccess appelé');
        } else {
            setError('Nom d\'utilisateur ou mot de passe incorrect');
            console.log('User non trouver le else du handle login');

        }
    };

    const handleCreateUser = async () => {
        const user = { username: newUsername, password: newPassword, email };
        const createdUser = await createUser(user);
        if (createdUser) {
            Alert.alert('Utilisateur créé avec succès');
            setShowCreateUser(false);
        } else {
            Alert.alert('Erreur', 'La création de l\'utilisateur a échoué');
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Se connecter</Text>
            <TextInput
                style={styles.input}
                placeholder="Nom d'utilisateur"
                value={username}
                onChangeText={setUsername}
            />
            <TextInput
                style={styles.input}
                placeholder="Mot de passe"
                secureTextEntry
                value={password}
                onChangeText={setPassword}
            />
            {error ? <Text style={styles.errorText}>{error}</Text> : null}
            <Button
                title={loading ? 'Chargement...' : 'Se connecter'}
                onPress={handleLogin}
                disabled={loading}
            />
            <Button
                title="Ajouter un utilisateur"
                onPress={() => setShowCreateUser(true)}
            />
            {showCreateUser && (
                <View style={styles.createUserContainer}>
                    <Text style={styles.title}>Créer un nouvel utilisateur</Text>
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
                        placeholder="Email"
                        value={email}
                        onChangeText={setEmail}
                    />
                    <Button title="Créer l'utilisateur" onPress={handleCreateUser} />
                </View>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
        backgroundColor: '#f5f5f5',
    },
    title: {
        fontSize: 22,
        fontWeight: 'bold',
        marginBottom: 20,
    },
    input: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        width: '100%',
        marginBottom: 15,
        paddingLeft: 10,
    },
    errorText: {
        color: 'red',
        marginBottom: 15,
    },
    createUserContainer: {
        marginTop: 20,
        padding: 20,
        backgroundColor: '#e8e8e8',
        width: '100%',
    },
});

export default UserForm;
