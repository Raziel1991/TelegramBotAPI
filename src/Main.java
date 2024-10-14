public class Main {
    public static void main(String[] args) {
        //Key paths
        String weatherApiPath = "C:\\Users\\hampt\\IdeaProjects\\weatherKey.txt";
        String googleApiKey =  "C:\\Users\\hampt\\IdeaProjects\\googleMapsKey.txt";
        String zipCode = "M5T2Y4";
        //Call the static method from ReadKeyFromFile to get the weather API
        System.out.println(ReadKeyFromFile.getKeyFromFile(weatherApiPath));
        System.out.println(ReadKeyFromFile.getKeyFromFile(googleApiKey));

        ApiClient googleApiClient  = new GoogleMapsApiClient(zipCode, ReadKeyFromFile.getKeyFromFile(googleApiKey));
        System.out.println(googleApiClient.getApiResponse());
    }
}
