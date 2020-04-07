package com.geekbrains.android.homework.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.WeatherActivity;
import com.geekbrains.android.homework.WeatherContainer;

import java.util.Objects;
import java.util.Random;

public class CitiesFragment extends Fragment {
    static final String WEATHER_DATA_CONTAINER = "weather_data_container";

    private ListView citiesListView;
    private TextView emptyTextView;
    private CheckBox temperatureCheckBox;
    private CheckBox wildSpeedCheckBox;

    private boolean isExistWeather;
    private int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_citieslist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }

        if (isExistWeather) {
            citiesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeather();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        citiesListView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
        temperatureCheckBox = view.findViewById(R.id.temperatureCheckBox);
        wildSpeedCheckBox = view.findViewById(R.id.wildSpeedCheckBox);
    }

    private void initList() {
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities,
                        android.R.layout.simple_list_item_activated_1);
        citiesListView.setAdapter(adapter);

        citiesListView.setEmptyView(emptyTextView);

        citiesListView.setOnItemClickListener(((parent, view, position, id) -> {
            currentPosition = position;
            showWeather();
        }));
    }

    private void showWeather() {
        if (isExistWeather) {
            citiesListView.setItemChecked(currentPosition, true);

            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weather);

            if (detail == null || detail.getIndex() != currentPosition) {

                detail = WeatherFragment.create(getWeatherContainer());

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.weather, detail);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack("Some_key");
                fragmentTransaction.commit();
            }
        }
        else {
            Intent intentContainer = new Intent();
            intentContainer.setClass(Objects.requireNonNull(getActivity()), WeatherActivity.class);

            intentContainer.putExtra(WEATHER_DATA_CONTAINER, getWeatherContainer());

            startActivity(intentContainer);
        }
    }

    private WeatherContainer getWeatherContainer() {
        String[] cities = getResources().getStringArray(R.array.cities);

        String city = cities[currentPosition];
        String temperature = "";
        String wildSpeed = "";

        Boolean isTemperature = temperatureCheckBox.isChecked();
        Boolean isWildSpeed = wildSpeedCheckBox.isChecked();

        Random random = new Random();

        if (isTemperature) {
            temperature = random.nextInt(10) + "C";
        }

        if (isWildSpeed) {
            wildSpeed = String.valueOf(random.nextInt(5));
        }

        WeatherContainer.getInstance().setCity(city);
        WeatherContainer.getInstance().setTemperature(temperature);
        WeatherContainer.getInstance().setWildSpeed(wildSpeed);
        WeatherContainer.getInstance().setPosition(currentPosition);

        return WeatherContainer.getInstance();
    }
}