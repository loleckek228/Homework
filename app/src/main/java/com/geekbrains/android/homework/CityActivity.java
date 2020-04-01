package com.geekbrains.android.homework;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CityActivity extends AppCompatActivity {

    private final String temperatureCheckBoxDataKey = "temperatureCheckBoxDataKey";
    private final String wildSpeedCheckBoxDataKey = "wildSpeedCheckBoxDataKey";

    private ListView citiesList;
    private CheckBox temperatureCheckBox;
    private CheckBox wildSpeedCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView() {
        temperatureCheckBox = findViewById(R.id.temperatureCheckBox);
        wildSpeedCheckBox = findViewById(R.id.windSpeedCheckBox);
        initСitiesList();
    }

    private void initСitiesList() {
        final TextView textView = findViewById(R.id.txt);
        citiesList = findViewById(R.id.listView);
        ArrayAdapter<CharSequence> adapter
                = ArrayAdapter.createFromResource(this, R.array.cities,
                android.R.layout.simple_expandable_list_item_1);
        citiesList.setAdapter(adapter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);

        Boolean isTemperature = saveInstanceState.getBoolean(temperatureCheckBoxDataKey, false);
        Boolean isWilsSpeed = saveInstanceState.getBoolean(wildSpeedCheckBoxDataKey, false);

        temperatureCheckBox.setChecked(isTemperature);
        wildSpeedCheckBox.setChecked(isWilsSpeed);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        saveInstanceState.putBoolean(temperatureCheckBoxDataKey, temperatureCheckBox.isChecked());
        saveInstanceState.putBoolean(wildSpeedCheckBoxDataKey, wildSpeedCheckBox.isChecked());
        super.onSaveInstanceState(saveInstanceState);
    }
}