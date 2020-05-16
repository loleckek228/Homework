package com.geekbrains.android.homework.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.geekbrains.android.homework.R;

public class DialogBuilderFragment extends DialogFragment {

    private String city;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public DialogBuilderFragment(String city) {
        this.city = city;
    }

    public DialogBuilderFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (city != null) {
            String error = getResources().getString(R.string.error);
            String city = getResources().getString(R.string.city);

            String notFound = getResources().getString(R.string.not_found);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(error)
                    .setNegativeButton("Назад", ((dialog, i) -> {
                        dismiss();
                    }))
                    .setMessage(city + ": \"" + this.city + "\" " + notFound);

            return builder.create();
        }

        String error = getResources().getString(R.string.error);

        String networkProblem = getResources().getString(R.string.network_problem);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(error)
                .setNegativeButton("Назад", ((dialog, i) -> {
                    dismiss();
                }))
                .setMessage(networkProblem);

        return builder.create();
    }
}