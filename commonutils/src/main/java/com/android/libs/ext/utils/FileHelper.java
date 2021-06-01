package com.android.libs.ext.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件处理类。
 * @author E
 */
public class FileHelper {

	//统一的项目存储地址
	private static String UNIFORM_STORE_FILE_PATH = Environment.getExternalStorageDirectory() + "/.extSdk/";
	//图片存储地址
	private static String UNIFORM_IMAGE_FILE_PATH = UNIFORM_STORE_FILE_PATH + "images/";
	//语音存储地址
	private static String UNIFORM_AUDIO_FILE_PATH = UNIFORM_STORE_FILE_PATH + "audios/";
	//下载的APK地址
	private static String UNIFORM_APK_FILE_PATH = UNIFORM_STORE_FILE_PATH + "downApk/";
	//下载的视频地址
	private static String UNIFORM_VIDEO_FILE_PATH = UNIFORM_STORE_FILE_PATH + "video/";
	//其他文件地址
	private static String UNIFORM_DIR_FILE_PATH = UNIFORM_STORE_FILE_PATH + "file/";
	//日志文件地址
	private static String UNIFORM_LOG_FILE_PATH = UNIFORM_STORE_FILE_PATH + "logFile/";
	
	public static boolean fileExist(String filePath){
		if (CheckHelper.isNullOrEmpty(filePath)) {
			return false;
		}
		try {
			File file = new File(filePath);
			return file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断图片文件在本地是否已存在。
	 * @param imageFileName 图片文件名
	 * @return boolean
	 */
	public static boolean imageExist(String imageFileName){
		if (CheckHelper.isNullOrEmpty(imageFileName)) {
			return false;
		}
		File file = new File(getImageDirFile(), imageFileName);
		return file.exists();
	}
	
	/**
	 * 本地是否存有当前语音文件。
	 * @param audioName
	 * @return boolean
	 */
	public static boolean hasStoredAudio(String audioName){
		String currentAudioName = subStringPicName(audioName);
		File audioFile = new File(FileHelper.getAudioDirFile(), currentAudioName);
        if (audioFile.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据图片文件名得到本地的图片文件
	 * @param imageFileName 图片文件名
	 * @return 本地的图片文件
	 */
	public static File getImageFile(String imageFileName){
		File file = new File(getImageDirFile() , imageFileName);
		return file;
	}
	
	/**
	 * 清除所有的聊天信息。
	 */
	public static void clearAllCaches(){
		clearCacheFiles();
	}
	
	/**
	 * 清除本地缓存的所有文件内容，包括图片，语音等。
	 */
	public static void clearCacheFiles(){
		deleteImageDirFile();
		deleteAudioDirFile();
		deleteDownApkDirFile();
		deleteDirFile();
		deleteLogDirFile();
	}
	
	/**
	 * 删除UNIFROM_IMAGE_FILE_PATH目录下的所有图片文件及文件夹。
	 */
	public static void deleteImageDirFile(){
		deleteDirFiles(UNIFORM_IMAGE_FILE_PATH);
	}
	
	/**
	 * 删除UNIFROM_AUDIO_FILE_PATH目录下的所有语音文件及文件夹。
	 */
	public static void deleteAudioDirFile(){
		deleteDirFiles(UNIFORM_AUDIO_FILE_PATH);
	}
	
	/**
	 * 删除UNIFROM_APK_FILE_PATH目录下的所有APK文件及文件夹。
	 */
	public static void deleteDownApkDirFile(){
		deleteDirFiles(UNIFORM_APK_FILE_PATH);
	}
	
	/**
	 * 删除UNIFROM_APK_FILE_PATH目录下的所有APK文件及文件夹。
	 */
	public static void deleteDirFile(){
		deleteDirFiles(UNIFORM_DIR_FILE_PATH);
	}

	/**
	 * 删除UNIFORM_LOG_FILE_PATH目录下的所有日志文件及文件夹。
	 */
	public static void deleteLogDirFile(){
		deleteDirFiles(UNIFORM_LOG_FILE_PATH);
	}

	public static void deleteDirFiles(String path){
		File dirFile = new File(path);
		if (dirFile.isDirectory()) {
			File[] files = dirFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				file.delete();
			}
		}
	}
	
	/**
	 * 取得统一的存储图片的目录文件夹。
	 * @return 存储图片的文件夹
	 */
	public static File getImageDirFile(){
		return makeFileDirs(UNIFORM_IMAGE_FILE_PATH);
	}
	
	/**
	 * 取得统一存储语音的目录文件夹。
	 * @return 存储录音的文件夹
	 */
	public static File getAudioDirFile(){
		return makeFileDirs(UNIFORM_AUDIO_FILE_PATH);
	}
	
	/**
	 * 取得统一存储视频的目录文件夹。
	 * @return 存储录音的文件夹
	 */
	public static File getVideoDirFile(){
		return makeFileDirs(UNIFORM_VIDEO_FILE_PATH);
	}	
	
	/**
	 * 取得统一目录文件夹。
	 * @return 存储录音的文件夹
	 */
	public static File getDirFile(){
		return makeFileDirs(UNIFORM_DIR_FILE_PATH);
	}

	/**
	 * 取得统一日志目录文件夹。
	 * @return 存储日志的文件夹
	 */
	public static File getLogDirFile(){
		return makeFileDirs(UNIFORM_LOG_FILE_PATH);
	}

	/**
	 * 在dirFile目录下创建fileName命名的文件。
	 * @param dirFile 目录文件
	 * @param fileName 文件名
	 * @return 创建的文件
	 */
	public static File createFile(File dirFile , String fileName){
		File file = new File(dirFile, fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
		}
		return file;
	}
	
	/**
	 * 在dirFilePath路径下创建fileName命名的文件。
	 * @param dirFilePath 目录文件地址
	 * @param fileName 文件名
	 * @return 创建的文件
	 */
	public static File createFile(String dirFilePath , String fileName){
		File dirFile = makeFileDirs(dirFilePath);
		return createFile(dirFile, fileName);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String generateSoundFileName(){
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String add = (currentTime+"").substring(0, 3);
		return dateFormat.format(date) + add;
	}
	
	/**
	 * 取得统一的存储下载的APK的目录文件夹。
	 * @return 存储图片的文件夹
	 */
	public static File getDownApkDirFile(){
		return makeFileDirs(UNIFORM_APK_FILE_PATH);
	}
	
	private static File makeFileDirs(String path){
		try {
			File dirFile = new File(path);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			return dirFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 删除聊天图片文件夹里对应的图片。
	 * @param imageFileName 要删除的图片名
	 */
	public static void deleteImageFile(String imageFileName){
		File dirFile = new File(UNIFORM_IMAGE_FILE_PATH);
		if (dirFile.isDirectory()) {
			File[] files = dirFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				String fileName = file.getName();
				if (imageFileName.contains(fileName) ||fileName.contains(imageFileName)) {
					Log.e("文件名", fileName);
					file.delete();
					return;
				}
			}
		}
	}
	
	/**
	 * 通过图片路径名取得图片名。
	 * @param picPath 图片路径
	 * @return 图片名
	 */
	private static String subStringPicName(String picPath){
		int index = picPath.lastIndexOf("/");
		picPath = picPath.substring(index+1);
		return picPath;
	}

}
