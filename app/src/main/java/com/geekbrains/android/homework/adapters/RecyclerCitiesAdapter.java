package com.geekbrains.android.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.CitiesSource;
import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.model.City;
import com.geekbrains.android.homework.weatherData.RetrievesWeatherData;

import java.util.List;

public class RecyclerCitiesAdapter extends RecyclerView.Adapter<RecyclerCitiesAdapter.ViewHolder> {
    private CitiesSource dataSource;
    private Fragment fragment;
    private Context context;

    private int listPosition = -1;

    public RecyclerCitiesAdapter(CitiesSource dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
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
        registerContextMenu(holder);
        setOnItemClickBehavior(holder, position);
        highLightSelectedPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return (int) dataSource.getCountCities();
    }

    private void setText(@NonNull ViewHolder holder, int position) {
        List<City> cities = dataSource.getCities();
        City city = cities.get(position);

        holder.cityTextView.setText(city.city);

        holder.dateTextView.setText(city.info.date);
        holder.temperatureTextView.setText(city.info.temperature);
    }

    private void setOnItemClickBehavior(@NonNull ViewHolder holder, int position) {
        holder.cardView.setOnLongClickListener(view -> {
            listPosition = position;

            return false;
        });

        holder.cardView.setOnClickListener(view -> {
            listPosition = position;

            highLightSelectedPosition(holder, position);

            List<City> cities = dataSource.getCities();
            City city = cities.get(position);

            RetrievesWeatherData.getInstance().updateWeatherData(city.city, true);
        });
    }

    private void highLightSelectedPosition(@NonNull ViewHolder holder, int position) {
        if (listPosition == position) {
            int backgroundColor = ContextCompat.getColor(context, android.R.color.holo_green_dark);
            int textColor = ContextCompat.getColor(context, android.R.color.black);

            holder.cityTextView.setTextColor(textColor);
            holder.dateTextView.setTextColor(textColor);
            holder.temperatureTextView.setTextColor(textColor);
            holder.cardView.setBackgroundColor(backgroundColor);
        } else {
            int backgroundColor = ContextCompat.getColor(context, android.R.color.transparent);
            int textColor = ContextCompat.getColor(context, R.color.colorAccent);

            holder.cityTextView.setTextColor(textColor);
            holder.dateTextView.setTextColor(textColor);
            holder.temperatureTextView.setTextColor(textColor);
            holder.cardView.setBackgroundColor(backgroundColor);
        }
    }

    private void registerContextMenu(@NonNull ViewHolder holder) {
        if (fragment != null) {
            fragment.registerForContextMenu(holder.cardView);
        }
    }

    public int getListPosition() {
        return listPosition;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityTextView;
        TextView dateTextView;
        TextView temperatureTextView;
        View cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            initView(itemView);
        }

        private void initView(View itemView) {
            cardView = itemView;
            cityTextView = cardView.findViewById(R.id.cityItemTextView);
            dateTextView = cardView.findViewById(R.id.dataItemTextView);
            temperatureTextView = cardView.findViewById(R.id.temperatureItemTextView);
        }
    }
}