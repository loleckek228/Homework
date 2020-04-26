package com.geekbrains.android.homework;

import java.io.Serializable;

public class WeatherContainer implements Serializable {

    private static WeatherContainer instance;

    private String city;
    private String temperatureToday;
    private String windSpeed;
    private String description;
    private String date;
    private String icon;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private WeatherContainer() {
    }

    public static WeatherContainer getInstance() {
        if (instance == null) instance = new WeatherContainer();

        return instance;
    }

    public String getCity() {
        return city;
    }

    public String getTemperatureToday() {
        return temperatureToday;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperatureToday(String temperatureToday) {
        this.temperatureToday = temperatureToday;
    }

    public void setWindSpeed(String wildSpeed) {
        this.windSpeed = wildSpeed;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}