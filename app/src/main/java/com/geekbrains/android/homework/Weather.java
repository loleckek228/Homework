package com.geekbrains.android.homework;

public class Weather {
    private String temperature;
    private String date;

    public Weather(String temperature, String date) {
        this.temperature = temperature;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }
}