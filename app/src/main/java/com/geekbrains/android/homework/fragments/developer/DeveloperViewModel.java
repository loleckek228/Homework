package com.geekbrains.android.homework.fragments.developer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.geekbrains.android.homework.R;

public class DeveloperViewModel extends AndroidViewModel {
    private MutableLiveData<String> mText;

    public DeveloperViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue(application.getString(R.string.developer_name));
    }

    public LiveData<String> getText() {
        return mText;
    }
}