package com.geekbrains.android.homework;

public class Weather {
    private String temperature;
    private float temp;
    private String date;

    public Weather(String temperature, float temp, String date) {
        this.temperature = temperature;
        this.temp = temp;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public float getTemp() {
        return temp;
    }
}