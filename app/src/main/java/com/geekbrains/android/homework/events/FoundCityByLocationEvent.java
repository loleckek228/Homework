package com.geekbrains.android.homework.events;

public class FoundCityByLocationEvent {
    private String city;

    public FoundCityByLocationEvent(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}