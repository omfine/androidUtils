package com.android.libs.ext.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * To wrap Toast to make it simple.
 * @author E
 */
public class ToastHelper {

	public static void makeText(Context context, CharSequence text){
		if (null == context || null == text) {
			return;
		}
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public static void makeText(Context context, int resId){
		if (null == context) {
			return;
		}
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}
}
