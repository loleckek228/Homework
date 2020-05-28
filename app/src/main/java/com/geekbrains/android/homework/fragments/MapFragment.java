package com.geekbrains.android.homework.fragments;

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

import com.geekbrains.android.homework.EventBus;
import com.geekbrains.android.homework.LocationFinder;
import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.events.FoundLocationEvent;
import com.geekbrains.android.homework.weatherData.RetrievesWeatherData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        return view;
    }

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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LocationFinder.getInstance().findLocation(getActivity());

        this.googleMap.setOnMapLongClickListener(markLocation -> {
            String city = getAddress(markLocation);

            RetrievesWeatherData.getInstance().updateWeatherData(city, true);
        });
    }

    private String getAddress(final LatLng latLng) {
       return LocationFinder.getInstance().getCityByLocation(latLng.latitude,
                latLng.longitude, getActivity());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void OnFoundLocation(FoundLocationEvent event) {
        double latitude = event.getLocation().getLatitude();
        double longitude = event.getLocation().getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        googleMap.clear();

        String city = LocationFinder.getInstance()
                .getCityByLocation(latitude, longitude, getActivity());

        this.googleMap.addMarker(new MarkerOptions().position(latLng).title(city));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_location:
                LocationFinder.getInstance().findLocation(getActivity());

                break;
        }
    }
}