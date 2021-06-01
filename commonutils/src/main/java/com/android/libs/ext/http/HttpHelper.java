package com.android.libs.ext.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import java.util.HashMap;

/**
 * 网络访问类，主要用来轮询。
 * @author E
 */
public class HttpHelper {
	
	public static final int RESULT_OK = BaseHttpHelper.RESULT_OK;
	public static final int NET_ERROR_CODE = BaseHttpHelper.NET_ERROR_CODE;
	public static final String NET_ERROR_STRING = BaseHttpHelper.NET_ERROR_STRING;
	
	/**
	 * Send request to server (Basic method).
	 * @param urls 地址数组
	 * @param params params to send
	 * @return result returned
	 * @throws Exception exception caused
	 */
	public static String sendRequest(String[] urls , HashMap<String, Object> params) throws Exception {
		return BaseHttpHelper.getInstance().sendRequest(params ,urls);
	}
	
	/**
	 * Send request to server.
	 * @param context
	 * @param urls 地址数组
	 * @param params params to send
	 * @return returned
	 * @throws Exception exception caused
	 */
	public static String sendRequest(Context context , String[] urls , HashMap<String, Object> params) throws Exception {
		return BaseHttpHelper.getInstance().sendRequest(context,params ,urls);
	}	
	
	/**
	 * 异步发送请求到服务器,通过Handler返回请求结果，网络异常等。
	 * @param urls 地址数组
	 * @param params 传送的参数
	 * @param handler 返回的请求结果
	 */
	public static void sendRequest(Context context , String[] urls , HashMap<String, Object> params , Handler handler){
		sendRequest(context ,urls, params, handler, -1);
	}
	
	/**
	 * 异步发送请求到服务器,通过Handler返回请求结果，网络异常等。
	 * @param urls 地址数组
	 * @param params 传送的参数
	 * @param handler 返回的请求结果
	 * @param resultWhats Handler在返回结果是的WHATS, 如果是1,则用默认的IWYWhats.REQEST_RESULT还回结果,否则用传入的WHATS
	 */
	public static void sendRequest(Context context , String[] urls , HashMap<String, Object> params , Handler handler, int resultWhats){
		BaseHttpHelper.getInstance().sendRequest(context,params, handler, resultWhats ,urls);
	}
	
	/**
	 * 异步发送请求到服务器,通过Handler返回请求结果，网络异常等。
	 * @param urls 地址数组
	 * @param params 传送的参数
	 * @param handler 返回的请求结果
	 * @param msg Handler在返回结果是的WHATS, 如果是1,则用默认的IWYWhats.REQEST_RESULT还回结果,否则用传入的WHATS
	 */
	public static void sendRequest(Context context , String[] urls , HashMap<String, Object> params , Handler handler, Message msg){
		BaseHttpHelper.getInstance().sendRequest(context, params, handler, msg ,urls);
	}
}
