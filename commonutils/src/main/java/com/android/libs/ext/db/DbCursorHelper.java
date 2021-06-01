package com.android.libs.ext.db;

import android.database.Cursor;

public class DbCursorHelper {

	public static String getString(Cursor cursor , String columnName){
		return cursor.getString(cursor.getColumnIndex(columnName));
	}
	
	public static int getInt(Cursor cursor , String columnName){
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}
	
	public static long getLong(Cursor cursor , String columnName){
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}
	
	public static double getDouble(Cursor cursor , String columnName){
		return cursor.getDouble(cursor.getColumnIndex(columnName));
	}	
	
	public static float getFloat(Cursor cursor , String columnName){
		return cursor.getFloat(cursor.getColumnIndex(columnName));
	}

	public static int getCount(Cursor cursor){
		return cursor.getCount();
	}

	public static int getColumnCount(Cursor cursor){
		return cursor.getColumnCount();
	}
}
