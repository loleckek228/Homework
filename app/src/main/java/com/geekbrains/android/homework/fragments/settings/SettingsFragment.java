package com.geekbrains.android.homework.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.geekbrains.android.homework.R;

public class SettingsFragment extends Fragment {
    private SettingViewModel settingViewModel;

    private Switch isTheme;
    private Switch isSound;
    private Switch isNotification;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingViewModel = new ViewModelProvider(getActivity()).get(SettingViewModel.class);

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        isTheme = root.findViewById(R.id.themeSwitch);
        isSound = root.findViewById(R.id.soundSwitch);
        isNotification = root.findViewById(R.id.notificationSwitch);

        settingViewModel.getIsTheme().observe(getViewLifecycleOwner(), isTheme -> {
            this.isTheme.setChecked(isTheme);
        });

        settingViewModel.getIsSound().observe(getViewLifecycleOwner(), isSound -> {
            this.isSound.setChecked(isSound);
        });

        settingViewModel.getIsNotification().observe(getViewLifecycleOwner(), isNotification -> {
            this.isNotification.setChecked(isNotification);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setOnIsThemeSwitchChanged();
        setOnIsSoundSwitchChanged();
        setOnIsNotificationSwitchChanged();
    }

    private void setOnIsThemeSwitchChanged() {
        isTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {

            settingViewModel.setIsTheme(isChecked);

        });
    }

    private void setOnIsSoundSwitchChanged() {
        isSound.setOnCheckedChangeListener((buttonView, isChecked) -> {

            settingViewModel.setIsSound(isChecked);

        });
    }

    private void setOnIsNotificationSwitchChanged() {
        isNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {

            settingViewModel.setIsNotification(isChecked);

        });
    }
}