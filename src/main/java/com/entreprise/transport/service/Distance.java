package com.entreprise.transport.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Service pour calculer la distance entre deux villes en utilisant l'API
 * OpenRouteService.
 * 
 * Ce service permet de calculer la distance entre deux villes en kilomètres en
 * utilisant l'API OpenRouteService.
 * 
 * Auteur: Ouagal Mahamat
 */
@Service
public class Distance {

	// URL de l'API OpenRouteService pour obtenir la distance
	private static final String ORS_API_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
	
	// Clé API injectée depuis les propriétés Spring
	@Value("${distance.api.key}")
	private String API_KEY; // Remplacez par votre clé API

	/**
	 * Calcule la distance entre deux villes (origine et destination) en appelant l'API OpenRouteService.
	 * 
	 * @param origin Le nom de la ville d'origine.
	 * @param destination Le nom de la ville de destination.
	 * @return La distance entre les deux villes en kilomètres.
	 * @throws Exception Si une erreur se produit lors de l'appel API.
	 */
	public double calculateDistance(String origin, String destination) throws Exception {
		// Étape 1 : Obtenez les coordonnées des villes
		String originCoordinates = getCoordinates(origin);
		String destinationCoordinates = getCoordinates(destination);

		// Étape 2 : Formulation de l'URL de l'API OpenRouteService avec les coordonnées obtenues
		OkHttpClient client = new OkHttpClient();
		String url = ORS_API_URL + "?api_key=" + API_KEY + "&start=" + originCoordinates + "&end="
				+ destinationCoordinates;

		// Envoi de la requête HTTP GET à l'API
		Request request = new Request.Builder().url(url).get().build();

		// Exécution de la requête
		try (Response response = client.newCall(request).execute()) {
			// Si la réponse de l'API est invalide
			if (!response.isSuccessful()) {
				throw new RuntimeException("API Request failed: " + response.message());
			}

			// Lecture de la réponse JSON
			String responseBody = response.body().string();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(responseBody);

			// Extraction de la distance (en mètres) de la réponse JSON et conversion en kilomètres
			return jsonNode.at("/features/0/properties/segments/0/distance").asDouble() / 1000.0; // Convertir en kilomètres
		}
	}

	/**
	 * Obtient les coordonnées géographiques (latitude, longitude) d'une ville en appelant l'API Nominatim d'OpenStreetMap.
	 * 
	 * @param cityName Le nom de la ville dont on souhaite obtenir les coordonnées.
	 * @return Une chaîne de caractères représentant les coordonnées sous le format "longitude,latitude".
	 * @throws Exception Si une erreur se produit lors de l'appel API.
	 */
	private String getCoordinates(String cityName) throws Exception {
		// URL de l'API Nominatim pour obtenir les coordonnées d'une ville
		String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
		OkHttpClient client = new OkHttpClient();

		// Construction de l'URL avec le nom de la ville
		String url = String.format("%s?q=%s&format=json&limit=1", NOMINATIM_URL, cityName);

		// Envoi de la requête HTTP GET à l'API Nominatim
		Request request = new Request.Builder().url(url).get().header("User-Agent", "VotreApp/1.0").build();

		// Exécution de la requête
		try (Response response = client.newCall(request).execute()) {
			// Si la réponse de l'API est invalide
			if (!response.isSuccessful()) {
				throw new RuntimeException("API Request failed: " + response.message());
			}

			// Lecture de la réponse JSON
			String responseBody = response.body().string();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(responseBody);

			// Si aucune ville n'a été trouvée
			if (jsonNode.isEmpty()) {
				throw new RuntimeException("City not found: " + cityName);
			}

			// Extraction de la latitude et de la longitude
			String latitude = jsonNode.get(0).get("lat").asText();
			String longitude = jsonNode.get(0).get("lon").asText();
			// Retourne les coordonnées sous le format "longitude,latitude"
			return longitude + "," + latitude;
		}
	}

}
