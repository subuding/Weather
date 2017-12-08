package com.amy.weather.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.os.AsyncTaskCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.amy.android.util.MxxPreferenceUtil;
import com.amy.android.util.UiUtil;
import com.amy.dynamicweather.BaseDrawer;
import com.amy.dynamicweather.BaseDrawer.Type;
import com.amy.weather.api.entity.HeWeather6;

@SuppressLint("SimpleDateFormat") @SuppressWarnings("deprecation")
public class ApiManager {

//	private static AreaData AREA_DATA;

	static final String Url = "https://api.heweather.com/x3/weather?key=e80f9ffa350b4bfa9a85276076eea817&cityid=";
	static final Gson GSON = new Gson();
	static final String TAG = ApiManager.class.getSimpleName();

	public interface GeocoderListener {
		public void onConvertCity(String cityName);

		public void onError();
	}

	public interface ApiListener {
		public void onReceiveWeather(HeWeather6 weather, boolean updated);

		public void onUpdateError();
	}

	public interface LoadAreaDataListener {
		public void onLoaded(AreaData areaData);

		public void onError();
	}

	private static final String KEY_SELECTED_AREA = "KEY_SELECTED_AREA";
	private static final String KEY_DEFAULT_SELECTED_AREA_ID = "KEY_DEFAULT_SELECTED_AREA_ID";

//	public static Weather loadDefaultSelectedAreaWeather(Context context) {
//		final String defalutAreaId = getDefaultSelectedAreaId(context);
//		if (!TextUtils.isEmpty(defalutAreaId)) {
//			return loadWeather(context, defalutAreaId);
//		}
//		return null;
//	}

