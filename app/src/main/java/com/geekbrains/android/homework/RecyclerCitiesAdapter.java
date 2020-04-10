package com.geekbrains.android.homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.fragments.CitiesFragment;

import java.util.ArrayList;

public class RecyclerCitiesAdapter extends RecyclerView.Adapter<RecyclerCitiesAdapter.ViewHolder> {
    private ArrayList<String> citiesList;
    private CitiesFragment citiesFragment;
    private Context context;

    private int selectedPosition = -1;

    public RecyclerCitiesAdapter(ArrayList<String> citiesList, CitiesFragment citiesFragment) {
        this.citiesList = citiesList;
        this.citiesFragment = citiesFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cities_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setText(holder, position);
        setColourOfTextView(holder);
        setOnItemClickBehavior(holder, position);
        highLightSelectedPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return citiesList == null ? 0 : citiesList.size();
    }

    private void setText(@NonNull ViewHolder holder, int position) {
        holder.cityTextView.setText(citiesList.get(position));
    }

    private void setOnItemClickBehavior(@NonNull ViewHolder holder, int position) {
        holder.cityTextView.setOnClickListener((view) -> {
            selectedPosition = position;
            citiesFragment.showWeather(citiesList, position);

            notifyDataSetChanged();
        });
    }

    private void setColourOfTextView(@NonNull ViewHolder holder) {
        int textColor = ContextCompat.getColor(context, android.R.color.holo_green_dark);

        holder.cityTextView.setTextColor(textColor);
    }

    private void highLightSelectedPosition(@NonNull ViewHolder holder, int position) {
        if (position == selectedPosition) {
            int backgroundColor = ContextCompat.getColor(context, android.R.color.holo_green_dark);
            int textColor = ContextCompat.getColor(context, android.R.color.black);

            holder.cityTextView.setTextColor(textColor);
            holder.cityTextView.setBackgroundColor(backgroundColor);
        } else {
            int color = ContextCompat.getColor(context, android.R.color.transparent);

            holder.cityTextView.setBackgroundColor(color);

            setColourOfTextView(holder);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            initView(itemView);
        }

        private void initView(View itemView) {
            cityTextView = itemView.findViewById(R.id.cityListItemTextView);
        }
    }
}