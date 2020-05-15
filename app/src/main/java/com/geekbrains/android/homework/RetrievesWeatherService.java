package com.geekbrains.android.homework;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class RetrievesWeatherService extends IntentService {
    public RetrievesWeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String city = intent.getStringExtra("City");

        RetrievesWeatherData.getInstance().updateWeatherData(city);

        stopSelf();
    }
}