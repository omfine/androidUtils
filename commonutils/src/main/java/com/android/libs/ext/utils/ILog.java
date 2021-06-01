package com.android.libs.ext.utils;

import android.util.Log;
/**
 * Log类封装。
 * @author E
 */
public class ILog {

	private static final boolean DEBUG = true;
	private static final String TAG = "ilog";
	
	public static void e(String tag, String msg){
		if (DEBUG) {
			int msgLength = msg.length();
			if (msgLength < 1500){
				Log.e(tag, msg);

				return;
			}


			int i = 0;
			while (msgLength > 1500){
				int start = i*1500;
				int end = start + 1500;
				String subMsg = msg.substring(start , end);

				Log.e(tag, subMsg);

				i ++;
				msgLength = msgLength - 1500;

				if (msgLength > 0 && msgLength <= 1500){
					Log.e(tag, msg.substring(end));
				}
			}
		}
	}
	
	public static void e(String msg){
		e(TAG , msg);
	}

	public static void i(String tag, String msg){
		if (DEBUG) {
			Log.i(tag, msg);
		}
	}
	
	public static void i(String msg){
		if (DEBUG) {
			Log.i(TAG, msg);
		}
	}
	
	public static void d(String tag, String msg){
		if (DEBUG) {
			Log.d(tag, msg);
		}
	}
	
	public static void d(String msg){
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}
	
	public static void w(String tag, String msg){
		if (DEBUG) {
			Log.w(tag, msg);
		}
	}
	
	public static void w(String msg){
		if (DEBUG) {
			Log.w(TAG, msg);
		}
	}
	
	public static void v(String tag, String msg){
		if (DEBUG) {
			Log.v(tag, msg);
		}
	}
	
	public static void v(String msg){
		if (DEBUG) {
			Log.v(TAG, msg);
		}
	}

}
