package com.amy.weather;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.amy.android.util.PreferenceUtil;
import com.amy.android.util.UiUtil;
import com.amy.dynamicweather.BaseDrawer.Type;
import com.amy.support.widget.LabelSpinner;
import com.amy.support.widget.SmoothSwitch;
import com.amy.weather.api.ApiManager;
import com.amy.weather.api.ApiManager.Area;
import com.amy.weather.api.entity.SeachCity;
import com.amy.weather.github.R;

public class SettingsFragment extends BaseFragment implements View.OnClickListener{
	private View mRootView;
	private TextView mGpsTextView,settings_manage_area;
	private ArrayList<Area> mSeachAreas, areas;
	private  PopupWindow popupWindow;
	private Button manage_areas_cancle_button,manage_areas_add,manage_areas_ok_button,
	manage_areas_return_button;
	ViewSwitcher manage_areas_viewswitcher;
	EditText manage_areas_search_edittext;
	ListView manage_areas_search_listview ,listView;
	ArrayAdapter<Area> arrayAdapterSelect;
	private Type type = Type.DEFAULT;
	private static final String BUNDLE_EXTRA_SELECTED_AREAS = "BUNDLE_EXTRA_SELECTED_AREAS";
	Gson gson;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 1:
					ArrayAdapter<Area> arrayAdapterseach=new ArrayAdapter(getContext(),R.layout.listitem_simple,mSeachAreas);
					manage_areas_search_listview.setAdapter(arrayAdapterseach);
					manage_areas_search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							if(!areas.contains(mSeachAreas.get(position))){
								areas.add(mSeachAreas.get(position));
								arrayAdapterSelect.notifyDataSetChanged();
								manage_areas_viewswitcher.showPrevious();
							}else{
								Toast.makeText(getContext(), "此城市已添加", Toast.LENGTH_SHORT).show();
							}
						}
					});
					break;
			}
		}
	};
	public static SettingsFragment makeInstance(@NonNull ArrayList<Area> selectedAreas) {
		SettingsFragment fragment = new SettingsFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLE_EXTRA_SELECTED_AREAS, selectedAreas);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gson=new Gson();
		final int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hourOfDay >= 7 && hourOfDay <= 18) {
			type = Type.UNKNOWN_D;
		} else {
			type = Type.UNKNOWN_N;
		}
		
	}
	
	public static void installShortcut(Context context, Intent shortcut, String label, Bitmap icon) {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcut);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, label);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
        intent.putExtra("duplicate", false);    
        context.sendBroadcast(intent);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.fragment_settings, null);
			SmoothSwitch switchNotification = (SmoothSwitch) mRootView.findViewById(R.id.settings_switch_notification);
			final LabelSpinner smallIconSpinner = (LabelSpinner) mRootView.findViewById(R.id.settings_spinner_smallicon);
			final LabelSpinner textColorSpinner = (LabelSpinner) mRootView.findViewById(R.id.settings_spinner_textcolor);
//			switchNotification.setChecked(WeatherNotificationService.Config.isShowNotification(getActivity()), false);
//			smallIconSpinner.setVisibility(switchNotification.isChecked() ? View.VISIBLE : View.GONE);
			textColorSpinner.setVisibility(switchNotification.isChecked() ? View.VISIBLE : View.GONE);
			switchNotification.setOnCheckedChangeListener(new SmoothSwitch.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					smallIconSpinner.setVisibility(isChecked ? View.VISIBLE : View.GONE);
					textColorSpinner.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//					WeatherNotificationService.Config.setShowNotification(getActivity(), isChecked);
//					WeatherNotificationService.startServiceCheckConfig(getActivity());
				}
			});
			
			
//			smallIconSpinner.setSelection(WeatherNotificationService.Config.getSmallIconType(smallIconSpinner.getContext()), false);
			smallIconSpinner.setSelection(0, false);
			smallIconSpinner.setOnSelectionChangedListener(new LabelSpinner.OnSelectionChangedListener() {
				@Override
				public void OnSelectionChanged(int position) {
//					WeatherNotificationService.Config.setSmallIconType(context, position);
//					WeatherNotificationService.startServiceCheckConfig(context);
				}
			});
