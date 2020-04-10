package com.geekbrains.android.homework;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int countOfFragmentInManger = getSupportFragmentManager().getBackStackEntryCount();

        if (countOfFragmentInManger > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }
}