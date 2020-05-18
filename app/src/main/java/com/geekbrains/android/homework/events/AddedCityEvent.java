package com.geekbrains.android.homework.events;

import com.geekbrains.android.homework.model.City;

public class AddedCityEvent {
    public City city;

    public AddedCityEvent(City city) {
        this.city = city;
    }
}
