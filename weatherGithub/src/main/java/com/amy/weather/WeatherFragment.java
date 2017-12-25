package com.amy.weather;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amy.android.util.NetworkUtil;
import com.amy.android.util.TimingLogger;
import com.amy.android.util.UiUtil;
import com.amy.dynamicweather.BaseDrawer;
import com.amy.dynamicweather.BaseDrawer.Type;
import com.amy.weather.api.ApiManager;
import com.amy.weather.api.ApiManager.Area;
import com.amy.weather.api.entity.HeWeather6;
import com.amy.weather.github.R;
import com.amy.weather.widget.AqiView;
import com.amy.weather.widget.AstroView;
import com.amy.weather.widget.DailyForecastView;
import com.amy.weather.widget.HourlyForecastView;
import com.amy.weather.widget.PullRefreshLayout;

public class WeatherFragment extends BaseFragment {

	
	private View mRootView;
	private HeWeather6 mWeather;
	private DailyForecastView mDailyForecastView;
	private PullRefreshLayout pullRefreshLayout;
	private HourlyForecastView mHourlyForecastView;
	private AqiView mAqiView;
	private AstroView mAstroView;
	private Area mArea;
	private ScrollView mScrollView;
	private BaseDrawer.Type mDrawerType = BaseDrawer.Type.UNKNOWN_D;
	public BaseDrawer.Type getDrawerType() {
		// if(mDrawerType == null){
		// if(mWeather != null){
		// mDrawerType = ApiManager.convertWeatherType(mWeather);
		// }
		// }
		return this.mDrawerType;
	}

	private static final String BUNDLE_EXTRA_AREA = "BUNDLE_EXTRA_AREA";
	private static final String BUNDLE_EXTRA_WEATHER = "BUNDLE_EXTRA_WEATHER";

	// private static final String BUNDLE_SAVED_TYPE = "BUNDLE_SAVED_TYPE";

