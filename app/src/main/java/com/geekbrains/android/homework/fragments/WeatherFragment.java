package com.geekbrains.android.homework.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.RecyclerWeatherAdapter;
import com.geekbrains.android.homework.Weather;
import com.geekbrains.android.homework.WeatherContainer;

import java.util.Objects;

public class WeatherFragment extends Fragment {
    private RecyclerView temperatureRecyclerView;
    private TextClock timeTextView;
    private TextView windSpeedTextView;
    private TextView cityTextView;
    private String temperatureYesterday;
    private String temperatureToday;
    private String temperatureTomorrow;
    private View view;

    static WeatherFragment create(WeatherContainer container) {
        WeatherFragment fragment = new WeatherFragment();

        Bundle args = new Bundle();
        args.putSerializable(CitiesFragment.WEATHER_DATA_CONTAINER, container);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initViews();
        getInfo();
        initRecyclerView();
        setColourOfTextView();
    }

    private void initViews() {
        windSpeedTextView = view.findViewById(R.id.windSpeedTextView);
        cityTextView = view.findViewById(R.id.cityTextView);
        timeTextView = view.findViewById(R.id.timeTextClock);
    }

    private void setColourOfTextView() {
        windSpeedTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        timeTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        cityTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
    }

    private void initRecyclerView() {
        temperatureRecyclerView = view.findViewById(R.id.temperatureRecyclerVIew);

        Weather[] weathers = new Weather[]{new Weather(temperatureYesterday, -1),
                new Weather(temperatureToday, 0),
                new Weather(temperatureTomorrow, 1)};

        RecyclerWeatherAdapter adapter = new RecyclerWeatherAdapter(weathers);

        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);

        temperatureRecyclerView.setLayoutManager(layoutManager);
        temperatureRecyclerView.setAdapter(adapter);
    }

    private void getInfo() {
        WeatherContainer container = (WeatherContainer) (Objects.requireNonNull(getArguments())
                .getSerializable(CitiesFragment.WEATHER_DATA_CONTAINER));

        String city = container.getCity();
        String windSpeed = container.getWindSpeed();

        temperatureYesterday = container.getTemperatureYesterday();
        temperatureToday = container.getTemperatureToday();
        temperatureTomorrow = container.getTemperatureTomorrow();

        cityTextView.setText(city);

        windSpeedTextView.setText(windSpeed);
    }

    int getIndex() {
        WeatherContainer container = (WeatherContainer) (Objects.requireNonNull(getArguments())
                .getSerializable(CitiesFragment.WEATHER_DATA_CONTAINER));

        try {
            return container.getPosition();
        } catch (Exception e) {
            return 0;
        }
    }
}