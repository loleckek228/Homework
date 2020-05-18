package com.geekbrains.android.homework.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.Weather;
import com.geekbrains.android.homework.WeatherContainer;
import com.geekbrains.android.homework.adapters.RecyclerWeatherAdapter;
import com.squareup.picasso.Picasso;

public class WeatherFragment extends Fragment {
    private SharedPreferences activityPrefs;
    private RecyclerView temperatureRecyclerView;
    private TextClock timeTextView;
    private TextView cityTextView;
    private TextView windSpeedTextView;
    private TextView weatherIconTextView;
    private TextView weatherDescriptionTextView;
    private Typeface weatherFont;
    private View view;

    private final String namedPrefsKey = "named_prefs";
    private final String cityKey = "city_key";
    private final String dateKey = "date_key";
    private final String iconKey = "icon_key";
    private final String floatTempKey = "tempFloat_key";
    private final String windSpeedKey = "windSpeed_key";
    private final String temperatureKey = "temperature_key";
    private final String descriptionKey = "description_key";

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

        activityPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_search_city);
        }

        registerForContextMenu(view);
        initViews();
        getInfo();
        initFonts();
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

        float floatTemp;

        String date;
        String temperature;

        if (WeatherContainer.getInstance().getTemperature() == null) {
            floatTemp = activityPrefs.getFloat(floatTempKey, Integer.MIN_VALUE);
            date = activityPrefs.getString(dateKey, getString(R.string.date));
            temperature = activityPrefs.getString(temperatureKey, getString(R.string.temperature));
        } else {
            temperature = WeatherContainer.getInstance().getTemperature();
            floatTemp = WeatherContainer.getInstance().getFloatTemp();
            date = WeatherContainer.getInstance().getDate();
        }

        Weather weather = new Weather(temperature, floatTemp, date);

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


        if (city != null) {
            cityTextView.setText(city);
            windSpeedTextView.setText(windSpeed);
            weatherDescriptionTextView.setText(weatherDescription);
            weatherIconTextView.setText(weatherIcon);

            saveToPreference(activityPrefs);
        } else {
            readFromPreference(activityPrefs);
        }
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

    private void saveToPreference(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();

        float tempFloat = WeatherContainer.getInstance().getFloatTemp();

        String city = WeatherContainer.getInstance().getCity();
        String date = WeatherContainer.getInstance().getDate();
        String weatherIcon = WeatherContainer.getInstance().getIcon();
        String windSpeed = WeatherContainer.getInstance().getWindSpeed();
        String temperature = WeatherContainer.getInstance().getTemperature();
        String weatherDescription = WeatherContainer.getInstance().getDescription();


        editor.putFloat(floatTempKey, tempFloat);
        editor.putString(cityKey, city);
        editor.putString(dateKey, date);
        editor.putString(iconKey, weatherIcon);
        editor.putString(windSpeedKey, windSpeed);
        editor.putString(temperatureKey, temperature);
        editor.putString(descriptionKey, weatherDescription);

        editor.apply();
    }

    private void readFromPreference(SharedPreferences preferences) {
        String city = preferences.getString(cityKey, getString(R.string.city));
        String windSpeed = preferences.getString(windSpeedKey, getString(R.string.windSpeed));
        String weatherIcon = preferences.getString(iconKey, "");
        String weatherDescription =
                preferences.getString(descriptionKey, getString(R.string.weather_description));

        cityTextView.setText(city);
        windSpeedTextView.setText(windSpeed);
        weatherDescriptionTextView.setText(weatherDescription);
        weatherIconTextView.setText(weatherIcon);
    }

}