//			textColorSpinner.setSelection(WeatherNotificationService.Config.getRemoteViewTextColor(context), false);
			textColorSpinner.setSelection(0, false);
			textColorSpinner.setOnSelectionChangedListener(new LabelSpinner.OnSelectionChangedListener() {
				@Override
				public void OnSelectionChanged(int position) {
//					WeatherNotificationService.Config.setRemoteViewTextColor(context, position);
//					WeatherNotificationService.startServiceCheckConfig(context);
				}
			});
		}
		return mRootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		notifyActivityUpdate();
		settings_manage_area= (TextView) mRootView.findViewById(R.id.settings_manage_area);
		settings_manage_area.setOnClickListener(this);
		mGpsTextView = (TextView) mRootView.findViewById(R.id.settings_gps_location);
		
	}


	

	@Override
	public String getTitle() {
		return "Weather_demo";
	}

	@Override
	public void onSelected() {
	}

	@Override
	public Type getDrawerType() {
		return type;
	}



	public  void showDeleDialog(final Context context, String city, final int position){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 设置参数
		builder.setTitle("确定删除"+city+"吗?")
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
										int which) {
						Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
								int which) {
				areas.remove(position);
				arrayAdapterSelect.notifyDataSetChanged();
				Toast.makeText(context, "已删除", Toast.LENGTH_SHORT).show();
			}
		});
		builder.create().show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.settings_manage_area:
				showAreaPopupWindow();
				break;
			case R.id.manage_areas_cancle_button:
			popupWindow.dismiss();
				break;
			case R.id.manage_areas_add:
				manage_areas_viewswitcher.showNext();
				break;
			case R.id.manage_areas_ok_button:
				String json=gson.toJson(areas);
				PreferenceUtil.setPrefString(getContext(),"Areas","area",json);
				Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
				popupWindow.dismiss();
				MainActivity mainActivity= (MainActivity) getActivity();
				mainActivity.loadAreaToViewPager(0);
				break;
			case R.id.manage_areas_return_button:
				manage_areas_viewswitcher.showPrevious();
				break;
		}
	}

	private void showAreaPopupWindow() {
		View view=LayoutInflater.from(getContext()).inflate(R.layout.dialog_manage_area,null,false);
		listView= (ListView) view.findViewById(R.id.listview);
		manage_areas_cancle_button= (Button) view.findViewById(R.id.manage_areas_cancle_button);
		manage_areas_add= (Button) view.findViewById(R.id.manage_areas_add);
		manage_areas_ok_button= (Button) view.findViewById(R.id.manage_areas_ok_button);
		manage_areas_return_button= (Button) view.findViewById(R.id.manage_areas_return_button);
		manage_areas_search_edittext= (EditText) view.findViewById(R.id.manage_areas_search_edittext);
		manage_areas_cancle_button.setOnClickListener(this);
		manage_areas_add.setOnClickListener(this);
		manage_areas_ok_button.setOnClickListener(this);
		manage_areas_return_button.setOnClickListener(this);
		manage_areas_search_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					if(!manage_areas_search_edittext.getText().toString().equals("")){
						new Thread(){
							@Override
							public void run() {
								super.run();
								String json=ApiManager.httpDoPostAre(manage_areas_search_edittext.getText().toString());
								SeachCity seachCity=gson.fromJson(json, SeachCity.class);
								if(seachCity.getHeWeather5().get(0).getStatus().equals("ok")){
									mSeachAreas=new ArrayList<Area>();
									mSeachAreas.add(new Area(seachCity.getHeWeather5().get(0).getBasic().getCity()));
									handler.sendEmptyMessage(1);
								}
							}
						}.start();
					}else{
						Toast.makeText(getContext(),"请输入城市",Toast.LENGTH_SHORT).show();
					}
				}
				return false;
			}
		});
		manage_areas_viewswitcher= (ViewSwitcher) view.findViewById(R.id.manage_areas_viewswitcher);
		manage_areas_search_listview= (ListView) view.findViewById(R.id.manage_areas_search_listview);
		 areas=ApiManager.loadSelectedArea(getContext());
		arrayAdapterSelect=new ArrayAdapter(getContext(),R.layout.listitem_simple,areas);
		listView.setAdapter(arrayAdapterSelect);
		popupWindow=new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,true);
		popupWindow.setOutsideTouchable(true);
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha =0.3f;
		getActivity().getWindow().setAttributes(lp);
		popupWindow.showAtLocation(mRootView, Gravity.CENTER,0, UiUtil.getStatusBarHeight());
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				// popupWindow隐藏时恢复屏幕正常透明度
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1.0f;
				getActivity().getWindow().setAttributes(lp);
			}
		});
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showDeleDialog(getContext(),areas.get(position).city,position);
			}
		});
	}
}










