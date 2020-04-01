package com.geekbrains.android.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String temperatureDataKey = "temperatureDataKey";
    private final String TAG = this.getClass().getSimpleName();

    private TextView temperatureTextView;
    private TextView dataTextView;
    private TextView timeTextView;

    private int temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setColourOfTextView();
        setDate(dataTextView = findViewById(R.id.dataTextView), "dd.MM.yyyy");
        setDate(timeTextView = findViewById(R.id.timeTextView), "hh:mm");

        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск!";
        } else {
            instanceState = "Повторный запуск!";
        }

        Toast.makeText(getApplicationContext(), instanceState + " - onCreate()", Toast.LENGTH_SHORT).show();
         Log.d(TAG, "On create");
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

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        Toast.makeText(getApplicationContext(), "Повторный запуск!! - onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Повторный запуск!! - onRestoreInstanceState()");

        DataContainer conteiner = (DataContainer) saveInstanceState.getSerializable(temperatureDataKey);

        temperatureTextView.setText(conteiner.getTemperatureText());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        Toast.makeText(getApplicationContext(), "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onSaveInstanceState()");

        saveInstanceState.putSerializable(temperatureDataKey, DataContainer.getInstance(temperature));

        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy()");
    }
}