import com.google.gson.Gson;
import com.telegramWeather.util.ReadKeyFromFile;
import com.telegramWeather.bot.TelegramBot;
import com.telegramWeather.weather.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {
    public static void main(String[] args) {
        // File paths for the API keys
        String weatherApiPath = "C:\\Users\\hampt\\IdeaProjects\\weatherKey.txt";
        String googleApiKeyPath = "C:\\Users\\hampt\\IdeaProjects\\googleMapsKey.txt";

        // The ZIP code to search for
        String zipCode = "M5T2Y4";


        // Create a com.telegramWeather.weather.GoogleMapsApiClient instance using the ZIP code and Google Maps API key
        ApiClient googleClient = new GoogleMapsApiClient(zipCode, ReadKeyFromFile.getKeyFromFile(googleApiKeyPath));


        // Make the API request and get the JSON response
        String cityDataJsonResponse = googleClient.getApiResponse();


        // Deserialize the JSON response to a com.telegramWeather.weather.CityData object
        CityData cityData = CityData.fromJson(cityDataJsonResponse);

        //Print Google API URI
        try {
            System.out.println(googleClient.getUri());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Print Weather API URI
        WeatherApiClient weatherApiClient = new WeatherApiClient(ReadKeyFromFile.getKeyFromFile(weatherApiPath),
                cityData.getLat() , cityData.getLon());
        try {
            System.out.println(weatherApiClient.getUri());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        ApiClient weatherClient = new WeatherApiClient(ReadKeyFromFile.getKeyFromFile(weatherApiPath),cityData.getLat(), cityData.getLon());
        String cityWeatherJsonResponse = weatherClient.getApiResponse();
        CityWeather cityWeather = new Gson().fromJson(cityWeatherJsonResponse, CityWeather.class);


        System.out.println(cityWeather.toString());

        /// Telegram stuff /////
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
