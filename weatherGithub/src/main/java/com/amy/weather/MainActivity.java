package com.amy.weather;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.amy.android.util.PreferenceUtil;
import com.amy.android.util.TimingLogger;
import com.amy.android.util.UiUtil;
import com.amy.dynamicweather.DynamicWeatherView;
import com.amy.mxxedgeeffect.widget.MxxFragmentPagerAdapter;
import com.amy.mxxedgeeffect.widget.MxxViewPager;
import com.amy.weather.api.ApiManager;
import com.amy.weather.api.ApiManager.Area;
import com.amy.weather.github.R;

public class MainActivity extends FragmentActivity {

	public static Typeface typeface;

	public static Typeface getTypeface(Context context) {
		return typeface;
	}
	public  BaseFragment[] fragments;
	private DynamicWeatherView weatherView;
	private MxxViewPager viewPager;
	private String area="[{\"city\":\"北京\"},{\"city\":\"上海\"},{\"city\":\"广州\"}]";
	public MxxViewPager getViewPager() {
		return viewPager;
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		//UiUtil.toastDebug(this, "onNewIntent->" + intent.toString());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(PreferenceUtil.getPrefString(this,"Areas","area",null)==null){
			PreferenceUtil.setPrefString(this,"Areas","area",area);
		}
		TimingLogger logger = new TimingLogger("MainActivity.onCreate");
//		typeface = Typeface.createFromAsset(getAssets(), "fonts/clock text typeface.ttf");
		if(typeface == null){
			typeface = Typeface.createFromAsset(getAssets(), "fonts/font2.ttf");
		}
		logger.addSplit("Typeface.createFromAsset");
		setContentView(R.layout.activity_main);
		logger.addSplit("setContentView");
		viewPager = (MxxViewPager) findViewById(R.id.main_viewpager);
		if (Build.VERSION.SDK_INT >= 19) {
			viewPager.setPadding(0, UiUtil.getStatusBarHeight(), 0, 0);
		}
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
		alphaAnimation.setDuration(260);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				getWindow().setBackgroundDrawable(//getResources().getDrawable(R.drawable.window_frame_color));
				 new ColorDrawable(Color.BLACK));
//				WeatherNotificationService.startServiceWithNothing(MainActivity.this);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {}
		});
		viewPager.setAnimation(alphaAnimation);
//		viewPager.setPageMargin(UiUtil.dp2px(this, 4));
		logger.addSplit("findViewPager");
		weatherView = (DynamicWeatherView) findViewById(R.id.main_dynamicweatherview);
		logger.addSplit("findWeatherView");
		loadAreaToViewPager(1);
		logger.addSplit("loadAreaToViewPager");
		logger.dumpToLog();
		
	}
	
	public void loadAreaToViewPager(int item){
		final ArrayList<Area> selectedAreas = ApiManager.loadSelectedArea(this);
		fragments = new BaseFragment[selectedAreas.size() + 1] ;
//		viewPager.setOffscreenPageLimit(fragments.length);
		for(int i = 0; i<selectedAreas.size();i++){
			final Area area = selectedAreas.get(i);
			fragments[i+1] = WeatherFragment.makeInstance(area, ApiManager.loadWeather(this, area.city));
		}
		fragments[0] = SettingsFragment.makeInstance(selectedAreas);
		viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments));
		viewPager.setOnPageChangeListener(new MxxViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int state) {
				super.onPageScrollStateChanged(state);
			}
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				weatherView.setDrawerType(((SimpleFragmentPagerAdapter) viewPager.getAdapter()).getItem(
						position).getDrawerType());
			}
		});
		viewPager.setCurrentItem(item, false);
	}

	public void updateCurDrawerType() {
		weatherView.setDrawerType(((SimpleFragmentPagerAdapter) viewPager.getAdapter()).getItem(
				viewPager.getCurrentItem()).getDrawerType());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		weatherView.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		weatherView.onPause();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		weatherView.onDestroy();
	}

	public static class SimpleFragmentPagerAdapter extends MxxFragmentPagerAdapter {

		private BaseFragment[] fragments;

		public SimpleFragmentPagerAdapter(FragmentManager fragmentManager, BaseFragment[] fragments) {
			super(fragmentManager);
			this.fragments = fragments;
		}
		

		@Override
		public BaseFragment getItem(int position) {
			BaseFragment fragment = fragments[position];
			fragment.setRetainInstance(true);
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return fragments[position].getTitle();
		}

		@Override
		public int getCount() {
			return fragments.length;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(((Fragment) object).getView());
			super.destroyItem(container, position, object);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
