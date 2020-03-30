package com.geekbrains.android.homework;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CityActivity extends AppCompatActivity {

    private ListView citiesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView() {
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
}