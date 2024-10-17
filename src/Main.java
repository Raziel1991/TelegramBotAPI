
public class Main {
    public static void main(String[] args) {
        // File paths for the API keys
        String weatherApiPath = "C:\\Users\\hampt\\IdeaProjects\\weatherKey.txt";
        String googleApiKeyPath = "C:\\Users\\hampt\\IdeaProjects\\googleMapsKey.txt";

        // The ZIP code to search for
        String zipCode = "M5T2Y4";

        // Retrieve and print the API keys from the respective files
        System.out.println("Weather API Key: " + ReadKeyFromFile.getKeyFromFile(weatherApiPath));
        System.out.println("Google Maps API Key: " + ReadKeyFromFile.getKeyFromFile(googleApiKeyPath));

        // Create a GoogleMapsApiClient instance using the ZIP code and Google Maps API key
        ApiClient googleClient = new GoogleMapsApiClient(zipCode, ReadKeyFromFile.getKeyFromFile(googleApiKeyPath));

        // Make the API request and get the JSON response
        String cityDataJsonResponse = googleClient.getApiResponse();


        // Deserialize the JSON response to a CityData object
        CityData cityData = CityData.fromJson(cityDataJsonResponse);

        // If the deserialization was successful, print the extracted city information
        if (cityData != null) {
            System.out.println("City: " + cityData.getName());
            System.out.println("Latitude: " + cityData.getLat());
            System.out.println("Longitude: " + cityData.getLon());
            System.out.println("Country: " + cityData.getCountry());
            System.out.println("State: " + cityData.getState());
        } else {
            System.out.println("Failed to parse city data.");
        }

        //Get maps URI
        try {
            System.out.println(googleClient.getUri());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Get Weather API URI
        WeatherApiClient weatherApiClient = new WeatherApiClient(ReadKeyFromFile.getKeyFromFile(weatherApiPath),
                cityData.getLat() , cityData.getLon());

        try {
            System.out.println(weatherApiClient.getUri());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
