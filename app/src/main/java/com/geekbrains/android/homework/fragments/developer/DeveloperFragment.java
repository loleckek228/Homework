package com.geekbrains.android.homework.fragments.developer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.geekbrains.android.homework.R;

public class DeveloperFragment extends Fragment {

    private DeveloperViewModel developerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        developerViewModel = new ViewModelProvider(this).get(DeveloperViewModel.class);

        View root = inflater.inflate(R.layout.fragment_developer, container, false);

        final TextView developerName = root.findViewById(R.id.developerTextView);

        developerViewModel.getText().observe(getViewLifecycleOwner(), text -> {
            developerName.setText(text);
        });

        return root;
    }
}