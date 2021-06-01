package com.android.libs.ext.http;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.android.libs.ext.bean.FormFile;
import com.android.libs.ext.utils.CheckHelper;
import com.android.libs.ext.utils.ThreadPool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传，适用于图文混排，批量上传。 
 * @author E
 */
public class UpLoadHelper {
	
	//jpg格式的图片
	public static String IMAGE_JPG = "image/png";
	//amr格式的音频
	public static String AUDIO_AMR = "audio/amr";
	
	public static final int RESULT_OK = 9654225;
	public static final int NET_ERROR_CODE = 1234564;
	
	/**
	 * 上传单张图片。
	 * @param urls 地址
	 * @param params 参数
	 * @param imageUrl 图片地址
	 * @return 结果
	 * @throws Exception 异常
	 */
	public static String uploadFiles(String[] urls , HashMap<String, String> params , String imageUrl) throws Exception {
		ArrayList<String> imageList = new ArrayList<String>();
		imageList.add(imageUrl);
		return uploadFilesToServer(urls, params, imageList);
	}
	
	/**
	 * 上传文件。
	 * @param urls 服务器地址数组
	 * @param params 要上传的参数
	 * @param imageList 文件地址
	 */
	public static String uploadFiles(String[] urls, HashMap<String, String> params , ArrayList<String> imageList) throws Exception {
		return uploadFilesToServer(urls, params, imageList);
	}
	
	/**
	 * 上传文件。
	 * @param urls 服务器地址数组
	 * @param params 要上传的参数
	 * @param imageList 文件地址
	 * @param handler 自定义的Handler
	 */
	public static void uploadFiles(Context context , final String[] urls, final HashMap<String, String> params , final ArrayList<String> imageList , final Handler handler){
		uploadFiles(context ,urls, params, imageList,null,null,handler, -1);
	}
	
	public static void uploadFiles(Context context , final String[] urls, final HashMap<String, String> params , final ArrayList<String> imageList , final Handler handler , final int resultWhats){
		uploadFiles(context ,urls, params, imageList,null,null,handler,resultWhats);
	}
	
	public static void uploadFiles(Context context , final String[] urls, final HashMap<String, String> params , final String imageUrl , final Handler handler , final int resultWhats){
		ArrayList<String> imageList = new ArrayList<String>();
		imageList.add(imageUrl);
		uploadFiles(context, urls, params, imageList, handler, resultWhats);
	}
	
	/**
	 * 上传图片和音频
	 */
	public static void uploadImgAndSoundFiles(Context context , final String[] urls, final HashMap<String, String> params , final ArrayList<String> imageList , final String soundUrl, final String soundLength, final Handler handler){
		uploadFiles(context ,urls, params, imageList,soundUrl,soundLength,handler, -1);
	}
	
	/**
	 * 上传文件（包括图片和音频）
	 * @param context
	 * @param urls
	 * @param params
	 * @param imageList
	 * @param soundUrl
	 * @param soundLength
	 * @param handler
	 * @param resultWhats
	 */
	public static void uploadFiles(Context context , final String[] urls, final HashMap<String, String> params , final ArrayList<String> imageList , final String soundUrl, final String soundLength, final Handler handler , final int resultWhats){
		if (!NetStatus.checkNetWorkStatus(context)) {
			if (null != handler) {
				handler.sendEmptyMessage(NET_ERROR_CODE);
			}
			return;
		}
		ThreadPool.add(() -> {
			try {
				String result = uploadImgAndSoundToServer(urls, params, imageList, soundUrl, soundLength);
				if (null != handler) {
					handler.obtainMessage(-1 == resultWhats ? RESULT_OK : resultWhats, result).sendToTarget();
				}
			} catch (Exception e) {
				if (null != handler) {
					handler.sendEmptyMessage(NET_ERROR_CODE);
				}
			}
		});
	}
	
	/**
	 * 文件上传(包含图片和音频)。
	 * @param urls 要提交到的服务器地址
	 * @param params 参数 
	 * @param imageList imageList要上传的图片文件地址的集合
	 * @param soundUrl 音频文件地址
	 * @param soundLength 音频文件长度
	 * @return
	 * @throws Exception
	 */
	private static String uploadImgAndSoundToServer(String[] urls, HashMap<String, String> params , ArrayList<String> imageList, String soundUrl, String soundLength) throws Exception {
		FormFile[] iwuFormFile = null;
		int allSize = 0;
		if(!CheckHelper.listIsEmpty(imageList))
			allSize = imageList.size();
		if(!TextUtils.isEmpty(soundUrl))
			allSize += 1;
		
		iwuFormFile = new FormFile[allSize];
		
		if(!CheckHelper.listIsEmpty(imageList)){
			int size = imageList.size();
			params.put("imagesNum", ""+size);
			
			for (int i = 0; i < size; i++) {
				String image = imageList.get(i);
				File localImageFile = new File(image);
				FormFile formFile = new FormFile(localImageFile.getName(), localImageFile, "image" + i, IMAGE_JPG);
				iwuFormFile[i] = formFile;
			}
		}
		if(!TextUtils.isEmpty(soundUrl)){
			File localFile = new File(soundUrl);
			FormFile formFile = new FormFile(localFile.getName(), localFile, "sound", AUDIO_AMR);
			iwuFormFile[allSize-1] = formFile;	
			//声音长度
			params.put("soundLength", "" + soundLength);	
		}
		
		String result = uploadFilesToServer(urls, 0, params, iwuFormFile);
		return result;
	}
	
