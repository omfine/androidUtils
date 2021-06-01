package com.android.libs.ext.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 网络状态。
 * @author E
 */
public class NetStatus {

    public static final int NONE = 0;
    public static final int WIFI = 1;
    public static final int MOBILE = 2;
	
	/**
	 * 检查当前网络连接状态。
	 * @param context 上下文环境
	 * @return true or false
	 */
	public static boolean checkNetWorkStatus(Context context) {
		//先判断WIFI是否是假连接(未经精度测试)
		boolean isWifiEndabled = WifiStatus.getInstance(context).isWifiEnabled();
		if (isWifiEndabled) {
			int linkSpeed = WifiStatus.getInstance(context).getLinkSpeed();
//			return -1 != linkSpeed;
			boolean isWifiConnected = (-1 != linkSpeed);
			if (isWifiConnected) {
				return true;
			}
		}
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		boolean status = null != netInfo && netInfo.isConnected();
		return status;
	}
	
	/**
	 * 返回网络状态。WIFI, 3G
	 * @param context 上下文环境
	 * @return 网络状态码
	 */
    public static int getNetworkStatus(Context context){
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Wifi
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(state == State.CONNECTED||state == State.CONNECTING){
            return WIFI;
        }
        //3G
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null == netInfo) {
        	return NONE;
		}else {
			state = netInfo.getState();
		}
        if(state == State.CONNECTED||state == State.CONNECTING){
            return MOBILE;
        }
        return NONE;
    }

}
