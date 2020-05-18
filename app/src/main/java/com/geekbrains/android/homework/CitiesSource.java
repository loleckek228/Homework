package com.geekbrains.android.homework;

import com.geekbrains.android.homework.dao.CitiesDao;
import com.geekbrains.android.homework.model.City;

import java.util.List;

public class CitiesSource {
    private final CitiesDao citiesDao;

    private List<City> cities;

    public CitiesSource(CitiesDao citiesDao) {
        this.citiesDao = citiesDao;
    }

    public List<City> getCities() {
        if (cities == null) {
            loadCities();
        }

        return cities;
    }

    public void loadCities() {
        cities = citiesDao.getAllCities();
    }

    public long getCountCities() {
        return citiesDao.getCountCities();
    }

    public void addCity(City city) {
        citiesDao.insertCity(city);

        loadCities();
    }

    public void updateCity(City city) {
        citiesDao.updateCity(city);

        loadCities();
    }

    public void removeCity(long id) {
        citiesDao.deleteCityById(id);

        loadCities();
    }

    public void sortByCity() {
        cities = citiesDao.sortByCity();
    }

    public void clearCities() {
        citiesDao.clearCities();

        loadCities();
    }
}