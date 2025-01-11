import 'react-native-gesture-handler';
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { SafeAreaView, Text, StyleSheet } from 'react-native';

// Importation des écrans
import HomeScreen from './HomeScreen';
import DriversScreen from './screens/DriversScreen';
import UserScreen from './screens/UserScreen';
import DashboardScreen from './screens/DashboardScreen';
import VehiclesScreen from './screens/VehiclesScreen';
import TripScreen from './screens/TripScreen';

// Créer le Top Tab Navigator
const Tab = createMaterialTopTabNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={{
          tabBarStyle: {
            backgroundColor: 'black',
            height: 70,
            paddingTop: 10,
          },
          tabBarActiveTintColor: '#fff',
          tabBarInactiveTintColor: 'gray',
          tabBarLabelStyle: {
            fontSize: 16,
            fontWeight: 'bold',
            paddingBottom: 5,
          },
        }}
      >
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Drivers" component={DriversScreen} />
        <Tab.Screen name="User" component={UserScreen} />
        <Tab.Screen name="Dashboard" component={DashboardScreen} />
        <Tab.Screen name="Vehicles" component={VehiclesScreen} />
        <Tab.Screen name="Trips" component={TripScreen} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}

export default App;
