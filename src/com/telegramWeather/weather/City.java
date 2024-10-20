package com.telegramWeather.weather;

public abstract class City {
    private final String name;
    private final double lat;
    private final double lon;
    private final String country;
    private final String state;

    public City(String name, double lat, double lon, String country, String state) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.country = country;
        this.state = state;
    }

    // Getters for fields
    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public String getCountry() { return country; }
    public String getState() { return state; }
}
