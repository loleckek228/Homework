package com.geekbrains.android.homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Switch beginnerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setColourOfTextView();
        setOnSwitchChanged();
    }

    private void initViews() {
        textView = findViewById(R.id.hellotextView);
        beginnerSwitch = findViewById(R.id.switch1);
    }

    private void setColourOfTextView() {
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }

    private void setOnSwitchChanged() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
            beginnerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String text;
                    if (isChecked) {
                        text = getString(R.string.i_m_beginner);
                    } else {
                        text = getString(R.string.im_expirienced);
                    }

                    textView.setText(text);
                }
            });
        }
    }
}
