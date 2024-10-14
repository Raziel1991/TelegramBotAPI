import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherApiClient extends ApiClient {
    private String city;

    public WeatherApiClient(String city, String apiKey) {
        super(apiKey);
        this.city = city;
    }

    @Override
    protected URI getUri() throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        return new URI("https", "api.weatherapi.com", "/v1/current.json",
                "q=" + encodedCity + "&key=" + apiKey, null);
    }
}
