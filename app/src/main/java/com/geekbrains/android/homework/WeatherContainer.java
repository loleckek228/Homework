package com.geekbrains.android.homework;

import java.io.Serializable;

public class WeatherContainer implements Serializable {

    private static WeatherContainer instance;

    private String city;
    private String temperatureYesterday;
    private String temperatureToday;
    private String temperatureTomorrow;
    private String windSpeed;

    private int position = 0;

    private WeatherContainer() { }

    public static WeatherContainer getInstance() {
        if (instance == null) instance = new WeatherContainer();

        return instance;
    }

    public String getCity() {
        return city;
    }

    public String getTemperatureYesterday() {
        return temperatureYesterday;
    }

    public String getTemperatureToday() {
        return temperatureToday;
    }

    public String getTemperatureTomorrow() {
        return temperatureTomorrow;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public int getPosition() {
        return position;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperatureYesterday(String temperatureYesterday) {
        this.temperatureYesterday = temperatureYesterday;
    }

    public void setTemperatureToday(String temperatureToday) {
        this.temperatureToday = temperatureToday;
    }

    public void setTemperatureTomorrow(String temperatureTomorrow) {
        this.temperatureTomorrow = temperatureTomorrow;
    }

    public void setWindSpeed(String wildSpeed) {
        this.windSpeed = wildSpeed;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}