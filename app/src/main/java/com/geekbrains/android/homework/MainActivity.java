package com.geekbrains.android.homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView temperatureTextView;
    private TextView dataTextView;
    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setColourOfTextView();
        setDate(dataTextView = findViewById(R.id.dataTextView), "dd.MM.yyyy");
        setDate(timeTextView = findViewById(R.id.timeTextView), "hh:mm");
    }

    private void initViews() {
        temperatureTextView = findViewById(R.id.temperatureTextView);
    }

    private void setColourOfTextView() {
        temperatureTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }

    private void setDate(TextView textView, String format) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(() -> {
                            Date currentData = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
                            String data = dateFormat.format(currentData);
                            textView.setText(data);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}