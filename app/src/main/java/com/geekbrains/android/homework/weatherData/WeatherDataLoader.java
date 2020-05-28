package com.geekbrains.android.homework.weatherData;

import androidx.fragment.app.Fragment;

import com.geekbrains.android.homework.CurrentFragment;
import com.geekbrains.android.homework.EventBus;
import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.WeatherContainer;
import com.geekbrains.android.homework.events.AddedCityEvent;
import com.geekbrains.android.homework.fragments.SearchCityFragment;
import com.geekbrains.android.homework.model.City;
import com.geekbrains.android.homework.model.Info;
import com.geekbrains.android.homework.notifications.Notification;
import com.geekbrains.android.homework.rest.entities.WeatherRequestRestModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherDataLoader {

    private static WeatherDataLoader instance;

    private WeatherDataLoader() {
    }

    public static WeatherDataLoader getInstance() {
        if (instance == null) {
            instance = new WeatherDataLoader();
        }

        return instance;
    }

    public void renderWeather(WeatherRequestRestModel model) {

//        saveCityWeather(model);

        String temperature = getCurrentTemp(model.main.temp);
        String date = getUpdateDate(model.dt);
        float floatTemp = model.main.temp;

        WeatherContainer.getInstance().setTemperature(temperature);
        WeatherContainer.getInstance().setDate(date);
        WeatherContainer.getInstance().setFloatTemp(floatTemp);

        String description = model.weather[0].description;
        float windSpeed = model.wind.speed;

        setDetails(description);
        setWindSpeed(windSpeed);
        setWeatherIcon(model.weather[0].id,
                model.sys.sunrise * 1000,
                model.sys.sunset * 1000);

        SearchCityFragment fragment = CurrentFragment.getInstance().getFragment();

        String city = model.name;

        fragment.showWeather(city);
        pushNotificationAboutBadWeather(floatTemp, windSpeed);
    }

    public void saveCityWeather(WeatherRequestRestModel model) {
        City city = new City();

        String cityName = model.name;
        String temperature = getCurrentTemp(model.main.temp);
        String date = getUpdateDate(model.dt);

        city.city = cityName;

        city.info = new Info();
        city.info.date = date;
        city.info.temperature = temperature;

        EventBus.getBus().post(new AddedCityEvent(city));
    }

    private void setDetails(String description) {
        WeatherContainer.getInstance().setDescription(description);
    }

    private String getCurrentTemp(float temp) {
        return String.format(Locale.getDefault(), "%.2f",
                temp) + "\u2103";
    }

    private String getUpdateDate(long dt) {
        return new SimpleDateFormat("dd/MM").format(new Date(dt * 1000));
    }

    private void setWindSpeed(float speed) {
        String windSpeed = String.format(Locale.getDefault(), "%.2f",
                speed);

        WeatherContainer.getInstance().setWindSpeed(windSpeed);
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "\u2600";
            } else {
                icon = "\uf02e";
            }
        } else {
            switch (id) {
                case 2: {
                    icon = "\uf01e";
                    break;
                }
                case 3: {
                    icon = "\uf01c";
                    break;
                }
                case 5: {
                    icon = "\uf019";
                    break;
                }
                case 6: {
                    icon = "\uf01b";
                    break;
                }
                case 7: {
                    icon = "\uf014";
                    break;
                }
                case 8: {
                    icon = "\u2601";
                    break;
                }
            }
        }

        WeatherContainer.getInstance().setIcon(icon);
    }

    private void pushNotificationAboutBadWeather(float temp, float speed) {
        Fragment fragment = CurrentFragment.getInstance().getFragment();

        if (temp < -15 && speed > 10) {
            new Notification(fragment.getContext(), R.drawable.ic_attention , fragment.getString(R.string.bad_weather));
        }
    }
}