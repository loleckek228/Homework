package com.geekbrains.android.homework;

import androidx.fragment.app.Fragment;

import com.geekbrains.android.homework.fragments.CitiesFragment;
import com.geekbrains.android.homework.fragments.searchCities.SearchCityFragment;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void renderWeather(JSONObject jsonObject, String city) {
        try {
            Fragment fragment = CurrentFragment.getInstance().getFragment();

            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject windSpeed = jsonObject.getJSONObject("wind");

            setDetails(details);
            setCurrentTemp(main);
            setUpdatedText(jsonObject);
            setWindSpeed(windSpeed);
            setWeatherIcon(details.getInt("id"),
                    jsonObject.getJSONObject("sys").getLong("sunrise") * 1000,
                    jsonObject.getJSONObject("sys").getLong("sunset") * 1000);

            if (fragment instanceof CitiesFragment) {
                CitiesFragment citiesFragment = (CitiesFragment) fragment;
                citiesFragment.showWeather(city);
            }
            else if (fragment instanceof SearchCityFragment) {
                SearchCityFragment searchCityFragment = (SearchCityFragment) fragment;
                searchCityFragment.showWeather(city);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void setDetails(JSONObject details) throws JSONException {
        String detailsText = details.getString("description").toUpperCase();

        WeatherContainer.getInstance().setDescription(detailsText);
    }

    private void setCurrentTemp(JSONObject main) throws JSONException {
        String currentTextText = String.format(Locale.getDefault(), "%.2f",
                main.getDouble("temp")) + "\u2103";

        WeatherContainer.getInstance().setTemperatureToday(currentTextText);
    }

    private void setUpdatedText(JSONObject jsonObject) throws JSONException {
        String updateOn = new SimpleDateFormat("dd/MM").format(new Date(jsonObject.getLong("dt") * 1000));
        WeatherContainer.getInstance().setDate(updateOn);
    }

    private void setWindSpeed(JSONObject jsonObject) throws JSONException {
        String windSpeed = String.format(Locale.getDefault(), "%d",
                jsonObject.getInt("speed"));

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