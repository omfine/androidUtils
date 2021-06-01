package com.android.libs.ext.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * Activity的集合类。
 * @author E
 */
@SuppressLint("UseSparseArrays")
public class ActivityStack {
	
	private static ActivityStack activtiStack = null;
	
	public static ActivityStack getInstance(){
		if (null == activtiStack) {
			activtiStack = new ActivityStack();
		}
		return activtiStack;
	}
	
	private HashMap<Integer, Stack<Activity>> activityMap = new HashMap<Integer, Stack<Activity>>();
	
	/**
	 * 将ACTIVITY保存到栈中。
	 * @param flag 标识
	 * @param activity ACTIVITY
	 */
	public void add(int flag , Activity activity){
		boolean exist = activityMap.containsKey(flag);
		if (exist) {
			activityMap.get(flag).add(activity);
		}else {
			Stack<Activity> stack = new Stack<Activity>();
			stack.add(activity);
			activityMap.put(flag, stack);
		}
	}
	
	/**
	 * 关闭某一标识的ACTIVITY。
	 * @param flag 标识
	 */
	public void clear(int flag){
		boolean exist = activityMap.containsKey(flag);
		if (exist) {
			Stack<Activity> activities = activityMap.get(flag);
			for (Activity activity : activities) {
				activity.finish();
//                activities.remove(activity);

				Log.e("ilog" , "清除的类: " + activity.getLocalClassName());
			}
		}
	}
	
	/**
	 * 关闭所有的ACTIVITY。
	 */
	public void clearAll(){
		Iterator<Entry<Integer, Stack<Activity>>> iterator = activityMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Stack<Activity>> entry = iterator.next();
			Stack<Activity> activities = entry.getValue();
			for (Activity activity : activities) {
				activity.finish();
			}
		}
	}
	
	public void remove(int flag , Activity activity){
		boolean exist = activityMap.containsKey(flag);
		if (exist) {
			activityMap.get(flag).remove(activity);
		}
	}

}
