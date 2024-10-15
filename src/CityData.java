import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CityData extends City {

    public CityData(ApiClient apiClient, String zip) {
        super(apiClient, zip);
    }

    @Override
    void deserializeJson(String jsonResponse) {
       
    }
}