	public static ArrayList<Area> loadSelectedArea(Context context) {
		ArrayList<Area> areas = new ArrayList<ApiManager.Area>();
		String json =MxxPreferenceUtil.getPrefString(context,"Areas","area",null);
		if (TextUtils.isEmpty(json)) {
			return areas;
		}
		try {
			Area[] aa = GSON.fromJson(json, Area[].class);
			if (aa != null) {
				Collections.addAll(areas, aa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areas;
	}

	/**
	 * 保存已选择的位置 并清空所有的位置的天气信息 并把第一个位置设置为默认（为桌面小工具)
	 * 
	 * @param context
	 * @param areas
	 */
//	public static void saveSelectedArea(Context context, ArrayList<Area> areas) {
//		try {
//			ArrayList<Area> oldAreas = loadSelectedArea(context);
//			for (Area oldArea : oldAreas) {
//				if (!areas.contains(oldArea)) {
//					MxxPreferenceUtil.removePreference(context, oldArea.id);
//					Log.d(TAG, "removePreference->" + oldArea.name_cn + "@" + oldArea.id);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			MxxPreferenceUtil.setPrefString(context, KEY_SELECTED_AREA, GSON.toJson(areas));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			if (areas != null && areas.size() > 0) {
//				setDefaultSelectedAreaId(context, areas.get(0));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	private static void setDefaultSelectedAreaId(Context context, Area area) {
//		Log.d(TAG, "setDefaultSelectedAreaId->" + area.name_cn + "@" + area.id);
//		try {
//			MxxPreferenceUtil.setPrefString(context, KEY_DEFAULT_SELECTED_AREA_ID, area.id);
//			UpdateManager.sendUpdateWeather(context, loadDefaultSelectedAreaWeather(context));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static String getDefaultSelectedAreaId(@NonNull Context context) {
//		Log.d(TAG,
//				"getDefaultSelectedAreaId->"
//						+ MxxPreferenceUtil.getPrefString(context, KEY_DEFAULT_SELECTED_AREA_ID, null));
//		return MxxPreferenceUtil.getPrefString(context, KEY_DEFAULT_SELECTED_AREA_ID, null);
//	}

	/**
	 * 转换为城市 返回城市名 如”北京“（不包括”市“）
	 * 
	 * @param context
	 * @param location
	 * @param geocoderListener
	 */
//	public static void convertLocationToCity(@NonNull final Location location, final GeocoderListener geocoderListener) {
//		final String url = "http://api.map.baidu.com/geocoder/v2/?ak=yourkey&output=json&pois=0coordtype=wgs84ll&location="
//				+ location.getLatitude() + "," + location.getLongitude();
//		AsyncTask<Void, Void, String> loadTask = new AsyncTask<Void, Void, String>() {
//
//			@Override
//			protected String doInBackground(Void... params) {
//				try {
//					String json = doHttpGet(url);
//					JSONObject jsonObject = new JSONObject(json);
//					String cityName = jsonObject.getJSONObject("result").getJSONObject("addressComponent")
//							.getString("city");
//					if (!TextUtils.isEmpty(cityName)) {
//						if (cityName.endsWith("市")) {
//							cityName = cityName.substring(0, cityName.length() - 1);
//						}
//						return cityName;
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return null;
//			}
//
//			@Override
//			protected void onPostExecute(String result) {
//				super.onPostExecute(result);
//				if (geocoderListener != null) {
//					if (!TextUtils.isEmpty(result)) {
//						geocoderListener.onConvertCity(result);
//					} else {
//						geocoderListener.onError();
//					}
//				}
//
//			}
//		};
//		AsyncTaskCompat.executeParallel(loadTask);
//	}

	/**
	 * load AreaData from assets
	 *
	 * @param context
	 */
//	public static void loadAreaData(final Context context, final LoadAreaDataListener loadAreaDataListener) {
//		if (loadAreaDataListener != null) {
//			if (AREA_DATA != null && AREA_DATA.list != null && AREA_DATA.list.size() > 0) {
//				loadAreaDataListener.onLoaded(AREA_DATA);
//				return;
//			}
//		}
//		final AssetManager assetManager = context.getAssets();
//		AsyncTask<Void, Void, AreaData> loadTask = new AsyncTask<Void, Void, AreaData>() {
//
//			@Override
//			protected AreaData doInBackground(Void... params) {
//				try {
//					InputStream is = assetManager.open("AreaData");
//					InputStreamReader isr = new InputStreamReader(is);
//					JsonReader reader = new JsonReader(isr);
//					AreaData areaData = GSON.fromJson(reader, AreaData.class);
//					reader.close();
//					return areaData;
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return null;
//			}
//
//			@Override
//			protected void onPostExecute(AreaData result) {
//				super.onPostExecute(result);
//				if (loadAreaDataListener != null) {
//					if (result != null && result.list != null && result.list.size() > 0) {
//						AREA_DATA = result;
//						loadAreaDataListener.onLoaded(result);
//					} else {
//						loadAreaDataListener.onError();
//					}
//				}
//
//			}
//		};
//		AsyncTaskCompat.executeParallel(loadTask);
//	}

	public static void updateWeather(@NonNull Context context, @NonNull String areaId, @NonNull ApiListener apiListener) {
		if (TextUtils.isEmpty(areaId)) {
			return;
		}
		UiUtil.logDebug("debug", "APImannager updateWeather->" + areaId);
		final String url=areaId;
		ApiTask apiTask = new ApiTask(context, url, apiListener);
		AsyncTaskCompat.executeParallel(apiTask);
	}

	// public static void updateWeatherByLocation(@NonNull Context context,
	// @NonNull Location location, @NonNull ApiListener apiListener) {
	// location.get
	// ApiTask apiTask = new ApiTask(context,URL+ cityId, apiListener);
	// AsyncTaskCompat.executeParallel(apiTask);
	// }

	// private static SparseArray<Weather> CACHE_WEAHTER = new
	// SparseArray<Weather>();

	/**
	 *
	 * @param context
	 */
//	private static void saveWeater(@NonNull Context context, @NonNull Weather weather, @NonNull String weatherJson) {
//		if (context == null || weather == null || !weather.isOK()) {
//			return;
//		}
//		try {
//			final String key = weather.get().basic.id;
//			UiUtil.logDebug(TAG, "saveWeater->" + key);
//			MxxPreferenceUtil.setPrefString(context, key, weatherJson);
//			if (TextUtils.equals(key, getDefaultSelectedAreaId(context))) {
//				// WeatherWidgetProvider.sendUpdateAppWidget(context, weather);
//				UpdateManager.sendUpdateWeather(context, weather);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static HeWeather6 loadWeather(@NonNull Context context, @NonNull String areaId) {
//		if (context == null || TextUtils.isEmpty(areaId)) {
//			return null;
//		}
//		try {
//			String json = MxxPreferenceUtil.getPrefString(context, areaId, null);
//			if (TextUtils.isEmpty(json)) {
//				return null;
//			}
//			Weather weather = GSON.fromJson(json, Weather.class);
//			return weather;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}

	/**
	 * 是否需要更新Weather数据 1小时15分钟之内的return false; 传入null或者有问题的weather也会返回true
	 * 
	 * @param weather
	 * @return
	 */
	public static boolean isNeedUpdate(HeWeather6 weather) {
		if (!acceptWeather(weather)) {
			return true;
		}
		try {
			final String updateTime = weather.getHeWeather6().get(0).getUpdate().getLoc();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date updateDate = format.parse(updateTime);
			Date curDate = new Date();
			long interval = curDate.getTime() - updateDate.getTime();// 时间间隔 ms
			if ((interval >= 0) && (interval < 75 * 60 * 1000)) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 是否是今天2015-11-05 04:00 合法data格式： 2015-11-05 04:00 或者2015-11-05
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(String date) {
		if (TextUtils.isEmpty(date) || date.length() < 10) {// 2015-11-05
															// length=10
			return false;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String today = format.format(new Date());
			if (TextUtils.equals(today, date.substring(0, 10))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否是合法的Weather数据
	 * 
	 * @param weather
	 * @return
	 */
	public static boolean acceptWeather(HeWeather6 weather) {
		if (weather == null || !weather.getHeWeather6().get(0).getStatus().equals("ok")) {
			return false;
		}
		return true;
	}

	/**
	 * 把Weather转换为对应的BaseDrawer.Type
	 * 
	 * @param weather
	 * @return
	 */
	public static BaseDrawer.Type convertWeatherType(HeWeather6 weather) {
		if (weather == null || !weather.getHeWeather6().get(0).getStatus().equals("ok")) {
			return Type.DEFAULT;
		}
		final boolean isNight = isNight(weather);
		try {
			final int w = Integer.valueOf(weather.getHeWeather6().get(0).getNow().getCond_code());
			switch (w) {
			case 100:
				return isNight ? Type.CLEAR_N : Type.CLEAR_D;
			case 101:// 多云
			case 102:// 少云
			case 103:// 晴间多云
				return isNight ? Type.CLOUDY_N : Type.CLOUDY_D;
			case 104:// 阴
				return isNight ? Type.OVERCAST_N : Type.OVERCAST_D;
				// 200 - 213是风
			case 200:
			case 201:
			case 202:
			case 203:
			case 204:
			case 205:
			case 206:
			case 207:
			case 208:
			case 209:
			case 210:
			case 211:
			case 212:
			case 213:
				return isNight ? Type.WIND_N : Type.WIND_D;
			case 300:// 阵雨Shower Rain
			case 301:// 强阵雨 Heavy Shower Rain
			case 302:// 雷阵雨 Thundershower
			case 303:// 强雷阵雨 Heavy Thunderstorm
			case 304:// 雷阵雨伴有冰雹 Hail
			case 305:// 小雨 Light Rain
			case 306:// 中雨 Moderate Rain
			case 307:// 大雨 Heavy Rain
			case 308:// 极端降雨 Extreme Rain
			case 309:// 毛毛雨/细雨 Drizzle Rain
			case 310:// 暴雨 Storm
			case 311:// 大暴雨 Heavy Storm
			case 312:// 特大暴雨 Severe Storm
			case 313:// 冻雨 Freezing Rain
				return isNight ? Type.RAIN_N : Type.RAIN_D;
			case 400:// 小雪 Light Snow
			case 401:// 中雪 Moderate Snow
			case 402:// 大雪 Heavy Snow
			case 403:// 暴雪 Snowstorm
			case 407:// 阵雪 Snow Flurry
				return isNight ? Type.SNOW_N : Type.SNOW_D;
			case 404:// 雨夹雪 Sleet
			case 405:// 雨雪天气 Rain And Snow
			case 406:// 阵雨夹雪 Shower Snow
				return isNight ? Type.RAIN_SNOW_N : Type.RAIN_SNOW_D;
			case 500:// 薄雾
			case 501:// 雾
				return isNight ? Type.FOG_N : Type.FOG_D;
			case 502:// 霾
			case 504:// 浮尘
				return isNight ? Type.HAZE_N : Type.HAZE_D;
			case 503:// 扬沙
			case 506:// 火山灰
			case 507:// 沙尘暴
			case 508:// 强沙尘暴
				return isNight ? Type.SAND_N : Type.SAND_D;
			default:
				return isNight ? Type.UNKNOWN_N : Type.UNKNOWN_D;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isNight ? Type.UNKNOWN_N : Type.UNKNOWN_D;
	}

	/**
	 * 转换日期2015-11-05为今天、明天、昨天，或者是星期几
	 * 
	 * @param date
	 * @return
	 */
	public static String prettyDate(String date) {
		try {
			final String[] strs = date.split("-");
			final int year = Integer.valueOf(strs[0]);
			final int month = Integer.valueOf(strs[1]);
			final int day = Integer.valueOf(strs[2]);
			Calendar c = Calendar.getInstance();
			int curYear = c.get(Calendar.YEAR);
			int curMonth = c.get(Calendar.MONTH) + 1;// Java月份从0月开始
			int curDay = c.get(Calendar.DAY_OF_MONTH);
			if (curYear == year && curMonth == month) {
				if (curDay == day) {
					return "今天";
				} else if ((curDay + 1) == day) {
					return "明天";
				} else if ((curDay - 1) == day) {
					return "昨天";
				}
			}
			c.set(year, month - 1, day);
			// http://www.tuicool.com/articles/Avqauq
			// 一周第一天是否为星期天
			boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
			// 获取周几
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			// 若一周第一天为星期天，则-1
			if (isFirstSunday) {
				dayOfWeek = dayOfWeek - 1;
				if (dayOfWeek == 0) {
					dayOfWeek = 7;
				}
			}
			// 打印周几
			// System.out.println(weekDay);

			// 若当天为2014年10月13日（星期一），则打印输出：1
			// 若当天为2014年10月17日（星期五），则打印输出：5
			// 若当天为2014年10月19日（星期日），则打印输出：7
			switch (dayOfWeek) {
			case 1:
				return "周一";
			case 2:
				return "周二";
			case 3:
				return "周三";
			case 4:
				return "周四";
			case 5:
				return "周五";
			case 6:
				return "周六";
			case 7:
				return "周日";
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static boolean isNight(HeWeather6 weather) {
		if (weather == null || !weather.getHeWeather6().get(0).getStatus().equals("ok")) {
			return false;
		}
		// SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd HH mm ss");
		try {
			final Date date = new Date();
			String todaydate = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
			String todaydate1 = (new SimpleDateFormat("yyyy-M-d")).format(date);
			HeWeather6.HeWeather6Bean.DailyForecastBean todayForecast = null;
			for (HeWeather6.HeWeather6Bean.DailyForecastBean forecast : weather.getHeWeather6().get(0).getDaily_forecast()) {
				if (TextUtils.equals(todaydate, forecast.getDate()) || TextUtils.equals(todaydate1, forecast.getDate())) {
					todayForecast = forecast;
					break;
				}
			}
			if (todayForecast != null) {
				final int curTime = Integer.valueOf((new SimpleDateFormat("HHmm").format(date)));
				final int srTime = Integer.valueOf(todayForecast.getSr().replaceAll(":", ""));// 日出
				final int ssTime = Integer.valueOf(todayForecast.getSs().replaceAll(":", ""));// 日落
				if (curTime > srTime && curTime <= ssTime) {// 是白天
					return false;
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	static class ApiTask extends AsyncTask<Void, Void, HeWeather6> {

		private final Context context;
		private final String url;
		private final ApiListener listener;

		public ApiTask(Context context, String url, ApiListener listener) {
			super();
			this.context = context;
			this.url = url;
			this.listener = listener;
		}

		@Override
		protected HeWeather6 doInBackground(Void... params) {
			return updateWeatherFromInternet(context, url);
		}

		@Override
		protected void onPostExecute(HeWeather6 result) {
			super.onPostExecute(result);
			if (listener != null) {
				if (result != null) {
					listener.onReceiveWeather(result, true);
				} else {
					listener.onUpdateError();
					UiUtil.toastDebug(context, "更新失败");
				}
			}

		}

	}
	/**
	 * 网络获取weather 如果成功ok自动保存
	 * 
	 * @param context
	 * @param url
	 * @return
	 */
	static HeWeather6 updateWeatherFromInternet(Context context, final String url) {
		try {
			
			String json = "";
				json=httpDoPost(url);
				if (!TextUtils.isEmpty(json)) {
					HeWeather6 weather = GSON.fromJson(json, HeWeather6.class);
					Log.i("debug", "json: "+weather);
					if (acceptWeather(weather)) {
						//ApiManager.saveWeater(context, weather, json);
						return weather;
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static class AreaData implements Serializable {
		private static final long serialVersionUID = 9143853030679080418L;
		@SerializedName("list")
		@Expose
		public ArrayList<Area> list = new ArrayList<ApiManager.Area>();
		@SerializedName("create")
		@Expose
		public String create;

		public AreaData() {
			super();
		}

	}

	public static class Area implements Serializable {
		private static final long serialVersionUID = 7646903512215148839L;
		@SerializedName("city")
		@Expose
		public String city;

		public Area(String city) {
			this.city = city;
		}
		public Area() {
		}
		@Override
		public String toString() {
			return city;
		}
	}
	public static String httpDoPost(String s){
		Log.i("debug", "httpDoPost: "+s);
		String param = "key=e80f9ffa350b4bfa9a85276076eea817&location="+s;
		StringBuilder sb = new StringBuilder();
		InputStream    is=null;
		BufferedReader br=null;
		PrintWriter out=null;
		try {
			//接口地址
			String url = "https://free-api.heweather.com/s6/weather";
			URL uri= new URL(url);
			HttpURLConnection connection= (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(10000);
			connection.setRequestProperty("accept", "*/*");
			//发送参数
			connection.setDoOutput(true);
			out = new PrintWriter(connection.getOutputStream());
			out.print(param);
			out.flush();
			//接收结果
			is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String         line;
			//缓冲逐行读取
			while ( (line = br.readLine()) != null ) {
				sb.append(line);
			}
			return sb.toString();
		}catch ( Exception ignored ){}
		finally {
			//关闭流
			try {
				if(is!=null){
					is.close();
				}
				if(br!=null){
					br.close();
				}
				if (out!=null){
					out.close();
				}
			} catch (IOException e2) {}
		}
		return null;
	}

	public static String httpDoPostAre(String city){
		Log.i("httpDoPostAre", "httpDoPostAre: "+city);
		String param = "key=e80f9ffa350b4bfa9a85276076eea817&city="+city;
		StringBuilder sb = new StringBuilder();
		InputStream    is=null;
		BufferedReader br=null;
		PrintWriter out=null;
		try {
			Log.i("httpDoPostAre", "try: ");
			//接口地址
			String url = "https://api.heweather.com/v5/search";
			URL uri= new URL(url);
			HttpURLConnection connection= (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(10000);
			connection.setRequestProperty("accept", "*/*");
			//发送参数
			connection.setDoOutput(true);
			out = new PrintWriter(connection.getOutputStream());
			out.print(param);
			out.flush();
			//接收结果
			is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String         line;
			//缓冲逐行读取
			while ( (line = br.readLine()) != null ) {
				sb.append(line);
			}
			Log.i("httpDoPostAre", "while: "+sb.toString());
			return sb.toString();
		}catch ( Exception ignored ){
			ignored.printStackTrace();
		}
		finally {
			//关闭流
			try {
				if(is!=null){
					is.close();
				}
				if(br!=null){
					br.close();
				}
				if (out!=null){
					out.close();
				}
			} catch (IOException e2) {}
		}
		return null;
	}

}
