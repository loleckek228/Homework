package com.geekbrains.android.homework.fragments.searchCities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.RecyclerCitiesAdapter;
import com.geekbrains.android.homework.WeatherActivity;
import com.geekbrains.android.homework.WeatherContainer;
import com.geekbrains.android.homework.fragments.WeatherFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class SearchCityFragment extends Fragment {
    private Pattern checkInputCity = Pattern.compile("^[а-яА-Я]+(?:[\\s-][а-яА-Я]+)*$");
    private RecyclerCitiesAdapter adapter = null;
    private SearchCityViewModel searchCityViewModel;
    private TextInputEditText inputCityEditText;
    private ArrayList<String> citiesList;
    private ArrayList<String> searchCitiesList;
    private RecyclerView recyclerView;
    private View view;

    private boolean isExistWeather;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchCityViewModel = new ViewModelProvider(getActivity()).get(SearchCityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_city, container, false);
        searchCityViewModel.getCities().observe(getViewLifecycleOwner(), cities -> {
            initList(cities);
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        inputCityEditText = Objects.requireNonNull(getActivity()).findViewById(R.id.inputCityEditText);

        citiesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.citiesArray)));

        if (searchCitiesList == null) {
            searchCitiesList = new ArrayList<>();
        }

        isExistWeather = checkOrientation();

        checkInputCityField();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExistWeather = checkOrientation();

        if (isExistWeather) {
            showWeather(WeatherContainer.getInstance().getCity());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_search, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    private void initList(ArrayList<String> cities) {
        searchCitiesList = cities;
        adapter = new RecyclerCitiesAdapter(searchCitiesList, this, isExistWeather);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView = view.findViewById(R.id.searchCitiesRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_search) {
            if (inputCityEditText.getVisibility() == View.VISIBLE) {
                inputCityEditText.setVisibility(View.GONE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_search_city);
            } else {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
                inputCityEditText.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean checkCity(String city) {
        return searchCitiesList.contains(city) || citiesList.contains(city);
    }

    private boolean checkOrientation() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void checkInputCityField() {
        inputCityEditText.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                validate(textView, checkInputCity, "Введите город!");
                return true;
            }
            return false;
        });
    }

    private void validate(TextView tv, Pattern check, String message) {
        String city = tv.getText().toString();
        if (check.matcher(city).matches()) {
            hideError(tv);
            addCity(tv.getRootView(), city);
        } else {
            showError(tv, message);
        }
    }

    private void showError(TextView view, String message) {
        view.setError(message);
    }

    private void hideError(TextView view) {
        view.setError(null);
    }

    private void addCity(View view, String city) {
        if (!checkCity(city)) {
            searchCitiesList.add(city);
            searchCityViewModel.setCities(searchCitiesList);

            Snackbar.make(view, R.string.city_founded, Snackbar.LENGTH_SHORT).show();

            inputCityEditText.setText(null);
            inputCityEditText.setVisibility(View.GONE);
        }
    }

    public void showWeather(String city) {
        if (isExistWeather) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(fragmentManager).findFragmentById(R.id.weather);

            if (detail == null || WeatherContainer.getInstance().getCity() != city) {

                detail = WeatherFragment.create();

                WeatherContainer.getInstance().setCity(city);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.weather, detail);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack("Some_key");
                fragmentTransaction.commit();
            }
        } else {
            Intent intentContainer = new Intent();
            intentContainer.setClass(Objects.requireNonNull(getActivity()), WeatherActivity.class);
            WeatherContainer.getInstance().setCity(city);
            startActivity(intentContainer);
        }
    }
}