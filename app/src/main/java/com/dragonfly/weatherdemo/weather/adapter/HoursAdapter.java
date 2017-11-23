package com.dragonfly.weatherdemo.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragonfly.weatherdemo.weather.R;

import java.util.List;

/**
 * Created by Amy on 2017/10/9.
 */

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder> {
    LayoutInflater mLayoutInflater;

    public HoursAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.hous_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.time = (TextView) view.findViewById(R.id.time);
        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
        viewHolder.temperature = (TextView) view.findViewById(R.id.temperature);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 24;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, temperature;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
