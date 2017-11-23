package com.dragonfly.weatherdemo.weather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dragonfly.weatherdemo.weather.R;
import com.dragonfly.weatherdemo.weather.bean.Weather;

import java.util.List;

/**
 * Created by Amy on 2017/9/26.
 */

public class WeatherAdapter extends BaseAdapter {
    Context context;
    List<Weather.QueryBean.ResultsBean.ChannelBean.ItemBean.ForecastBean> forecastBeens;

    public WeatherAdapter(Context context, List<Weather.QueryBean.ResultsBean.ChannelBean.ItemBean.ForecastBean> forecastBeens) {
        this.context = context;
        this.forecastBeens = forecastBeens;
    }

    @Override
    public int getCount() {
        return forecastBeens == null ? 0 : forecastBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return forecastBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.weather_list_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_weater = (TextView) convertView.findViewById(R.id.tv_weater);
            viewHolder.tv_low = (TextView) convertView.findViewById(R.id.tv_low);
            viewHolder.tv_high = (TextView) convertView.findViewById(R.id.tv_high);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_date.setText(forecastBeens.get(position).getDay());
        viewHolder.tv_weater.setText(forecastBeens.get(position).getText());
        viewHolder.tv_low.setText(forecastBeens.get(position).getLow());
        viewHolder.tv_high.setText(forecastBeens.get(position).getHigh());
        return convertView;
    }

    class ViewHolder {
        TextView tv_date, tv_weater, tv_low, tv_high;
    }
}
