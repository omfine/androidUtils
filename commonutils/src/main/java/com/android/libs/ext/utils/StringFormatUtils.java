package com.android.libs.ext.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * 字符串的功能类。
 * @author E
 */
public class StringFormatUtils {

	/**
	 * 将1万以上的数字简化显示
	 * @param numString
	 * @return 简化显示的数字
	 */
	public static String formatNum2Wan(String numString){
		if (CheckHelper.isNullOrEmpty(numString)) {
			return "0";
		}
		int num = Integer.valueOf(numString);
		if (num < 10000) {
			return numString;
		}
		Double db = Double.parseDouble(numString)/10000;
		//四舍五入，保留一位小数
		BigDecimal bigDecimal = new BigDecimal(db);
		bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
		db = bigDecimal.doubleValue();
		String result = db + "万";
		return result;
	}
	
	/**
	 * 转换格式为1,234,567
	 * @param numText
	 * @return 转换后的格式 
	 */
	public static String formatNumString(String numText){
		if (CheckHelper.isNullOrEmpty(numText)) {
			return "0";
		}
		int length = numText.length();
		if (length <= 3) {
			return numText;
		}
		ArrayList<String> list = new ArrayList<String>();
		while (numText.length() >= 3) {
			int txtLength = numText.length();
			String result = numText.substring(txtLength - 3);
			numText = numText.substring(0, txtLength - 3);
			list.add(0,result);
		}
		list.add(0,numText);
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String text = list.get(i);
			if (!"".equals(text)) {
				builder.append(text);
				if (i < list.size()-1) {
					builder.append(",");
				}
			}
		}
		return builder.toString();
	}
	
	public static String[] parseTextToArray(String text){
		if (CheckHelper.isNullOrEmpty(text)) {
			return null;
		}
		String[] result = null;
		if (text.contains(",")) {
			result = text.split(",");
		}else {
			result = new String[]{text};
		}
		return result;
	}
	
	public static ArrayList<String> parseTextToList(String text){
		ArrayList<String> list = new ArrayList<String>();
		String[] strings = parseTextToArray(text);
		if (null == strings) {
			return list;
		}
		for (String str : strings) {
			list.add(str);
		}
		return list;
	}	
	
	public static ArrayList<String> array2List(String... text){
		ArrayList<String> list = new ArrayList<String>();
		int length = text.length;
		for (int i = 0; i < length; i++) {
			list.add(text[i]);
		}
		return list;
	}
	
	/**
	 * 全半角转换。
	 * @param text 原始字符
	 * @return 转换后的字符
	 */
	public static String ToDBC(String text) {
		if (CheckHelper.isNullOrEmpty(text)) {
			return "";
		}
	   char[] c = text.toCharArray();
	   for (int i = 0; i< c.length; i++) {
	       if (c[i] == 12288) {
	         c[i] = (char) 32;
	         continue;
	       }if (c[i]> 65280&& c[i]< 65375)
	          c[i] = (char) (c[i] - 65248);
	       }
	   return new String(c);
	}
	
	public static String trimLineBreak(String text){
		if (CheckHelper.isNullOrEmpty(text)) {
			return "";
		}
		return text = text.replaceAll("\r|\n", "");
	}
	
	public static String formatText(String text){
	if (CheckHelper.isNullOrEmpty(text)) {
		text = "";
		}else {
			text = ToDBC(text).replaceAll("<br>", "\n")
					.replaceAll("<br/>", "\n")
					.replaceAll("<br />", "\n")
					;
		}
		return text;
	}
	
	public static String trimAll(String text){
		if (CheckHelper.isNullOrEmpty(text)) {
			return "";
		}
		text = ToDBC(text);
		text = trimLineBreak(text);
		text = formatText(text);
		return text;
	}

	public static String trimLastZero(String text){
		int index = text.indexOf(".");
		if (-1 == index){
			return text;
		}
		if (text.endsWith("00")){
			text = text.substring(0 , text.length() - 2);
		}
		if (text.endsWith("0")){
			text = text.substring(0 , text.length() - 1);
		}
		if (text.endsWith(".")){
			text = text.substring(0 , text.length() - 1);
		}
		return text;
	}
	
}
