package com.android.libs.ext.db;

import android.content.ContentValues;

public class DbValuesHelper {

	public static ContentValues getUpdateValues(String[] keys , Object[] new_values){
		ContentValues values = new ContentValues();
		int key_length = keys.length;
		for (int i = 0; i < key_length; i++) {
			Object new_value = new_values[i];
			if (new_value instanceof String) {
				values.put(keys[i], String.valueOf(new_value));
			}else if (new_value instanceof Short) {
				values.put(keys[i], (Short)new_value);
			}else if (new_value instanceof Long) {
				values.put(keys[i], (Long)new_value);
			}else if (new_value instanceof Integer) {
				values.put(keys[i], (Integer)new_value);
			}else if (new_value instanceof Float) {
				values.put(keys[i], (Float)new_value);
			}else if (new_value instanceof Double) {
				values.put(keys[i], (Double)new_value);
			}else if (new_value instanceof Byte[]) {
				values.put(keys[i], (byte[])new_value);
			}else if (new_value instanceof Byte) {
				values.put(keys[i], (Byte)new_value);
			}else if (new_value instanceof Boolean) {
				values.put(keys[i], (Boolean)new_value);
			}
		}
		return values;
	}
}
