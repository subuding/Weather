package com.amy.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class MxxNetworkUtil {
	
	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 *            上下文
	 * @return true 表示有网络连接 false表示没有可用网络连接
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			Network[] networks = connectivity.getAllNetworks();
			NetworkInfo networkInfo;
			for (Network mNetwork : networks) {
				networkInfo = connectivity.getNetworkInfo(mNetwork);
				if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
					return true;
				}
			}
		}else{
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (NetworkInfo anInfo : info) {
						if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
		}
		return false;
	}
	
	/**
	 * 王斌增加，用于判断是否是wifi 网络
	 * 
	 * @param context
	 * @return 是否WIFI网络
	 */
	public static boolean isWifiConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

}
