package com.telegramWeather.util;

import com.google.gson.Gson;
import com.telegramWeather.weather.*;

public class WeatherHandler {

    private static final String WEATHER_API_PATH = "C:\\Users\\hampt\\IdeaProjects\\weatherKey.txt";
    private static final String GOOGLE_API_KEY_PATH = "C:\\Users\\hampt\\IdeaProjects\\googleMapsKey.txt";

    public static WeatherResponse getWeatherByZipCode(String zipCode) {
        try {
            // Google Maps API Client to get city data
            ApiClient gmClient = new GoogleMapsApiClient(zipCode, ReadKeyFromFile.getKeyFromFile(GOOGLE_API_KEY_PATH));
            CityData cityData = CityData.fromJson(gmClient.getApiResponse());

            if (cityData == null) {
                return null; // Return null if city data couldn't be retrieved
            }

            // Weather API Client to get weather data
            ApiClient weatherApiClient = new WeatherApiClient(ReadKeyFromFile.getKeyFromFile(WEATHER_API_PATH), cityData.getLat(), cityData.getLon());
            String weatherResponse = weatherApiClient.getApiResponse();

            // Deserialize and return CityWeather object
            return new Gson().fromJson(weatherResponse, WeatherResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
