package com.android.libs.ext.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

/**
 * 基本的网络请求的接口。
 * @author E
 */
public interface BaseHttp {

	/**
	 * 向服务器发送请求，并返回数据。
	 * @param urls 服务器地址,多个地址用于轮询
	 * @param params 传递的参数KEY-VALUE的形式
	 * @return 服务器的返回值
	 * @throws Exception 请求异常（IOException...）
	 */
	String sendRequest(HashMap<String, Object> params, String... urls) throws Exception;
	
	/**
	 * 向服务器发送请求，并返回数据。
	 * @param context 上下文环境
	 * @param urls 服务器地址,多个地址用于轮询
	 * @param params 传递的参数KEY-VALUE的形式
	 * @return 服务器的返回值
	 * @throws Exception 请求异常（IOException...）
	 */
	String sendRequest(Context context, HashMap<String, Object> params, String... urls) throws Exception;
	
	/**
	 * 向服务器发送请求，并将数据放在Handler中返回。
	 * @param context 上下文环境
	 * @param urls 服务器地址,多个地址用于轮询
	 * @param params 传递的参数KEY-VALUE的形式
	 * @param handler 自定义的Handler
	 */
	void sendRequest(Context context, HashMap<String, Object> params, Handler handler, String... urls);
	
	/**
	 * 向服务器发送请求，并将数据放在Handler中返回。
	 * @param context 上下文环境
	 * @param urls 服务器地址,多个地址用于轮询
	 * @param params 传递的参数KEY-VALUE的形式
	 * @param handler 自定义的Handler
	 * @param resultWhats 自定义的WHATS,用于接Handler中的数据
	 */
	void sendRequest(Context context, HashMap<String, Object> params, Handler handler, int resultWhats, String... urls);
	
	/**
	 * 向服务器发送请求，并将数据放在Message中,通过Handler返回。
	 * @param context 上下文环境
	 * @param urls 服务器地址,多个地址用于轮询
	 * @param params 传递的参数KEY-VALUE的形式
	 * @param handler 自定义的Handler
	 * @param msg 自定义的Message
	 */
	void sendRequest(Context context, HashMap<String, Object> params, Handler handler, Message msg, String... urls);
}
