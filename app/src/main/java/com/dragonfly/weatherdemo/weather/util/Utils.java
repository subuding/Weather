package com.dragonfly.weatherdemo.weather.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Amy on 2017/9/26.
 */

public class Utils {

    /**
     * @param listView 重新测量listview的高度（滑动布局嵌套滑动布局）
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    //设置key保存数据
    public static void savaData(Context context,String data,String key){
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mSharedPreferences= context.getSharedPreferences("data",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString(key, data);
        //提交当前数据
        editor.commit();
    }
    //通过key获取数据
    public static String getData(Context context,String key){
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mSharedPreferences= context.getSharedPreferences("data",
                Activity.MODE_PRIVATE);
        String data =mSharedPreferences.getString(key,null);
        return  data;
    }
}
