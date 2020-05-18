package com.geekbrains.android.homework.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.android.homework.R;

public class ErrorActivity extends AppCompatActivity {
    private TextView errorTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        initViews();

        setErrorTextView();
    }

    private void initViews() {
        errorTextView = findViewById(R.id.errorTextView);
    }

    private void setErrorTextView() {
        String error = getResources().getString(R.string.error);
        String city = getResources().getString(R.string.city);
        String cityName = getIntent().getStringExtra("City");
        String notFound = getResources().getString(R.string.not_found);

        errorTextView.setText(error + "\n" + city + ": " + cityName + "\n" + notFound);
    }
}