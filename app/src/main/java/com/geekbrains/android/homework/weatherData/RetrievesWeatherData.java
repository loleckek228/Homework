package com.geekbrains.android.homework.weatherData;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.geekbrains.android.homework.CurrentFragment;
import com.geekbrains.android.homework.fragments.DialogBuilderFragment;
import com.geekbrains.android.homework.rest.OpenWeatherRepo;
import com.geekbrains.android.homework.rest.entities.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private static final String UNITS = "metric";

    private DialogBuilderFragment dialogBuilderFragment;

    public void updateWeatherData(final String city, boolean isListItem) {
        Fragment fragment = CurrentFragment.getInstance().getFragment();

        OpenWeatherRepo.getSingleton().getAPI().loadWeather(city,
                OPEN_WEATHER_API_KEY, UNITS)
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            if (isListItem) {
                                WeatherDataLoader.getInstance().renderWeather(response.body());
                            } else {
                                WeatherDataLoader.getInstance().saveCityWeather(response.body());
                            }
                        } else {
                            if (response.code() == 404) {
                                dialogBuilderFragment = new DialogBuilderFragment(city);
                                dialogBuilderFragment.show(fragment.getActivity().getSupportFragmentManager(), "dialogBuilder");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        dialogBuilderFragment = new DialogBuilderFragment();
                        dialogBuilderFragment.show(fragment.getActivity().getSupportFragmentManager(), "dialogBuilder");
                    }
                });
    }
}