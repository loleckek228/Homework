package com.geekbrains.android.homework.events;

import android.location.Location;

public class FoundLocationEvent {
    private Location location;

    public FoundLocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}