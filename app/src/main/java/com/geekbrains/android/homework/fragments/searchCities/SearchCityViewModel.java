package com.geekbrains.android.homework.fragments.searchCities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SearchCityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> cities;

    public void setCities(ArrayList<String> cities) {
        this.cities.setValue(cities);
    }

    public LiveData<ArrayList<String>> getCities() {
        if (cities == null) {
            cities = new MutableLiveData<>();
        }

        return cities;
    }
}