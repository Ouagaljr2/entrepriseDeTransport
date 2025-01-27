package com.entreprise.transport.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class Distance {

	private static final String ORS_API_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
	private static final String API_KEY = "5b3ce3597851110001cf62481d5515c46ba945418d742e781b7ac42f"; // Remplacez par votre clé API

	public double calculateDistance(String origin, String destination) throws Exception {
		// Étape 1 : Obtenez les coordonnées des villes
		String originCoordinates = getCoordinates(origin);
		String destinationCoordinates = getCoordinates(destination);

		// Étape 2 : Appelez l'API pour obtenir la distance
		OkHttpClient client = new OkHttpClient();
		String url = ORS_API_URL + "?api_key=" + API_KEY + "&start=" + originCoordinates + "&end="
				+ destinationCoordinates;

		Request request = new Request.Builder().url(url).get().build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new RuntimeException("API Request failed: " + response.message());
			}

			String responseBody = response.body().string();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(responseBody);

			// Distance en mètres
			return jsonNode.at("/features/0/properties/segments/0/distance").asDouble() / 1000.0; // Convertir en
																									// kilomètres
		}
	}

	private String getCoordinates(String cityName) throws Exception {
		// Utilisation de l'API Nominatim pour obtenir les coordonnées de la ville
		String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
		OkHttpClient client = new OkHttpClient();

		String url = String.format("%s?q=%s&format=json&limit=1", NOMINATIM_URL, cityName);

		Request request = new Request.Builder().url(url).get().header("User-Agent", "VotreApp/1.0").build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new RuntimeException("API Request failed: " + response.message());
			}

			String responseBody = response.body().string();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(responseBody);

			if (jsonNode.isEmpty()) {
				throw new RuntimeException("City not found: " + cityName);
			}

			String latitude = jsonNode.get(0).get("lat").asText();
			String longitude = jsonNode.get(0).get("lon").asText();
			return longitude + "," + latitude;
		}
	}

}
