package com.android.libs.ext.http;

import android.content.Context;
import android.net.wifi.WifiManager;

public class WifiStatus {

	private static WifiStatus wifiStatus = null;
	private static WifiManager wifiManager = null;
	
	public static WifiStatus getInstance(Context context){
		if (null == wifiStatus) {
			wifiStatus = new WifiStatus();
		}
		if (null == wifiManager) {
			wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		}
		return wifiStatus;
	}
	
	public boolean isWifiEnabled(){
		return wifiManager.isWifiEnabled();
	}
	
	public int getLinkSpeed(){
		int linkSpeed = wifiManager.getConnectionInfo().getLinkSpeed();
		return linkSpeed;
	}

}
