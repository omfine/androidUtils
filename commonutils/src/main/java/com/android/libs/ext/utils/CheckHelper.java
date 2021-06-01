package com.android.libs.ext.utils;

import android.text.TextUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查类。
 * @author E
 */
public class CheckHelper {


	/**
	 * 检查是否有装SD卡。
	 * @return boolean boolean
	 */
	public static boolean sdCardExist() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 检查文本是否为空或是为Null。
	 * @param text 要检查的文本
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String text){
		if (null == text) {
			return true;
		}
	    text = text.trim();
		boolean isNullOrEmpty = TextUtils.isEmpty(text);
		boolean isEquelsNull = text.equalsIgnoreCase("null");
		return isNullOrEmpty || isEquelsNull;
	}
	
	/**
	 * 检查一个是集合是否是空。
	 * @param list 要检查的集合
	 * @return boolean
	 */
	public static boolean listIsEmpty(List<?> list){
		return null == list || list.isEmpty() || 0 == list.size();
	}
	
	/**
	 * 判断图片的网络地址是否格式正确。
	 * @param imageUrl 图片的网络地址
	 * @return boolean
	 */
	public static boolean isRightFormatNetImageUrl(String imageUrl){
		if (isNullOrEmpty(imageUrl)) {
			return false;
		}
		if (!imageUrl.startsWith("http")) {
			return false;
		}
		if (imageUrl.endsWith(".jpg") 
				|| imageUrl.endsWith(".jpeg")
				|| imageUrl.endsWith(".png")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 简单判断是否是图片名。
	 * @param imageName 图片名
	 * @return boolean
	 */
	public static boolean isRightImageName(String imageName){
		if (isNullOrEmpty(imageName)) {
			return false;
		}
		if (imageName.endsWith(".jpg") 
				|| imageName.endsWith(".jpeg")
				|| imageName.endsWith(".png")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查手机号码格式是否正确 。
	 * @param tel 手机号码
	 * @return boolean boolean
	 */
	public static boolean isRightFomatPhone(String tel){
		String regexp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(tel);
		return m.matches();
	}
	
	/**
	 * 验证邮箱格式
	 * @param email 要检验的邮箱
	 * @return boolean boolean
	 */
	public static boolean verifyEmail(String email) {
//		String regExp = "^([a-zA-Z0-9.]*[-_]?[a-zA-Z0-9.]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		String regExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//		String regExp = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * 是否是正确的网络地址。
	 * @param url 网络地址
	 * @return boolean
	 */
	public static boolean isValidUrl(String url){
		if (isNullOrEmpty(url)) {
			return false;
		}
		return url.startsWith("http://") || url.startsWith("https://");
	}
	
	
}
