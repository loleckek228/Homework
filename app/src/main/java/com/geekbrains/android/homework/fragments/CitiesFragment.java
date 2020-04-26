package com.geekbrains.android.homework.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.RecyclerCitiesAdapter;
import com.geekbrains.android.homework.WeatherActivity;
import com.geekbrains.android.homework.WeatherContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CitiesFragment extends Fragment {
    private ArrayList<String> citiesList;
    private View view;

    private boolean isExistWeather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_citieslist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        if (citiesList == null) {
            citiesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.citiesArray)));
        }

        isExistWeather = checkOrientation();

        initCitiesRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExistWeather = checkOrientation();

        if (isExistWeather) {
            showWeather(WeatherContainer.getInstance().getCity());
        }
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

    private boolean checkOrientation() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    public void showWeather(String city) {

        if (isExistWeather) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(fragmentManager).findFragmentById(R.id.weather);

            if (detail == null || WeatherContainer.getInstance().getCity() != city) {

                WeatherContainer.getInstance().setCity(city);
                detail = WeatherFragment.create();

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