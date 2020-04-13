package com.geekbrains.android.homework.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.RecyclerCitiesAdapter;
import com.geekbrains.android.homework.WeatherActivity;
import com.geekbrains.android.homework.WeatherContainer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

public class CitiesFragment extends Fragment {
    static final String WEATHER_DATA_CONTAINER = "weather_data_container";

    private ArrayList<String> citiesList;
    private ArrayList<String> addedCitiesList;
    private RecyclerView citiesRecyclerView;
    private RecyclerView addedCitiesRecyclerView;
    private TextInputEditText inputCityEditText;
    private MaterialButton addCityButton;
    private Bundle savedInstanceState;
    private View view;

    private Pattern checkInputCity = Pattern.compile("^[а-яА-Я]+(?:[\\s-][а-яА-Я]+)*$");

    private boolean isInputCityCorrect;
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
        this.view = view;

        citiesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.citiesArray)));
        addedCitiesList = new ArrayList<>();

        initViews();
        checkInputCityField();
        setOnAddCityButtonClickBehavior();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        isExistWeather = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
            addedCitiesList = savedInstanceState.getStringArrayList("AddedCities");
            isInputCityCorrect = savedInstanceState.getBoolean("CorrectCity", false);
        }

        initAddedCitiesRecyclerView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        outState.putStringArrayList("AddedCities", addedCitiesList);
        outState.putBoolean("CorrectCity", isInputCityCorrect);
        super.onSaveInstanceState(outState);
    }

    private void checkInputCityField() {
        inputCityEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) return;

            TextView textView = (TextView) v;

            validate(textView, checkInputCity, "Введите город!");
        });
    }

    private void initViews() {
        inputCityEditText = view.findViewById(R.id.inputCityEditText);
        addCityButton = view.findViewById(R.id.addCityButton);

        initCitiesRecyclerView();
    }

    private void initCitiesRecyclerView() {
        citiesRecyclerView = view.findViewById(R.id.citiesRecyclerView);

        RecyclerCitiesAdapter citiesAdapter = new RecyclerCitiesAdapter(citiesList, this);

        LinearLayoutManager layoutManger = new LinearLayoutManager(getContext());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(getContext().getDrawable(R.drawable.separator)));

        citiesRecyclerView.addItemDecoration(itemDecoration);
        citiesRecyclerView.setLayoutManager(layoutManger);
        citiesRecyclerView.setAdapter(citiesAdapter);
    }

    private void initAddedCitiesRecyclerView() {
        addedCitiesRecyclerView = view.findViewById(R.id.addedCitiesRecyclerView);

        RecyclerCitiesAdapter addedCitiesAdapter = new RecyclerCitiesAdapter(addedCitiesList, this);

        LinearLayoutManager addedCitiesLayoutManger = new LinearLayoutManager(getContext());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(getContext().getDrawable(R.drawable.separator)));

        addedCitiesRecyclerView.addItemDecoration(itemDecoration);
        addedCitiesRecyclerView.setLayoutManager(addedCitiesLayoutManger);
        addedCitiesRecyclerView.setAdapter(addedCitiesAdapter);
    }

    private void validate(TextView textView, Pattern check, String message) {
        String value = textView.getText().toString();

        if (check.matcher(value).matches()) {
            hideError(textView);
            isInputCityCorrect = true;
        } else {
            showError(textView, message);
            isInputCityCorrect = false;
        }
    }

    private void showError(TextView textView, String message) {
        textView.setError(message);
    }

    private void hideError(TextView textView) {
        textView.setError(null);
    }

    private void setOnAddCityButtonClickBehavior() {
        addCityButton.setOnClickListener((view) -> {
            String city = inputCityEditText.getText().toString();

            if (!checkCity(city) && isInputCityCorrect) {
                addedCitiesList.add(city);
                isInputCityCorrect = false;
                onActivityCreated(savedInstanceState);
                Snackbar.make(view, R.string.city_founded, Snackbar.LENGTH_SHORT).show();
                inputCityEditText.setText(null);
                addCityButton.clearFocus();
                closeKeyboard();
            }
        });
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private boolean checkCity(String city) {
        return addedCitiesList.contains(city) || citiesList.contains(city);
    }

    public void showWeather(ArrayList<String> citiesList, int position) {
        currentPosition = position;

        if (isExistWeather) {
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weather);

            if (detail == null || detail.getIndex() != currentPosition) {

                detail = WeatherFragment.create(getWeatherContainer(citiesList));

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.weather, detail);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack("Some_key");
                fragmentTransaction.commit();
            }
        } else {
            Intent intentContainer = new Intent();
            intentContainer.setClass(Objects.requireNonNull(getActivity()), WeatherActivity.class);

            intentContainer.putExtra(WEATHER_DATA_CONTAINER, getWeatherContainer(citiesList));

            startActivity(intentContainer);
        }
    }

    private WeatherContainer getWeatherContainer(ArrayList<String> citiesList) {
        ArrayList<String> cities = citiesList;

        Random random = new Random();

        String city = cities.get(currentPosition);

        String temperatureYesterday = random.nextInt(10) + "C";
        String temperatureToday = random.nextInt(10) + "C";
        String temperatureTomorrow = random.nextInt(10) + "C";
        String windSpeed = String.valueOf(random.nextInt(5));

        WeatherContainer.getInstance().setCity(city);
        WeatherContainer.getInstance().setTemperatureYesterday(temperatureYesterday);
        WeatherContainer.getInstance().setTemperatureToday(temperatureToday);
        WeatherContainer.getInstance().setTemperatureTomorrow(temperatureTomorrow);
        WeatherContainer.getInstance().setWindSpeed(windSpeed);
        WeatherContainer.getInstance().setPosition(currentPosition);

        return WeatherContainer.getInstance();
    }
}