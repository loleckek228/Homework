package com.geekbrains.android.homework;

import android.app.Application;

import androidx.room.Room;

import com.geekbrains.android.homework.dao.CitiesDao;
import com.geekbrains.android.homework.database.CitiesDatabase;
import com.geekbrains.android.homework.database.Migration_1_2;

public class App extends Application {
    private static App instance;

    private CitiesDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(
                getApplicationContext(),
                CitiesDatabase.class,
                "cities_database")
                .allowMainThreadQueries()
                .addMigrations(new Migration_1_2())
                .build();
    }

    public CitiesDao getCitiesDao() {
        return database.getCitiesDao();
    }
}