package com.dragonfly.weatherdemo.weather;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonfly.weatherdemo.weather.adapter.HoursAdapter;
import com.dragonfly.weatherdemo.weather.adapter.WeatherAdapter;
import com.dragonfly.weatherdemo.weather.bean.Weather;
import com.dragonfly.weatherdemo.weather.bean.Woeid;
import com.dragonfly.weatherdemo.weather.util.NetUtils;
import com.dragonfly.weatherdemo.weather.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.myedit)
    EditText myedit;
    Gson gson;
    @BindView(R.id.list)
    ListView list;
    String latLongString;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private LocationManager locationManager;
    @BindView(R.id.adView)
    NativeExpressAdView adView;
    HoursAdapter hoursAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 0:
                    String result = bundle.getString("woeid");
                    Woeid woeid = gson.fromJson(result, Woeid.class);
                    if (woeid.getQuery().getResults().getPlace().getWoeid() != null) {
                        getWeather(woeid.getQuery().getResults().getPlace().getWoeid());
                    }
                    break;
                case 1:
                    String string = bundle.getString("weather");
                    Utils.savaData(MainActivity.this,string,"data");
                    setData(string);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gson = new Gson();
        String data= Utils.getData(this,"data");
        Log.i("1234567890", "onCreate: "+data);
        if(data!=null){
            setData(data);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        hoursAdapter = new HoursAdapter(this);
        recyclerview.setAdapter(hoursAdapter);
        loadAndShowAD();
    }

    @OnClick({R.id.searchweather, R.id.im_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchweather:
                if (myedit.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "请输入城市", Toast.LENGTH_SHORT).show();
                } else {
                    getWoeid(myedit.getText().toString().trim());
                }
                break;
            case R.id.im_location:
                location();
                break;
        }
    }

    //定位
    private void location() {
        new Thread() {
            @Override
            public void run() {
                //6.0及更改版本动态申请权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);
                } else {
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        List<Address> addList = null;
                        Geocoder ge = new Geocoder(getApplicationContext());
                        try {
                            addList = ge.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (addList != null && addList.size() > 0) {
                            for (int i = 0; i < addList.size(); i++) {
                                Address ad = addList.get(i);
                                latLongString = ad.getLocality();
                                getWoeid(latLongString);
                            }
                        }
                    }
                }
            }
        }.start();
    }

    public void loadAndShowAD() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4951381514453260~9189247782");
        adView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());
        adView.loadAd(new AdRequest.Builder().build());
    }

    //获取城市woeid
    public void getWoeid(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new NetUtils().get("https://query.yahooapis.com/v1/public/yql?q=" + URLEncoder.encode("select woeid from geo.places(1) where text=\"" + s + "\"") + "&format=" + URLEncoder.encode("json"), handler, 0, "woeid");
            }
        }).start();
    }

    //通过城市的woeid获取该城市天气
    public void getWeather(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new NetUtils().get("https://query.yahooapis.com/v1/public/yql?q=" + URLEncoder.encode("select * from weather.forecast where woeid=" + s + " and u=\"c\"") + "&format=" + URLEncoder.encode("json"), handler, 1, "weather");
            }
        }).start();
    }

    //申请权限后调用
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            //重新发起定位
            location();
        }
    }
    //为view设置数据
    private void setData(String data) {
        Weather weather = gson.fromJson(data, Weather.class);
        city.setText(weather.getQuery().getResults().getChannel().getLocation().getCity());
        WeatherAdapter weatherAdapter = new WeatherAdapter(MainActivity.this, weather.getQuery().getResults().getChannel().getItem().getForecast());
        list.setAdapter(weatherAdapter);
        Utils.setListViewHeightBasedOnChildren(list);//重新计算list view的高度（滑动布局嵌套滑动布局）
        weatherAdapter.notifyDataSetChanged();
    }
}