package com.geekbrains.android.homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerWeatherAdapter extends RecyclerView.Adapter<RecyclerWeatherAdapter.ViewHolder> {
    private Weather[] weathers;
    private Context context;

    public RecyclerWeatherAdapter(Weather[] weathers) {
        this.weathers = weathers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lilst_temperature_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setText(holder, position);
        setColorOfTextView(holder);
    }

    @Override
    public int getItemCount() {
        return weathers == null ? 0 : weathers.length;
    }

    private void setText(@NonNull ViewHolder holder, int position) {
        holder.dataListItemTextView.setText(weathers[position].getDate());
        holder.temperatureListItemTextView.setText(weathers[position].getTemperature());
    }

    private void setColorOfTextView(@NonNull ViewHolder holder) {
        holder.dataListItemTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        holder.temperatureListItemTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dataListItemTextView;
        TextView temperatureListItemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            dataListItemTextView = itemView.findViewById(R.id.dataListItemTextView);
            temperatureListItemTextView = itemView.findViewById(R.id.temperatureListItemTextView);
        }
    }
}
