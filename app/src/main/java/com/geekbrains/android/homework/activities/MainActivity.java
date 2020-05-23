package com.geekbrains.android.homework.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geekbrains.android.homework.EventBus;
import com.geekbrains.android.homework.LocationFinder;
import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.events.FoundCityByLocationEvent;
import com.geekbrains.android.homework.notifications.BatteryStatusReceiver;
import com.geekbrains.android.homework.notifications.NetworkStatusReceiver;
import com.geekbrains.android.homework.weatherData.RetrievesWeatherData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private BroadcastReceiver networkStatusReceiver = new NetworkStatusReceiver();
    private BroadcastReceiver batteryStatusReceiver = new BatteryStatusReceiver();
    private DrawerLayout drawer;
    private FloatingActionButton floatingActionButton;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search_city, R.id.navigation_weather,
                R.id.navigation_maps, R.id.navigation_developer,
                R.id.navigation_settings, R.id.navigation_auth_google)
                .setDrawerLayout(drawer)
                .build();

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI
                .setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        registerReceivers();
        setOnFloatingButtonClick();
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getBus().unregister(this);
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        floatingActionButton = findViewById(R.id.floatingButton);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void registerReceivers() {
        registerReceiver(networkStatusReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
    }

    private void setOnFloatingButtonClick() {
        floatingActionButton.setOnClickListener(view -> {
            LocationFinder.getInstance().findCityByLocation(this);
        });
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void OnFoundLocationEvent(FoundCityByLocationEvent event) {
        String city = event.getCity();

        RetrievesWeatherData.getInstance().updateWeatherData(city, true);
    }
}