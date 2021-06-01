package com.android.libs.ext.http;

import android.content.Context;
import android.os.Handler;

import com.android.libs.ext.bean.FormFile;

import java.util.List;
import java.util.Map;

/**
 * 文件上传的接口类。
 * @author E
 */
public interface BaseUploadInterface {

	public String uploadFiles(Context context, Map<String, Object> params, String imageUrl, String... urls) throws Exception;
	
	public String uploadFiles(Context context, Map<String, Object> params, List<String> imageList, String... urls) throws Exception;
	
	public void uploadFiles(Context context, Map<String, Object> params, List<String> imageList, Handler handler, String... urls);
	
	public void uploadFiles(Context context, Map<String, Object> params, List<String> imageList, Handler handler, int resultWhats, String... urls);
	
	public void uploadFiles(Context context, Map<String, Object> params, String imageUrl, Handler handler, int resultWhats, String... urls);
	
	public void uploadImgAndSoundFiles(Context context, Map<String, Object> params, List<String> imageList, String soundUrl,
                                       String soundLength, Handler handler, String... urls);

	public void uploadFiles(Context context, Map<String, Object> params, List<String> imageList, String soundUrl,
                            String soundLength, Handler handler, int resultWhats, String... urls);
	
	public String uploadImgAndSoundToServer(Context context, Map<String, Object> params, List<String> imageList, String soundUrl, String soundLength, String... urls) throws Exception;
	
	public String uploadFilesToServer(Context context, Map<String, Object> params, List<String> imageList, String... urls) throws Exception;
	
	/**
	 * 上传文件的核心代码
	 * @param urls 服务器地址
	 * @param urlIndex 服务器地址序号
	 * @param params 封装后的请求参数
	 * @param files
	 * @return 服务器返回结果
	 * @throws Exception 导常处理
	 */
	public String uploadFilesToServer(Context context, Map<String, Object> params, FormFile[] files, int urlIndex, String... urls) throws Exception;
	
}
