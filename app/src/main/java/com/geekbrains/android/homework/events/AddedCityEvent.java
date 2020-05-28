package com.geekbrains.android.homework.events;

import com.geekbrains.android.homework.model.City;

public class AddedCityEvent {
    private City city;

    public AddedCityEvent(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }
}