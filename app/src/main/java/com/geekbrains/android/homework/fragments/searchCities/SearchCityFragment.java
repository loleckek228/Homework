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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.OnDialogListener;
import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.RecyclerCitiesAdapter;
import com.geekbrains.android.homework.WeatherActivity;
import com.geekbrains.android.homework.WeatherContainer;
import com.geekbrains.android.homework.fragments.SearchBottomSheerDialogFragment;
import com.geekbrains.android.homework.fragments.WeatherFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SearchCityFragment extends Fragment {
    private SearchBottomSheerDialogFragment dialogFragment;
    private RecyclerCitiesAdapter adapter = null;
    private SearchCityViewModel searchCityViewModel;
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

        citiesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.citiesArray)));

        if (searchCitiesList == null) {
            searchCitiesList = new ArrayList<>();
        }

        isExistWeather = checkOrientation();
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
                dialogFragment = new SearchBottomSheerDialogFragment().newInstance();
                dialogFragment.setOnDialogListener(dialogListener);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_fragment");
        }
    }

    private boolean checkCity(String city) {
        return searchCitiesList.contains(city) || citiesList.contains(city);
    }

    private boolean checkOrientation() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void addCity(String city) {
        if (!checkCity(city)) {
            searchCitiesList.add(city);
            searchCityViewModel.setCities(searchCitiesList);

            Snackbar.make(view, R.string.city_founded, Snackbar.LENGTH_SHORT).show();
        }
    }

    private OnDialogListener dialogListener = new OnDialogListener() {
        @Override
        public void onDialogSearch(String city) {
            addCity(city);
        }

        @Override
        public void onDialogBack() {

        }
    };

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