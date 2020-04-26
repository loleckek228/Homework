package com.geekbrains.android.homework;

import android.content.Intent;
import android.os.Handler;

import androidx.fragment.app.Fragment;

import com.geekbrains.android.homework.fragments.CitiesFragment;
import com.geekbrains.android.homework.fragments.searchCities.SearchCityFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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

    private static final String OPEN_WEATHER_API_KEY = "14bd5b8ec394bc4aabe8a6968999edab";
    private static final String OPEN_WEATHER_API_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String KEY = "x-api-key";
    private static final String TAG = "WEATHER";
    private static final Handler handler = new Handler();

    private Fragment fragment;
    private String city;

    public void updateWeatherData(final String city, Fragment fragment) {
        this.fragment = fragment;
        this.city = city;
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = getJSONData(city);
                if (jsonObject == null) {
                    handler.post(() -> {
                        Intent intent = new Intent(Objects.requireNonNull(fragment.getContext()), ErrorActivity.class);
                        fragment.startActivity(intent);

                    });
                } else {
                    handler.post(() -> renderWeather(jsonObject));
                }
            }
        }.start();
    }

    private JSONObject getJSONData(String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_API_URL, city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);
            connection.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());
            if (jsonObject.getInt("cod") != 200) {
                return null;
            } else {
                return jsonObject;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private void renderWeather(JSONObject jsonObject) {
        try {
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
                CitiesFragment fragment = (CitiesFragment) this.fragment;
                fragment.showWeather(city);
            }
            else if (fragment instanceof SearchCityFragment) {
                SearchCityFragment fragment = (SearchCityFragment) this.fragment;
                fragment.showWeather(city);
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