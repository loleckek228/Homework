package com.geekbrains.android.homework;

import androidx.fragment.app.Fragment;

import com.geekbrains.android.homework.fragments.CitiesFragment;
import com.geekbrains.android.homework.fragments.searchCities.SearchCityFragment;
import com.geekbrains.android.homework.rest.entities.WeatherRequestRestModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherDataLoader {

    private static WeatherDataLoader instance;

    private WeatherDataLoader() {
    }

    public static WeatherDataLoader getInstance() {
        if (instance == null) {
            instance = new WeatherDataLoader();
        }

        return instance;
    }

    public void renderWeather(WeatherRequestRestModel model, String city) {
        Fragment fragment = CurrentFragment.getInstance().getFragment();

        setDetails(model.weather[0].description);
        setCurrentTemp(model.main.temp);
        setUpdatedText(model.dt);
        setWindSpeed(model.wind.speed);
        setWeatherIcon(model.weather[0].id,
                model.sys.sunrise * 1000,
                model.sys.sunset * 1000);

        if (fragment instanceof CitiesFragment) {
            CitiesFragment citiesFragment = (CitiesFragment) fragment;
            citiesFragment.showWeather(city);
        } else if (fragment instanceof SearchCityFragment) {
            SearchCityFragment searchCityFragment = (SearchCityFragment) fragment;
            searchCityFragment.showWeather(city);
        }
    }

    private void setDetails(String description) {
        String detailsText = description.toUpperCase();

        WeatherContainer.getInstance().setDescription(detailsText);
    }

    private void setCurrentTemp(float temp) {
        String currentTextText = String.format(Locale.getDefault(), "%.2f",
                temp) + "\u2103";

        WeatherContainer.getInstance().setTemperature(currentTextText);
        WeatherContainer.getInstance().setTemp(temp);
    }

    private void setUpdatedText(long dt) {
        String updateOn = new SimpleDateFormat("dd/MM").format(new Date(dt * 1000));

        WeatherContainer.getInstance().setDate(updateOn);
    }

    private void setWindSpeed(float speed) {
        String windSpeed = String.format(Locale.getDefault(), "%.2f",
                speed);

        WeatherContainer.getInstance().setWindSpeed(windSpeed);
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "\u2600";
            } else {
                icon = "\uf02e";
            }
        } else {
            switch (id) {
                case 2: {
                    icon = "\uf01e";
                    break;
                }
                case 3: {
                    icon = "\uf01c";
                    break;
                }
                case 5: {
                    icon = "\uf019";
                    break;
                }
                case 6: {
                    icon = "\uf01b";
                    break;
                }
                case 7: {
                    icon = "\uf014";
                    break;
                }
                case 8: {
                    icon = "\u2601";
                    break;
                }
            }
        }

        WeatherContainer.getInstance().setIcon(icon);
    }
}