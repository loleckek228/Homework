package com.geekbrains.android.homework.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

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

        registerForContextMenu(view);
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

        String temperature = WeatherContainer.getInstance().getTemperature();
        float temp = WeatherContainer.getInstance().getTemp();
        String date = WeatherContainer.getInstance().getDate();

        Weather weather = new Weather(temperature, temp, date);

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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {

        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        loadImageWithPicasso();
        return super.onContextItemSelected(item);
    }

    private void loadImageWithPicasso() {
        ImageView image = view.findViewById(R.id.image);
        Picasso.get()
                .load("https://images.unsplash.com/photo-1469829638725-69bf13ad6801?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60")
                .into(image);
    }

}