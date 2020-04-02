package com.geekbrains.android.homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final String Container_Data_Key = "Container_Data_Key";
    private final String TAG = this.getClass().getSimpleName();

    private TextView temperatureTextView;
    private TextView wildSpeedTextView;
    private TextView dataTextView;
    private TextView timeTextView;
    private TextView cityTextView;

    private Button startCityActivityButton;

    private int secondActivityRequestCode = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setColourOfTextView();
        setOnStartCityActivityButtonClick();
        setDate(dataTextView, "dd.MM.yyyy");
        setDate(timeTextView, "hh:mm");


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
        wildSpeedTextView = findViewById(R.id.wildSpeedTextView);
        startCityActivityButton = findViewById(R.id.chooseCityButton);
        cityTextView = findViewById(R.id.cityTextView);
        dataTextView = findViewById(R.id.dataTextView);
        timeTextView = findViewById(R.id.timeTextView);
    }

    private void setColourOfTextView() {
        temperatureTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        wildSpeedTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        dataTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        timeTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        cityTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }

    private void setOnStartCityActivityButtonClick() {
        startCityActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CityActivity.class);
            startActivityForResult(intent, secondActivityRequestCode);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == secondActivityRequestCode && resultCode == RESULT_OK) {

            String text = null;
            Boolean isTemperature = false;
            Boolean isWildSpeed = false;

            if (data != null) {
                text = data.getStringExtra(CityActivity.CITY_DATA_KEY);
                isTemperature = data.getBooleanExtra(CityActivity.TEMPERATURE_CHECKBOX_DATA_KEY, false);
                isWildSpeed = data.getBooleanExtra(CityActivity.WILDSPEED_CHECKBOX_DATA_KEY, false);
            }

            cityTextView.setText(text);

            Random random = new Random();

            if (isTemperature) {
                temperatureTextView.setText(random.nextInt(10) + "C");
            }

            if (isWildSpeed) {
                wildSpeedTextView.setText(String.valueOf(random.nextInt(5)));
            }
        }
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

        DataContainer container = (DataContainer) saveInstanceState.getSerializable(Container_Data_Key);

        cityTextView.setText(container.getCity());
        temperatureTextView.setText(container.getTemperature());
        wildSpeedTextView.setText(container.getWildSpeed());
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

        DataContainer.getInstance().setCity(cityTextView.getText().toString());
        DataContainer.getInstance().setTemperature(temperatureTextView.getText().toString());
        DataContainer.getInstance().setWildSpeed(wildSpeedTextView.getText().toString());
        saveInstanceState.putSerializable(Container_Data_Key, DataContainer.getInstance());

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