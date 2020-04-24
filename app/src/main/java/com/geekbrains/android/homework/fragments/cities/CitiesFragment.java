package com.geekbrains.android.homework.fragments.cities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.RecyclerCitiesAdapter;
import com.geekbrains.android.homework.WeatherActivity;
import com.geekbrains.android.homework.WeatherContainer;
import com.geekbrains.android.homework.fragments.WeatherFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class CitiesFragment extends Fragment {
    public static final String WEATHER_DATA_CONTAINER = "weather_data_container";

    private СitiesViewModel citiesViewModel;
    private ArrayList<String> citiesList;
    private ArrayList<String> addedCitiesList;
    private TextInputEditText inputCityEditText;
    private MaterialButton addCityButton;
    private View view;

    private Pattern checkInputCity = Pattern.compile("^[а-яА-Я]+(?:[\\s-][а-яА-Я]+)*$");

    private boolean isInputCityCorrect;
    private boolean isExistWeather;
    private int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        citiesViewModel =
                new ViewModelProvider(getActivity()).get(СitiesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_citieslist, container, false);

        citiesViewModel.getCities().observe(getViewLifecycleOwner(), cities -> initAddedCitiesRecyclerView(cities));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        if (citiesList == null) {
            citiesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.citiesArray)));
        }

        if (addedCitiesList == null) {
            addedCitiesList = new ArrayList<>();
        }

        isExistWeather = checkOrientation();

        initViews();
        checkInputCityField();
        setOnAddCityButtonClickBehavior();
        initCitiesRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExistWeather = checkOrientation();

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
            isInputCityCorrect = savedInstanceState.getBoolean("CorrectCity", false);

        }

        if (isExistWeather) {
            showWeather(currentPosition);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
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
    }

    private void initCitiesRecyclerView() {
        RecyclerView citiesRecyclerView = view.findViewById(R.id.citiesRecyclerView);

        RecyclerCitiesAdapter citiesAdapter = new RecyclerCitiesAdapter(citiesList, this, isExistWeather);

        LinearLayoutManager layoutManger = new LinearLayoutManager(getContext());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(getContext().getDrawable(R.drawable.separator)));

        citiesRecyclerView.addItemDecoration(itemDecoration);
        citiesRecyclerView.setLayoutManager(layoutManger);
        citiesRecyclerView.setAdapter(citiesAdapter);
    }

    private void initAddedCitiesRecyclerView(ArrayList<String> cities) {
        RecyclerView addedCitiesRecyclerView = view.findViewById(R.id.addedCitiesRecyclerView);

        addedCitiesList = cities;

        RecyclerCitiesAdapter addedCitiesAdapter = new RecyclerCitiesAdapter(addedCitiesList, this, false);

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
            inputCityEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            String city = inputCityEditText.getText().toString();

            if (!checkCity(city) && isInputCityCorrect) {
                addedCitiesList.add(city);
                isInputCityCorrect = false;
                citiesViewModel.setCities(addedCitiesList);
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

    private boolean checkOrientation() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    public void showWeather(int position) {
        currentPosition = position;

        if (isExistWeather) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(fragmentManager).findFragmentById(R.id.weather);

            if (detail == null || detail.getIndex() != currentPosition) {

                detail = WeatherFragment.create(WeatherContainer.getInstance());

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.weather, detail);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack("Some_key");
                fragmentTransaction.commit();
            }
        } else {
            Intent intentContainer = new Intent();
            intentContainer.setClass(Objects.requireNonNull(getActivity()), WeatherActivity.class);

            intentContainer.putExtra(WEATHER_DATA_CONTAINER, WeatherContainer.getInstance());

            startActivity(intentContainer);
        }
    }
}