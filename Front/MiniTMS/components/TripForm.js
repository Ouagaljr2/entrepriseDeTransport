import React, { useState, useEffect } from 'react';
import { View, TextInput, Button, StyleSheet, Picker } from 'react-native';
import { addTrip, updateTrip } from '../services/tripService'; // Assurez-vous d'avoir un service pour gérer les trajets
import { fetchDrivers } from '../services/driverService'; // Pour obtenir la liste des conducteurs
import { fetchVehicles } from '../services/vehicleService'; // Pour obtenir la liste des véhicules

const TripForm = ({ fetchTrips, initialTrip = null, onClose }) => {
    const [origin, setOrigin] = useState(initialTrip ? initialTrip.origin : '');
    const [destination, setDestination] = useState(initialTrip ? initialTrip.destination : '');
    const [distance, setDistance] = useState(initialTrip ? initialTrip.distance.toString() : '');
    const [date, setDate] = useState(initialTrip ? initialTrip.date : new Date().toISOString().split('T')[0]); // Date actuelle par défaut
    const [status, setStatus] = useState(initialTrip ? initialTrip.status : 'attribué'); // Statut par défaut
    const [driver, setDriver] = useState(initialTrip ? initialTrip.driver.id : '');
    const [vehicle, setVehicle] = useState(initialTrip ? initialTrip.vehicle.id : '');
    const [drivers, setDrivers] = useState([]);
    const [vehicles, setVehicles] = useState([]);

    useEffect(() => {
        // Charger les conducteurs et véhicules lors du montage du composant
        const loadData = async () => {
            const driverData = await fetchDrivers();
            const vehicleData = await fetchVehicles();
            setDrivers(driverData);
            setVehicles(vehicleData);
        };
        loadData();
    }, []);

    const handleSubmit = async () => {
        const tripData = {
            origin,
            destination,
            distance: parseFloat(distance),
            date,
            status,
            driver: { id: driver },
            vehicle: { id: vehicle },
        };
        let success;
        if (initialTrip) {
            // Mise à jour du trajet
            success = await updateTrip(initialTrip.id, tripData);
        } else {
            // Ajout d'un nouveau trajet
            success = await addTrip(tripData);
        }

        if (success) {
            fetchTrips(); // Rafraîchir la liste des trajets
            onClose(); // Fermer le formulaire
        }
    };

    return (
        <View style={styles.form}>
            <TextInput
                style={styles.input}
                placeholder="Origine"
                value={origin}
                onChangeText={setOrigin}
            />
            <TextInput
                style={styles.input}
                placeholder="Destination"
                value={destination}
                onChangeText={setDestination}
            />
            <TextInput
                style={styles.input}
                placeholder="Distance"
                value={distance}
                onChangeText={setDistance}
                keyboardType="numeric"
            />
            <TextInput
                style={styles.input}
                placeholder="Date"
                value={date}
                editable={false} // Rendre le champ de date non modifiable
            />
            <TextInput
                style={styles.input}
                placeholder="Statut"
                value={status}
                editable={false} // Rendre le champ de statut non modifiable
            />
            <Picker
                selectedValue={driver}
                onValueChange={setDriver}
                style={styles.input}
            >
                {drivers.map((d) => (
                    <Picker.Item key={d.id} label={d.name} value={d.id} />
                ))}
            </Picker>
            <Picker
                selectedValue={vehicle}
                onValueChange={setVehicle}
                style={styles.input}
            >
                {vehicles.map((v) => (
                    <Picker.Item key={v.id} label={v.registrationNumber} value={v.id} />
                ))}
            </Picker>
            <Button title={initialTrip ? 'Mettre à jour' : 'Ajouter'} onPress={handleSubmit} />
            {onClose && <Button title="Annuler" color="red" onPress={onClose} />}
        </View>
    );
};

const styles = StyleSheet.create({
    form: {
        marginVertical: 20,
        padding: 20,
        backgroundColor: '#fff',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowOffset: { width: 0, height: 4 },
        shadowRadius: 5,
    },
    input: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        marginBottom: 10,
        paddingLeft: 10,
        borderRadius: 5,
    },
});

export default TripForm;
