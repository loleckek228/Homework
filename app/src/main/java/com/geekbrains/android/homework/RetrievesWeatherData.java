package com.geekbrains.android.homework;

import androidx.fragment.app.Fragment;

import com.geekbrains.android.homework.fragments.DialogBuilderFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrievesWeatherData {

    private static RetrievesWeatherData instance;

    private RetrievesWeatherData() {
    }

    public static RetrievesWeatherData getInstance() {
        if (instance == null) {
            instance = new RetrievesWeatherData();
        }

        return instance;
    }

    private static final String OPEN_WEATHER_API_KEY = "14bd5b8ec394bc4aabe8a6968999edab";
    private static final String OPEN_WEATHER_API_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String KEY = "x-api-key";

    private DialogBuilderFragment dialogBuilderFragment;


    public void updateWeatherData(final String city) {
        Fragment fragment = CurrentFragment.getInstance().getFragment();

        final JSONObject jsonObject = getJSONData(city);
        if (jsonObject == null) {
            dialogBuilderFragment = new DialogBuilderFragment(city);
            dialogBuilderFragment.show(fragment.getActivity().getSupportFragmentManager(), "dialogBuilder");
        } else {
            WeatherDataLoader.getInstance().renderWeather(jsonObject, city);
        }
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
}