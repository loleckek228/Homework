package com.geekbrains.android.homework.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geekbrains.android.homework.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_weather, R.id.navigation_search_city,
                R.id.navigation_developer, R.id.navigation_settings)
                .setDrawerLayout(drawer)
                .build();

        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI
                .setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.navigation_search_city);
        }
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_developer) {
            Intent intent = new Intent(this, DeveloperActivity.class);
            startActivity(intent);
        }
    }
}