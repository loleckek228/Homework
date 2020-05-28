package com.geekbrains.android.homework.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geekbrains.android.homework.dao.CitiesDao;
import com.geekbrains.android.homework.model.City;

@Database(entities = {City.class}, version = 1)
public abstract class CitiesDatabase extends RoomDatabase {
    public abstract CitiesDao getCitiesDao();
}