	/**
	 * 文件上传。
	 * @param urls 要提交到的服务器地址
	 * @param params 参数 
	 * @param imageList imageList要上传的图片文件地址的集合
	 * @return 成功或失败
	 */
	private static String uploadFilesToServer(String[] urls, HashMap<String, String> params , ArrayList<String> imageList) throws Exception {
		FormFile[] iwuFormFile = null;
		if (CheckHelper.listIsEmpty(imageList)) {
			params.put("imagesNum", "0");
			iwuFormFile = new FormFile[0];
		}else {
			int size = imageList.size();
			params.put("imagesNum", ""+size);
			iwuFormFile = new FormFile[size];
			
			for (int i = 0; i < size; i++) {
				String image = imageList.get(i);
				File localImageFile = new File(image);
				FormFile formFile = new FormFile(localImageFile.getName(), localImageFile, "image" + i, IMAGE_JPG);
				iwuFormFile[i] = formFile;
			}
		}
		String result = uploadFilesToServer(urls, 0, params, iwuFormFile);
		return result;
	}

	/**
	 * 上传文件的核心代码
	 * @param urls 服务器地址
	 * @param params 封装后的请求参数
	 * @param files 封装后的文件类型
	 * @return
	 * @throws Exception
	 */
	private static String uploadFilesToServer(String[] urls, int urlIndex, HashMap<String, String> params, FormFile[] files) throws Exception {
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成  
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        int TIME_OUT = 30 * 1000;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String result = "";
        try {
        	String requestUrl = urls[urlIndex];
        	int urlSize = urls.length;
            URL curl = new URL(requestUrl);
            conn = (HttpURLConnection) curl.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            StringBuffer sb = new StringBuffer();
            // //将个人信息转换为map;
            for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
            	String key = entry.getKey();
            	String value = entry.getValue();
            	if (null != key && null != value) {
                    sb.append("--");
                    sb.append(BOUNDARY);
                    sb.append("\r\n");
                    sb.append("Content-Disposition: form-data; name=\""
                            + key + "\"\r\n\r\n");
                    sb.append(value);
                    sb.append("\r\n");
				}
            }
            dos = new DataOutputStream(conn.getOutputStream());
            dos.write(sb.toString().getBytes());// 发送表单字段数据
            // 上传头像;
            if (files != null && files.length != 0) {
    			for (FormFile uploadFile : files) {
                    StringBuilder split = new StringBuilder();
                    split.append("--");
                    split.append(BOUNDARY);
                    split.append("\r\n");
                    split.append("Content-Disposition: form-data;name=\""+uploadFile.getParameterName()+"\";filename=\""+uploadFile.getFilname()+"\"\r\n");
                    split.append("Content-Type: "+uploadFile.getContentType()+"\r\n\r\n");
                    dos.write(split.toString().getBytes());
                    if (uploadFile.getInStream() != null) {
    					byte[] buffer = new byte[1024];
    					int len = 0;
    					while ((len = uploadFile.getInStream()
    							.read(buffer, 0, 1024)) != -1) {
    						dos.write(buffer, 0, len);
    					}
    					uploadFile.getInStream().close();
    				} else {
    					dos.write(uploadFile.getData(), 0,
    							uploadFile.getData().length);
    				}
                    dos.write("\r\n".getBytes());

                    byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 数据结束标志
                    dos.write(end_data);
    			}
            }
            dos.flush();
            int responseCode = conn.getResponseCode();
            if (200 != responseCode && urlIndex < (urlSize-1)) {
            	urlIndex ++ ;
            	uploadFilesToServer(urls, urlIndex, params, files);
			}
            result = read2String(conn.getInputStream()).toString();
        }catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally{
        	if(null != dos){
				try {
					dos.close();
				} catch (IOException e) {
				}					
			}	
        }
		return result;
	}
	
	/**
	 * 将输入流转为字符串
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	protected static String read2String(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		byte[] bytes = outSteam.toByteArray();
		outSteam.close();
		inStream.close();
		return new String(bytes, "UTF-8");
	}
}
