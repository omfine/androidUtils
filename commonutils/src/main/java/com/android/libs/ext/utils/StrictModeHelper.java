package com.android.libs.ext.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
/**
 * 线程处理类。
 * @author E
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StrictModeHelper {

	
	public static void initStrictMode(){
		int  currentApiVersion= Build.VERSION.SDK_INT;
		if (currentApiVersion > 10) {
			//主要用来处理线程，针对4.0以上
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() 
//			.detectDiskReads().detectDiskWrites().detectNetwork()
//			.penaltyLog().build());
//			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//			.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//			.penaltyLog().penaltyDeath().build());
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

}
