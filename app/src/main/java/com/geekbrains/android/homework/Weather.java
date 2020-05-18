package com.geekbrains.android.homework;

public class Weather {
    private String temperature;
    private float floatTemp;
    private String date;

    public Weather(String temperature, float temp, String date) {
        this.temperature = temperature;
        this.floatTemp = temp;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public float getFloatTemp() {
        return floatTemp;
    }
}