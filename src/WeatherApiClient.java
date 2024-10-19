import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherApiClient extends ApiClient {
    private static final String[] EXCLUSIONS = {"minutely", "daily"};
    private final double LATITUDE;
    private final double LONGITUDE;

    //get URI data from 3.0 weather data

    public WeatherApiClient(String apiKey, double latitude, double longitude) {
        super(apiKey);
        this.LATITUDE = latitude;
        this.LONGITUDE = longitude;
    }

    // Creates the URI for the Weather API call
    //Using metric as the units, may request default(kelvin) and make the math locally.
    @Override
    public URI getUri() throws Exception {
        return new URI("https","api.openweathermap.org","/data/3.0/onecall",
                "lat=" + LATITUDE + "&lon=" + LONGITUDE + "&exclude=" +
                        EXCLUSIONS[0] + ","  + EXCLUSIONS[1] + "&units=metric" + "&appid=" + apiKey,null);
    }
}
