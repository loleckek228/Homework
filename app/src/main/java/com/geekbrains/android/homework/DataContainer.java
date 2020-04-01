package com.geekbrains.android.homework;

import java.io.Serializable;

public class DataContainer implements Serializable {
    private static DataContainer instance;

    private int temperature;

    private DataContainer(int temperature) {
        this.temperature = temperature;
    }

    public static DataContainer getInstance(int temperature) {
        if (instance == null) instance = new DataContainer(temperature);

        return instance;
    }

    public String getTemperatureText() {
        return String.valueOf(temperature);
    }
}
