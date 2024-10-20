package com.telegramWeather.weather;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleMapsApiClient extends ApiClient {
    private final String zipcode;

    public GoogleMapsApiClient(String zipcode, String apiKey) {
        super(apiKey);
        this.zipcode = zipcode;
    }

    @Override
    public URI getUri() throws Exception {
        // Corrected the second parameter to URLEncoder.encode to use the charset name
        String encodedZipcode = URLEncoder.encode(zipcode, StandardCharsets.UTF_8.name());
        return new URI("https", "maps.googleapis.com", "/maps/api/geocode/json",
                "address=" + encodedZipcode + "&key=" + apiKey, null);
    }
}