	public static WeatherFragment makeInstance(@NonNull Area area, HeWeather6 weather) {
		WeatherFragment fragment = new WeatherFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLE_EXTRA_AREA, area);
		if(weather != null){
			bundle.putSerializable(BUNDLE_EXTRA_WEATHER, weather);
		}
		fragment.setArguments(bundle);
		return fragment;
	}
	
	private void fetchArguments() {
		if (this.mArea == null) {
			try {
				this.mArea = (Area) getArguments().getSerializable(BUNDLE_EXTRA_AREA);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(this.mWeather == null){
			try {
				this.mWeather = (HeWeather6)getArguments().getSerializable(BUNDLE_EXTRA_WEATHER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.fragment_weather, null);
			mDailyForecastView = (DailyForecastView) mRootView.findViewById(R.id.w_dailyForecastView);
			pullRefreshLayout = (PullRefreshLayout) mRootView.findViewById(R.id.w_PullRefreshLayout);
			mHourlyForecastView = (HourlyForecastView) mRootView.findViewById(R.id.w_hourlyForecastView);
			mAqiView = (AqiView) mRootView.findViewById(R.id.w_aqi_view);
			mAstroView = (AstroView) mRootView.findViewById(R.id.w_astroView);
			mScrollView = (ScrollView) mRootView.findViewById(R.id.w_WeatherScrollView);
			pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					ApiManager.updateWeather(getActivity(), mArea.city, new ApiManager.ApiListener() {
						@Override
						public void onUpdateError() {
							pullRefreshLayout.setRefreshing(false);
						}

						@Override
						public void onReceiveWeather(HeWeather6 weather, boolean updated) {
							pullRefreshLayout.setRefreshing(false);
							if (updated) {
								if (ApiManager.acceptWeather(weather)) {
									WeatherFragment.this.mWeather = weather;
									updateWeatherUI();
								}
							}
						}
					});
				}
			});
//			debug();
			if(mWeather != null){
				updateWeatherUI();
			}
		} else {
//			UiUtil.toastDebug(getActivity(), "mRootView is not null, just use it");
			mScrollView.post(new Runnable() {
				@Override
				public void run() {
					mScrollView.scrollTo(0, 0);
				}
			});
			
		}
		return mRootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fetchArguments();
		UiUtil.logDebug("debug", "onCreate");
	}

	public String getCityName() {
		fetchArguments();
		if (this.mArea != null) {
			return mArea.city;
		} else {
			return "Error";
		}
	}

	private void updateDrawerTypeAndNotify() {
		final BaseDrawer.Type curType = ApiManager.convertWeatherType(mWeather);
		// if(this.mDrawerType != curType){
		this.mDrawerType = curType;
		notifyActivityUpdate();
		// }

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fetchArguments();
		UiUtil.logDebug("debug", "onActivityCreated");
		if (this.mArea == null) {
			return;
		}
		TimingLogger logger = new TimingLogger("WeatherFragment.onActivityCreated");
		if (this.mWeather == null) {
			this.mWeather = ApiManager.loadWeather(getActivity(), mArea.city);
			logger.addSplit("loadWeather");
			updateWeatherUI();
			logger.addSplit("updateWeatherUI");
		}
		logger.dumpToLog();
		if (mWeather == null) {
			postRefresh();
		} 
	}
	
	private void debug(){
		// DEBUG///////////////
					mRootView.findViewById(R.id.w_WeatherLinearLayout).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
							ArrayList<String> strs = new ArrayList<String>();
							for (BaseDrawer.Type t : BaseDrawer.Type.values()) {
								strs.add(t.toString());
							}
							int index = 0;
							for (int i = 0; i < Type.values().length; i++) {
								if (Type.values()[i] == mDrawerType) {
									index = i;
									break;
								}
							}
							builder.setSingleChoiceItems(strs.toArray(new String[] {}), index,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											mDrawerType = Type.values()[which];
											notifyActivityUpdate();
											dialog.dismiss();
										}
									});
							builder.setNegativeButton(android.R.string.cancel, null);
							builder.create().show();
						}
					});

	}

	private void postRefresh() {
		if (pullRefreshLayout != null) {
			Activity activity = getActivity();
			if(activity != null){
				if(NetworkUtil.isNetworkAvailable(activity))
					pullRefreshLayout.postDelayed(new Runnable() {
						@Override
						public void run() {
							pullRefreshLayout.setRefreshing(true, true);
						}
					}, 100);
			}
			
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			checkRefresh();
		}
		
	}

	private void checkRefresh() {
		if (mArea == null) {
			return;
		}
		if (getUserVisibleHint()) {
			if (ApiManager.isNeedUpdate(mWeather)) {
				postRefresh();
			}
		}
	}

	private void updateWeatherUI() {
//		UiUtil.toastDebug(getActivity(),getCityName()+ "updateWeatherUI");
		if (!ApiManager.acceptWeather(mWeather)) {
			return;
		}
		try {
			updateDrawerTypeAndNotify();
			mDailyForecastView.setData(mWeather);
			mHourlyForecastView.setData(mWeather);
			mAstroView.setData(mWeather);
			// setTextViewString(R.id.w_city, w.basic.city);
			final String tmp = mWeather.getHeWeather6().get(0).getNow().getTmp();
			try {
				final int tmp_int = Integer.valueOf(tmp);
				if(tmp_int < 0){
					setTextViewString(R.id.w_now_tmp,String.valueOf(-tmp_int));
					mRootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.VISIBLE);
				}else{
					setTextViewString(R.id.w_now_tmp, tmp);
					mRootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.GONE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				setTextViewString(R.id.w_now_tmp, tmp);
				mRootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.GONE);
			}
			
			setTextViewString(R.id.w_now_cond_text, mWeather.getHeWeather6().get(0).getNow().getCond_txt());

			if (ApiManager.isToday(mWeather.getHeWeather6().get(0).getUpdate().getLoc())) {
				setTextViewString(R.id.w_basic_update_loc, mWeather.getHeWeather6().get(0).getUpdate().getLoc().substring(11) + " 发布");
			} else {
				setTextViewString(R.id.w_basic_update_loc, mWeather.getHeWeather6().get(0).getUpdate().getLoc().substring(5) + " 发布");
			}

			setTextViewString(R.id.w_todaydetail_bottomline,mWeather.getHeWeather6().get(0).getNow().getCond_txt()+ "  " + mWeather.getTodayTempDescription());
			setTextViewString(R.id.w_todaydetail_temp, mWeather.getHeWeather6().get(0).getNow().getTmp() + "°");
			setTextViewString(R.id.w_now_fl,  mWeather.getHeWeather6().get(0).getNow().getFl() + "°");
			setTextViewString(R.id.w_now_hum,  mWeather.getHeWeather6().get(0).getNow().getHum()+ "%");// 湿度
			setTextViewString(R.id.w_now_vis,  mWeather.getHeWeather6().get(0).getNow().getVis()+ "km");// 能见度
			setTextViewString(R.id.w_now_pcpn,  mWeather.getHeWeather6().get(0).getNow().getPcpn()+ "mm"); // 降雨量
			if ( mWeather.getHeWeather6().get(0).getLifestyle()!= null) {
				for(int i=0;i<mWeather.getHeWeather6().get(0).getLifestyle().size();i++){
					switch (mWeather.getHeWeather6().get(0).getLifestyle().get(i).getType()){
						case"comf":
							setTextViewString(R.id.w_suggestion_comf,mWeather.getHeWeather6().get(0).getLifestyle().get(i).getTxt());
							setTextViewString(R.id.w_suggestion_comf_brf, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getBrf());
							break;
						case "drsg":
							setTextViewString(R.id.w_suggestion_drsg,mWeather.getHeWeather6().get(0).getLifestyle().get(i).getTxt());
							setTextViewString(R.id.w_suggestion_drsg_brf, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getBrf());
							break;
						case "flu":
							setTextViewString(R.id.w_suggestion_flu, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getTxt());
							setTextViewString(R.id.w_suggestion_flu_brf, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getBrf());
							break;
						case "sport":
							setTextViewString(R.id.w_suggestion_sport, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getTxt());
							setTextViewString(R.id.w_suggestion_sport_brf, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getBrf());
							break;
						case "trav":
							setTextViewString(R.id.w_suggestion_tarv, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getTxt());
							setTextViewString(R.id.w_suggestion_tarv_brf, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getBrf());
							break;
						case "uv":
							setTextViewString(R.id.w_suggestion_uv, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getTxt());
							setTextViewString(R.id.w_suggestion_uv_brf, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getBrf());
							break;
						case "cw":
							setTextViewString(R.id.w_suggestion_cw, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getTxt());
							setTextViewString(R.id.w_suggestion_cw_brf, mWeather.getHeWeather6().get(0).getLifestyle().get(i).getBrf());
							break;
						case "air":
							break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			toast(mArea.city + " Error\n" + e.toString());

		}

	}

	@Override
	public void onDestroy() {
		// textviews.clear();
		super.onDestroy();
	}
	private void setTextViewString(int textViewId, String str) {
		TextView tv = (TextView) mRootView.findViewById(textViewId);
		if (tv != null) {
			tv.setText(str);
		} else {
			toast("Error NOT found textView id->" + Integer.toHexString(textViewId));
		}
	}
	@Override
	public String getTitle() {
		return getCityName();
	}

	@Override
	public void onResume() {
		super.onResume();
		checkRefresh();
	}

	@Override
	public void onSelected() {
		// checkRefresh();
	}
	
	public static int getCondIconDrawableId(final HeWeather6 weather){
		if (weather == null || !weather.getHeWeather6().get(0).getStatus().equals("ok")) {
			return R.drawable.cond_icon_na;
		}
		final boolean isNight = ApiManager.isNight(weather);
		try {
			final int w = Integer.valueOf(weather.getHeWeather6().get(0).getNow().getCond_code());
			switch (w) {
			case 100:
				return isNight ? R.drawable.cond_icon_sun_night : R.drawable.cond_icon_sun;
			case 101://多云
			case 102://少云
			case 103://晴间多云
				return isNight ? R.drawable.cond_icon_cloudy_night : R.drawable.cond_icon_cloudy;
			case 104://阴
				return R.drawable.cond_icon_overcast;
				//200 - 213是风
			case 300://阵雨Shower Rain
			case 305://小雨	Light Rain
			case 309://毛毛雨/细雨	Drizzle Rain
				return R.drawable.cond_icon_lightrain;
			case 302://雷阵雨	Thundershower
			case 303://强雷阵雨	Heavy Thunderstorm
				return R.drawable.cond_icon_thundershower;
			case 304://雷阵雨伴有冰雹	Hail
				return R.drawable.cond_icon_hail;
			case 306://中雨	Moderate Rain
				return R.drawable.cond_icon_moderaterain;
			case 307://大雨	Heavy Rain
			case 301://强阵雨	Heavy Shower Rain
			case 308://极端降雨	Extreme Rain
			case 310://暴雨	Storm
			case 311://大暴雨	Heavy Storm
			case 312://特大暴雨	Severe Storm
				return R.drawable.cond_icon_heavyrain;
			case 313://冻雨	Freezing Rain
				return R.drawable.cond_icon_icerain;
			case 400://小雪 Light Snow
			case 401://中雪 Moderate Snow
			case 407://阵雪 Snow Flurry
				return R.drawable.cond_icon_lightsnow;
			case 402://大雪 Heavy Snow
			case 403://暴雪 Snowstorm
				return R.drawable.cond_icon_snowstorm;
			case 404://雨夹雪 Sleet
			case 405://雨雪天气 Rain And Snow
			case 406://阵雨夹雪 Shower Snow
				return R.drawable.cond_icon_sleet;
			case 500://薄雾
			case 501://雾
				return R.drawable.cond_icon_foggy;
			case 502://霾
			case 504://浮尘
				return R.drawable.cond_icon_haze;
			case 503://扬沙
			case 506://火山灰
			case 507://沙尘暴
			case 508://强沙尘暴
				return R.drawable.cond_icon_sand;
			default:
				return R.drawable.cond_icon_na;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return R.drawable.cond_icon_na;
			}
}
