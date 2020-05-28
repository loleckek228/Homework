package com.geekbrains.android.homework.model;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {City.CITY})})
public class City {

    public final static String ID = "id";
    public final static String CITY = "city";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = CITY)
    public String city;

    @Embedded
    public Info info;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null || obj.getClass() != this.getClass()) return false;

        City city = (City) obj;

        return city.city.equals(this.city);
    }
}
