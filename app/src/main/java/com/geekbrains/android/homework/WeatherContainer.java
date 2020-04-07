package com.geekbrains.android.homework;

import java.io.Serializable;

public class WeatherContainer implements Serializable {

    private static WeatherContainer instance;

    private String city;
    private String temperature;
    private String wildSpeed;
    private int position = 0;

    private WeatherContainer() { }

    public static WeatherContainer getInstance() {
        if (instance == null) instance = new WeatherContainer();

        return instance;
    }

    public String getWildSpeed() {
        return wildSpeed;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getPosition() {
        return position;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWildSpeed(String wildSpeed) {
        this.wildSpeed = wildSpeed;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
