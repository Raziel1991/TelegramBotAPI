public class CityWeather {
    private double lat;
    private double lon;
    private String timezone;
    private int timezone_offset;
    private WeatherData current;


    public CityWeather(double lat, double lon, String timezone, int timezone_offset, WeatherData current) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezone_offset = timezone_offset;
        this.current = current;
    }


    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getTimezone_offset() {
        return timezone_offset;
    }


    // Getters for specific WeatherData fields for easier access
    public int getDt() {
        return current.getDt();
    }

    public int getSunrise() {
        return current.getSunrise();
    }

    public int getSunset() {
        return current.getSunset();
    }
    public double getTemperature() {
        return current.getTemp();
    }
    public double getFeelsLikeTemperature() {
        return current.getFeelsLike();
    }
    public int getPressure(){
        return current.getPressure();
    }
    public int getHumidity(){
        return current.getHumidity();
    }
    public double getDewPoint(){
        return current.getDew_point();
    }
    public double getUvi(){
        return current.getUvi();
    }
    public int getClouds(){
        return current.getClouds();
    }
    public int getVisibility(){
        return current.getVisibility();
    }
    public double getWindSpeed(){
        return current.getWind_speed();
    }
    public double getWindDeg(){
        return current.getWind_deg();
    }

    @Override
    public String toString() {
        return "CityWeather{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", temperature=" + current.getTemp() +
                ", feels like=" + current.getFeelsLike() +
                ", timezone='" + timezone + '\'' +
                ", timezone_offset=" + timezone_offset +
                ", current=" + current +
                '}';
    }
}
