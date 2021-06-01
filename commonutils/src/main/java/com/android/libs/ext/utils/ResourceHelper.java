package com.android.libs.ext.utils;

import android.content.Context;

/**
 * 资源寻找方式辅助类。能过该类可以通过资源名来找到资源ID。
 * @author E
 */
public class ResourceHelper {

	public final static String ID = "id";
	public final static String LAYOUT = "layout";
	public final static String STRING = "string";
	public final static String ANIM = "anim";
	public final static String STYLE = "style";
	public final static String DRAWABLE = "drawable";
	public final static String DIMEN = "dimen";
	public final static String STYLEABLE = "styleable";
	public final static String COLOR = "color";
	public final static String ARRAY = "array";
	public final static String RAW = "raw";
	
	/**
	 * 通过组件名获取ViewID。
	 * @param context 上下文环境
	 * @param name 组件名
	 * @return ViewID
	 */
	public static int findViewId(Context context, String name){
		return getIdByName(context,ID,name);
	}
	
	/**
	 * 通过资源名获取资源ID。
	 * @param context 上下文环境
	 * @param name 资源名
	 * @return 资源ID
	 */
	public static int findString(Context context, String name){
		return getIdByName(context,STRING,name);
	}
	
	/**
	 * 通过资源名获取资源ID。
	 * @param context 上下文环境
	 * @param name 资源名
	 * @return 资源ID
	 */
	public static int findRaw(Context context, String name){
		return getIdByName(context,RAW,name);
	}
	
	/**
	 * 通过布局名获取LayoutID。
	 * @param context 上下文环境
	 * @param name 布局名
	 * @return LayoutID
	 */
	public static int findLayout(Context context, String name){
		return getIdByName(context,LAYOUT,name);
	}
	
	/**
	 * 通过动画资源名获取动画资源ID。
	 * @param context 上下文环境
	 * @param name 动画资源名
	 * @return 动画资源ID
	 */
	public static int findAnim(Context context, String name){
		return getIdByName(context,ANIM,name);
	}
	
	public static int findStyle(Context context, String name){
		return getIdByName(context,STYLE,name);
	}
	
	public static int findDrawable(Context context, String name){
		return getIdByName(context,DRAWABLE,name);
	}
	
	public static int findDimen(Context context, String name){
		return getIdByName(context,DIMEN,name);
	}
	
	public static int findStyleable(Context context, String name){
		return getIdByName(context,STYLEABLE,name);
	}
	
	public static int findColor(Context context, String name){
		return getIdByName(context,COLOR,name);
	}
	
	public static int findArray(Context context, String name){
		return getIdByName(context,ARRAY,name);
	}
	
	public static int[] findStyleables(Context context, String name){
		return getIdsByName(context,STYLEABLE,name);
	}

	@SuppressWarnings("rawtypes")
	private static int getIdByName(Context context, String className, String name) {
		String packageName = context.getPackageName();
		Class r = null;
		int id = 0;
		try {
			r = Class.forName(packageName + ".R");
			Class[] classes = r.getClasses();
			Class desireClass = null;
			for (int i = 0; i < classes.length; ++i) {
				if (classes[i].getName().split("\\$")[1].equals(className)) {
					desireClass = classes[i];
					break;
				}
			}
			if (null != desireClass)
				id = desireClass.getField(name).getInt(desireClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@SuppressWarnings("rawtypes")
	private static int[] getIdsByName(Context context, String className, String name) {
	    String packageName = context.getPackageName();
	    Class r = null;
	    int[] ids = null;
	    try {
	      r = Class.forName(packageName + ".R");

	      Class[] classes = r.getClasses();
	      Class desireClass = null;

	      for (int i = 0; i < classes.length; ++i) {
	        if (classes[i].getName().split("\\$")[1].equals(className)) {
	          desireClass = classes[i];
	          break;
	        }
	      }

	      if ((desireClass != null) && (desireClass.getField(name).get(desireClass) != null) && (desireClass.getField(name).get(desireClass).getClass().isArray()))
	        ids = (int[])desireClass.getField(name).get(desireClass);
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    } catch (IllegalArgumentException e) {
	      e.printStackTrace();
	    } catch (SecurityException e) {
	      e.printStackTrace();
	    } catch (IllegalAccessException e) {
	      e.printStackTrace();
	    } catch (NoSuchFieldException e) {
	      e.printStackTrace();
	    }
	    return ids;
	  }
}
