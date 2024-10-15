
abstract public class City {
    //
    protected String name;
    protected double lat;
    protected double lng;
    protected String country;
    protected String state;
    protected String zip;
    protected ApiClient googleMapsApiClient;

    public City(ApiClient apiClient, String zip) {
        this.googleMapsApiClient = apiClient;
        this.zip = zip;
    }

    abstract void deserializeJson(String jsonResponse);

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
