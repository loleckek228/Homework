package com.geekbrains.android.homework.fragments.searchCities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.App;
import com.geekbrains.android.homework.CitiesSource;
import com.geekbrains.android.homework.CurrentFragment;
import com.geekbrains.android.homework.EventBus;
import com.geekbrains.android.homework.OnDialogListener;
import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.WeatherContainer;
import com.geekbrains.android.homework.adapters.RecyclerCitiesAdapter;
import com.geekbrains.android.homework.dao.CitiesDao;
import com.geekbrains.android.homework.events.AddedCityEvent;
import com.geekbrains.android.homework.fragments.SearchBottomSheerDialogFragment;
import com.geekbrains.android.homework.fragments.WeatherFragment;
import com.geekbrains.android.homework.model.City;
import com.geekbrains.android.homework.weatherData.RetrievesWeatherData;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.otto.Subscribe;

import static java.util.Objects.requireNonNull;

public class SearchCityFragment extends Fragment {
    private SearchBottomSheerDialogFragment dialogFragment;
    private RecyclerCitiesAdapter adapter;
    private CitiesSource citiesSource;
    private View view;

    private boolean isExistWeather;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getBus().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        isExistWeather = checkOrientation();

        initList();

        CurrentFragment.getInstance().setFragment(this);
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
        inflater.inflate(R.menu.toolbar_menu_searchcity, menu);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_listitem, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onContextItemSelected(item);
    }

    private void initList() {
        RecyclerView recyclerView = view.findViewById(R.id.searchCitiesRecyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        CitiesDao citiesDao = App
                .getInstance()
                .getCitiesDao();

        citiesSource = new CitiesSource(citiesDao);

        adapter = new RecyclerCitiesAdapter(citiesSource, this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private boolean checkOrientation() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void addCity(String city) {
        RetrievesWeatherData.getInstance().updateWeatherData(city, false);
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

    @Subscribe
    @SuppressWarnings("unused")
    public void onAddedCityEvent(AddedCityEvent event) {
        City city = event.city;

        for (City cityItem : citiesSource.getCities()) {
            if (city.equals(cityItem)) {
                citiesSource.updateCity(city);

                adapter.notifyItemChanged((int) adapter.getListPosition());
                return;
            }
        }

        citiesSource.addCity(city);
        adapter.notifyDataSetChanged();

        Snackbar.make(view, R.string.city_founded, Snackbar.LENGTH_SHORT).show();
    }

    public void showWeather(String city) {
        if (isExistWeather) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            WeatherFragment detail = (WeatherFragment)
                    requireNonNull(fragmentManager).findFragmentById(R.id.weather);

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
            WeatherContainer.getInstance().setCity(city);
            Navigation.findNavController(view).navigate(R.id.navigation_weather);
        }
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search:
                dialogFragment = new SearchBottomSheerDialogFragment().newInstance();
                dialogFragment.setOnDialogListener(dialogListener);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_fragment");

                break;
            case R.id.menu_clear_history:
                citiesSource.clearCities();
                adapter.notifyDataSetChanged();

                break;
            case R.id.menu_remove_city:
                City cityForRemove = citiesSource
                        .getCities()
                        .get(adapter.getListPosition());

                citiesSource.removeCity(cityForRemove.id);
                adapter.notifyDataSetChanged();

                break;
            case R.id.menu_sort_by_city:
                citiesSource.sortByCity();
                adapter.notifyDataSetChanged();

                break;
        }
    }
}