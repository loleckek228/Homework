package com.geekbrains.android.homework.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.geekbrains.android.homework.model.City;

import java.util.List;

@Dao
public interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCity(City city);

    @Update
    void updateCity(City city);

    @Query("DELETE FROM city WHERE id = :id")
    void deleteCityById(long id);

    @Query("SELECT * FROM city ORDER BY city")
    List<City> sortByCity();

    @Query("DELETE FROM city")
    void clearCities();

    @Query("SELECT * FROM city")
    List<City> getAllCities();

    @Query("SELECT COUNT() FROM city")
    long getCountCities();
}