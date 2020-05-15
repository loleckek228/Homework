package com.geekbrains.android.homework;

import androidx.fragment.app.Fragment;

public class CurrentFragment {
    private static CurrentFragment instance;

    private CurrentFragment() {
    }

    public static CurrentFragment getInstance() {
        if (instance == null) {
            instance = new CurrentFragment();
        }

        return instance;
    }

    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }
}