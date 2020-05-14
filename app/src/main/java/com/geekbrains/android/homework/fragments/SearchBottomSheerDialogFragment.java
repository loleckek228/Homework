package com.geekbrains.android.homework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geekbrains.android.homework.OnDialogListener;
import com.geekbrains.android.homework.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class SearchBottomSheerDialogFragment extends BottomSheetDialogFragment {
    private OnDialogListener dialogListener;
    private TextInputEditText inputCityEditText;

    public static SearchBottomSheerDialogFragment newInstance() {
        return new SearchBottomSheerDialogFragment();
    }
    public void setOnDialogListener (OnDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);

        inputCityEditText = view.findViewById(R.id.inputEditText);

        view.findViewById(R.id.searchButton).setOnClickListener((buttonView) -> {
            String city = inputCityEditText.getText().toString();

            dismiss();

            if (dialogListener != null) dialogListener.onDialogSearch(city);
        });

        view.findViewById(R.id.backButton).setOnClickListener(buttonView -> {
            dismiss();
            if (dialogListener != null) dialogListener.onDialogBack();
        });
    }
}