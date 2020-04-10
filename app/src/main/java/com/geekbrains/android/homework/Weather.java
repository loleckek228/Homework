package com.geekbrains.android.homework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Weather {
    private String temperature;

    private int numberOfDay;

    public Weather(String temperature, int numberOfDay) {
        this.temperature = temperature;
        this.numberOfDay = numberOfDay;
    }

    public String getDate() {
        String date = new SimpleDateFormat("dd.MM", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(Objects.requireNonNull(dateFormat.parse(date)));
            calendar.add(Calendar.DATE, numberOfDay);

            date = dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public String getTemperature() {
        return temperature;
    }
}