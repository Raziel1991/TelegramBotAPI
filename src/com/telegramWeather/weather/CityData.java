package com.telegramWeather.weather;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import com.google.gson.Gson;

public class CityData extends City {

    public CityData(String name, double lat, double lon, String country, String state) {
        super(name, lat, lon, country, state);
    }

    public static class AddressComponent {
        @SerializedName("long_name")
        String longName;

        @SerializedName("short_name")
        String shortName;

        List<String> types;
    }

    public static class Geometry {
        Location location;

        public static class Location {
            double lat;
            double lng;
        }
    }

    public static class Result {
        @SerializedName("address_components")
        List<AddressComponent> addressComponents;

        Geometry geometry;
    }

    @SerializedName("results")
    List<Result> results;

    public static CityData fromJson(String json) {
        Gson gson = new Gson();
        CityData cityData = gson.fromJson(json, CityData.class);

        // Extract relevant info from the first result (assuming the first result is valid)
        if (cityData.results != null && !cityData.results.isEmpty()) {
            Result result = cityData.results.get(0);
            Geometry.Location location = result.geometry.location;

            String name = null, country = null, state = null;
            for (AddressComponent component : result.addressComponents) {
                if (component.types.contains("locality")) {
                    name = component.longName;
                } else if (component.types.contains("country")) {
                    country = component.longName;
                } else if (component.types.contains("administrative_area_level_1")) {
                    state = component.longName;
                }
            }

            return new CityData(name, location.lat, location.lng, country, state);
        }
        return null;
    }
}
