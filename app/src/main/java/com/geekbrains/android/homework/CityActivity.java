package com.geekbrains.android.homework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CityActivity extends AppCompatActivity {

    static final String TEMPERATURE_CHECKBOX_DATA_KEY = "temperature_CheckBox_Data_Key";
    static final String WILDSPEED_CHECKBOX_DATA_KEY = "wild_Speed_CheckBox_Data_Key";
    static final String CITY_DATA_KEY = "city_second_activity_data_key";

    private ListView citiesList;
    private EditText enterCityEditText;
    private Button chooseCityButton;
    private Button showInfoAboutCityButton;
    private CheckBox temperatureCheckBox;
    private CheckBox wildSpeedCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        initView();
        setOnChooseCityButtonClickBehavior();
        setOnShowInfoAboutCityButtonClick();
    }

    private void initView() {
        initСitiesListView();
        enterCityEditText = findViewById(R.id.cityEditText);
        chooseCityButton = findViewById(R.id.setCityButton);
        showInfoAboutCityButton = findViewById(R.id.showInfoAboutCityButton);
        temperatureCheckBox = findViewById(R.id.temperatureCheckBox);
        wildSpeedCheckBox = findViewById(R.id.wildSpeedCheckBox);
    }

    private void initСitiesListView() {
        final TextView textView = findViewById(R.id.txt);
        citiesList = findViewById(R.id.listView);
        ArrayAdapter<CharSequence> adapter
                = ArrayAdapter.createFromResource(this, R.array.cities,
                android.R.layout.simple_expandable_list_item_1);
        citiesList.setAdapter(adapter);
    }

    private void setOnChooseCityButtonClickBehavior() {
        chooseCityButton.setOnClickListener((v -> {
            Intent intentСontainer = new Intent();
            String city = enterCityEditText.getText().toString().trim();
            Boolean isTemperature = temperatureCheckBox.isChecked();
            Boolean isWildSpeed = wildSpeedCheckBox.isChecked();

            intentСontainer.putExtra(CITY_DATA_KEY, city);
            intentСontainer.putExtra(TEMPERATURE_CHECKBOX_DATA_KEY, isTemperature);
            intentСontainer.putExtra(WILDSPEED_CHECKBOX_DATA_KEY, isWildSpeed);

            setResult(CityActivity.RESULT_OK, intentСontainer);
            finish();
        }));
    }

    private void setOnShowInfoAboutCityButtonClick() {
        showInfoAboutCityButton.setOnClickListener((v -> {
            String url = "https://ru.wikipedia.org/wiki/" + enterCityEditText.getText().toString();
            Uri uri = Uri.parse(url);
            Intent browser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(browser);
        }));
    }
}