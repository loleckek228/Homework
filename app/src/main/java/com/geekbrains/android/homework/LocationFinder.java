package com.geekbrains.android.homework;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import com.geekbrains.android.homework.events.FoundCityByLocationEvent;
import com.geekbrains.android.homework.events.FoundLocationEvent;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class LocationFinder {
    private static LocationFinder instance;

    private LocationFinder() {
    }

    public static LocationFinder getInstance() {
        if (instance == null) {
            instance = new LocationFinder();
        }

        return instance;
    }

    private Activity activity;

    private final static String MSG_NO_DATA = "No data";

    private static final int PERMISSION_REQUEST_CODE = 10;

    private LocationManager locationManager;

    public void findCityByLocation(Activity activity) {
        this.activity = activity;
        getLocationOnListener(cityListener);
    }

    public void findLocation(Activity activity){
        this.activity = activity;
        getLocationOnListener(locationListener);
    }

    private static boolean isRequestPermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    private void getLocationOnListener(LocationListener listener) {
        if (isRequestPermission(activity)) {
            locationManager
                    = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);

            List<String> providers = locationManager.getAllProviders();

            Looper myLooper = Looper.myLooper();

            for (String provider : providers) {
                locationManager.requestSingleUpdate(provider, listener, myLooper);
            }

            final Handler myHandler = new Handler(myLooper);
            myHandler.postDelayed(() -> locationManager.removeUpdates(listener), 1000);
        } else {
            requestLocationPermissions(activity);
        }
    }

    private static void requestLocationPermissions(Activity activity) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    private final LocationListener cityListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            String city = getCityByLocation(location.getLatitude(),
                    location.getLongitude(), activity);

            EventBus.getBus().post(new FoundCityByLocationEvent(city));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            EventBus.getBus().post(new FoundLocationEvent(location));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public String getCityByLocation(double latitude, double longitude, Activity activity) {
        final Geocoder geocoder = new Geocoder(activity);

        List<Address> list;

        try {
            list = geocoder
                    .getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

        if (list.isEmpty()) return MSG_NO_DATA;

        Address address = list.get(0);

        String city = address.getLocality();

        if (city != null) {
            return city;
        }
        else {
            city = address.getSubAdminArea();
        }

        return city;
    }
}