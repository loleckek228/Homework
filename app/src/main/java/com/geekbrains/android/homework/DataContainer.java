package com.geekbrains.android.homework;

import android.widget.TextView;

import java.io.Serializable;

public class DataContainer implements Serializable {

    private static DataContainer instance;

    private String city;
    private String temperature;
    private String wildSpeed;

    private DataContainer() { }

    public static DataContainer getInstance() {
        if (instance == null) instance = new DataContainer();

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

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWildSpeed(String wildSpeed) {
        this.wildSpeed = wildSpeed;
    }
}
