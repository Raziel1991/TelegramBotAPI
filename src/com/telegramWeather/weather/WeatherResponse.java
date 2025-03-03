package com.telegramWeather.weather;

import java.util.List;
import java.time.LocalTime;

// Main class for the entire JSON response
public class WeatherResponse {
    private double lat;
    private double lon;
    private String timezone;
    private int timezone_offset;
    private Current current;
    private List<Hourly> hourly;


    // Getters and Setters
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public int getTimezoneOffset() { return timezone_offset; }
    public void setTimezoneOffset(int timezone_offset) { this.timezone_offset = timezone_offset; }

    public Current getCurrent() { return current; }
    public void setCurrent(Current current) { this.current = current; }

    public List<Hourly> getHourly() { return hourly; }
    public void setHourly(List<Hourly> hourly) { this.hourly = hourly; }

    public double getTemperature(){ return  current.getTemp(); }
    public double getFeelsLike(){ return  current.getFeelsLike(); }
    public long getDt(){ return  current.getDt(); }

    public double getTemperatureHourly(int time){
        LocalTime currentTime = LocalTime.now();
        return  hourly.get(time - currentTime.getHour()).getTemp();
    }
    public double getFeelsLikeHourly(int time){
        LocalTime currentTime = LocalTime.now();
        return  hourly.get(time - currentTime.getHour()).getFeelsLike();
    }

    public long getDtHourly(int time){
        LocalTime currentTime = LocalTime.now();
        return  hourly.get(time - currentTime.getHour()).getDt();
    }

    @Override
    public String toString() {
        return "Timezone: " + timezone +
                "\nTemperature: " + current.getTemp() +
                "\nFeels Like: " + current.getFeelsLike();

    }
}

// Class for the "current" weather conditions
class Current {
    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double uvi;
    private int clouds;
    private int visibility;
    private double wind_speed;
    private int wind_deg;
    private List<Weather> weather;

    // Getters and Setters
    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }

    public long getSunrise() { return sunrise; }
    public void setSunrise(long sunrise) { this.sunrise = sunrise; }

    public long getSunset() { return sunset; }
    public void setSunset(long sunset) { this.sunset = sunset; }

    public double getTemp() { return temp; }
    public void setTemp(double temp) { this.temp = temp; }

    public double getFeelsLike() { return feels_like; }
    public void setFeelsLike(double feels_like) { this.feels_like = feels_like; }

    public int getPressure() { return pressure; }
    public void setPressure(int pressure) { this.pressure = pressure; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public double getDewPoint() { return dew_point; }
    public void setDewPoint(double dew_point) { this.dew_point = dew_point; }

    public double getUvi() { return uvi; }
    public void setUvi(int uvi) { this.uvi = uvi; }

    public int getClouds() { return clouds; }
    public void setClouds(int clouds) { this.clouds = clouds; }

    public int getVisibility() { return visibility; }
    public void setVisibility(int visibility) { this.visibility = visibility; }

    public double getWindSpeed() { return wind_speed; }
    public void setWindSpeed(double wind_speed) { this.wind_speed = wind_speed; }

    public int getWindDeg() { return wind_deg; }
    public void setWindDeg(int wind_deg) { this.wind_deg = wind_deg; }

    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }
}

// Class for hourly data
class Hourly {
    private long dt;
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double uvi;
    private int clouds;
    private int visibility;
    private double wind_speed;
    private int wind_deg;
    private double wind_gust;
    private List<Weather> weather;
    private double pop;

    // Getters and Setters
    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }

    public double getTemp() { return temp; }
    public void setTemp(double temp) { this.temp = temp; }

    public double getFeelsLike() { return feels_like; }
    public void setFeelsLike(double feels_like) { this.feels_like = feels_like; }

    public int getPressure() { return pressure; }
    public void setPressure(int pressure) { this.pressure = pressure; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public double getDewPoint() { return dew_point; }
    public void setDewPoint(double dew_point) { this.dew_point = dew_point; }

    public double getUvi() { return uvi; }
    public void setUvi(int uvi) { this.uvi = uvi; }

    public int getClouds() { return clouds; }
    public void setClouds(int clouds) { this.clouds = clouds; }

    public int getVisibility() { return visibility; }
    public void setVisibility(int visibility) { this.visibility = visibility; }

    public double getWindSpeed() { return wind_speed; }
    public void setWindSpeed(double wind_speed) { this.wind_speed = wind_speed; }

    public int getWindDeg() { return wind_deg; }
    public void setWindDeg(int wind_deg) { this.wind_deg = wind_deg; }

    public double getWindGust() { return wind_gust; }
    public void setWindGust(double wind_gust) { this.wind_gust = wind_gust; }

    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }

    public double getPop() { return pop; }
    public void setPop(double pop) { this.pop = pop; }
}

// Class for weather data
class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMain() { return main; }
    public void setMain(String main) { this.main = main; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
