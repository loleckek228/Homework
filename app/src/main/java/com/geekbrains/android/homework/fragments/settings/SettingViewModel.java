package com.geekbrains.android.homework.fragments.settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {
    private MutableLiveData<Boolean> isTheme;
    private MutableLiveData<Boolean> isSound;
    private MutableLiveData<Boolean> isNotification;

    public MutableLiveData<Boolean> getIsTheme() {
        if (isTheme == null) {
            isTheme = new MutableLiveData<>();
        }

        return isTheme;
    }

    public MutableLiveData<Boolean> getIsSound() {
        if (isSound == null) {
            isSound = new MutableLiveData<>();
        }

        return isSound;
    }

    public MutableLiveData<Boolean> getIsNotification() {
        if (isNotification == null) {
            isNotification = new MutableLiveData<>();
        }

        return isNotification;
    }

    public void setIsTheme(Boolean isTheme) {
        this.isTheme.setValue(isTheme);
    }

    public void setIsSound(Boolean isSound) {
        this.isSound.setValue(isSound);
    }

    public void setIsNotification(Boolean isNotification) {
        this.isNotification.setValue(isNotification);
    }
}
