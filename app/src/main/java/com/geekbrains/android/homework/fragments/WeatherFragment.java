package com.geekbrains.android.homework.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.RecyclerWeatherAdapter;
import com.geekbrains.android.homework.Weather;
import com.geekbrains.android.homework.WeatherContainer;

public class WeatherFragment extends Fragment {
    private RecyclerView temperatureRecyclerView;
    private TextClock timeTextView;
    private TextView windSpeedTextView;
    private TextView cityTextView;
    private TextView weatherIconTextView;
    private TextView weatherDescriptionTextView;
    private Typeface weatherFont;
    private View view;

    public static WeatherFragment create() {
        WeatherFragment fragment = new WeatherFragment();

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
        initFonts();
        getInfo();
        initRecyclerView();
        setColourOfTextView();
    }

    private void initViews() {
        windSpeedTextView = view.findViewById(R.id.windSpeedTextView);
        cityTextView = view.findViewById(R.id.cityTextView);
        timeTextView = view.findViewById(R.id.timeTextClock);
        weatherDescriptionTextView = view.findViewById(R.id.weatherDescriptionTextView);
        weatherIconTextView = view.findViewById(R.id.weatherIconTextView);
    }

    private void initFonts() {
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        weatherIconTextView.setTypeface(weatherFont);
    }

    private void setColourOfTextView() {
        timeTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        cityTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        weatherDescriptionTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));
        windSpeedTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));
    }

    private void initRecyclerView() {
        temperatureRecyclerView = view.findViewById(R.id.temperatureRecyclerVIew);

        String temperature = WeatherContainer.getInstance().getTemperatureToday();
        String date = WeatherContainer.getInstance().getDate();

        Weather weather = new Weather(temperature, date);

        RecyclerWeatherAdapter adapter = new RecyclerWeatherAdapter(new Weather[]{weather});

        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);

        temperatureRecyclerView.setLayoutManager(layoutManager);
        temperatureRecyclerView.setAdapter(adapter);
    }

    private void getInfo() {
        String city = WeatherContainer.getInstance().getCity();
        String windSpeed = WeatherContainer.getInstance().getWindSpeed();
        String weatherDescription = WeatherContainer.getInstance().getDescription();
        String weatherIcon = WeatherContainer.getInstance().getIcon();

        cityTextView.setText(city);
        windSpeedTextView.setText(windSpeed);
        weatherDescriptionTextView.setText(weatherDescription);
        weatherIconTextView.setText(weatherIcon);
    }
}