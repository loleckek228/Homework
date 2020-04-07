package com.geekbrains.android.homework.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.WeatherContainer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class WeatherFragment extends Fragment {

    private TextView temperatureTextView;
    private TextView wildSpeedTextView;
    private TextView dataTextView;
    private TextView timeTextView;
    private TextView cityTextView;

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
        initViews(view);
        setColourOfTextView(view);
        getInfo();
        setDate();
    }

    private void initViews(View view) {
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        wildSpeedTextView = view.findViewById(R.id.wildSpeedTextView);
        cityTextView = view.findViewById(R.id.cityTextView);
        dataTextView = view.findViewById(R.id.dataTextView);
        timeTextView = view.findViewById(R.id.timeTextClock);
    }

    private void setColourOfTextView(View view) {
        temperatureTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        wildSpeedTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        dataTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        timeTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        cityTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
    }

    private void getInfo() {
        WeatherContainer container = (WeatherContainer) (Objects.requireNonNull(getArguments())
        .getSerializable(CitiesFragment.WEATHER_DATA_CONTAINER));

        String city = container.getCity();
        String temperature = container.getTemperature();
        String wildSpeed = container.getWildSpeed();

        cityTextView.setText(city);

        if (!temperature.equals("")) {
            temperatureTextView.setText(temperature);
        }

        if (!wildSpeed.equals("")) {
            wildSpeedTextView.setText(wildSpeed);
        }
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

    private void setDate() {
        String date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

        dataTextView.setText(date);
    }
}