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
    protected URI getUri() throws Exception {
        String encodedZipcode = URLEncoder.encode(zipcode, StandardCharsets.UTF_8);
        return new URI("https", "maps.googleapis.com", "/maps/api/geocode/json",
                "address=" + encodedZipcode + "&key=" + apiKey, null);
    }
}
