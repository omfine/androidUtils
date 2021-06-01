package com.android.libs.ext.utils;

import android.os.Environment;
import android.os.StatFs;

/**
 * 存储空间计算的辅助类。
 * @author E
 */
public class StorageHelper {
	
	/**
	 * 内部存储可用容量。
	 * @return 内部存储可用容量
	 */
	@SuppressWarnings("deprecation")
	public static long getInternalAvailableStorage(){
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long bytesAvailable = (long)stat.getFreeBlocks() * (long)stat.getBlockSize();
		long megAvailable = bytesAvailable / 1048576;
		return megAvailable;
	}
	
	/**
	 * 内部存储总容量。
	 * @return 内部存储总容量
	 */
	@SuppressWarnings("deprecation")
	public static long getTotalInternalStorage(){
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
		long megAvailable = bytesAvailable / 1048576;
		return megAvailable;
	}
	
	/**
	 * 外部存储可用容量。
	 * @return 外部存储可用容量
	 */
	public static long getExternalAvailableStorage(){
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long bytesAvailable = (long)stat.getBlockSizeLong() * (long)stat.getAvailableBlocksLong();
		long megAvailable = bytesAvailable / 1048576;
		return megAvailable;
	}
	
	/**
	 * 外部存储总容量。
	 * @return 外部存储总容量
	 */
	@SuppressWarnings("deprecation")
	public static long getTotalExternalStorage(){
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
		long megAvailable = bytesAvailable / 1048576;
		return megAvailable;
	}

}
