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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class BaseUploadImp implements BaseUploadInterface {
	
	//jpg格式的图片
	public static String IMAGE_JPG = "image/png";
	//amr格式的音频
	public static String AUDIO_AMR = "audio/amr";
	
	public static final int RESULT_OK = 9654225;
	public static final int ERROR_REQUEST = 96526544;
	public static final int NET_ERROR_CODE = 1234564;

	public static final String methodPost = "POST";
	public static final String methodGet = "GET";

	public BaseUploadImp() {
	}

	@Override
	public String uploadFiles(Context context, Map<String, Object> params, String imageUrl, String... urls) throws Exception {
		List<String> imageList = new ArrayList<String>();
		imageList.add(imageUrl);
		return uploadFilesToServer(context, params, imageList, urls);
	}

	@Override
	public String uploadFiles(Context context, Map<String, Object> params, List<String> imageList, String... urls) throws Exception {
		return uploadFilesToServer(context, params, imageList, urls);
	}

	@Override
	public void uploadFiles(Context context, Map<String, Object> params, List<String> imageList, Handler handler, String... urls) {
		uploadFiles(context , params , imageList ,null , null , handler , -1 , urls);
	}

	@Override
	public void uploadFiles(Context context, Map<String, Object> params, List<String> imageList, Handler handler, int resultWhats,
                            String... urls) {
		uploadFiles(context , params , imageList ,null , null , handler , resultWhats , urls);
	}

	@Override
	public void uploadFiles(Context context, Map<String, Object> params, String imageUrl, Handler handler, int resultWhats, String... urls) {
		List<String> imageList = new ArrayList<String>();
		imageList.add(imageUrl);
		uploadFiles(context , params , imageList , handler , resultWhats , urls);
	}

	@Override
	public void uploadImgAndSoundFiles(Context context, Map<String, Object> params, List<String> imageList, String soundUrl, String soundLength,
                                       Handler handler, String... urls) {
		uploadFiles(context , params , imageList , soundUrl , soundLength , handler , -1 , urls);
	}

	@Override
	public void uploadFiles(final Context context, final Map<String, Object> params, final List<String> imageList, final String soundUrl, final String soundLength,
                            final Handler handler, final int resultWhats, final String... urls) {
		if (!NetStatus.checkNetWorkStatus(context)) {
			if (null != handler) {
				handler.sendEmptyMessage(NET_ERROR_CODE);
			}
			return;
		}
		ThreadPool.add(() -> {
			try {
				String result = uploadImgAndSoundToServer(context , params , imageList , soundUrl , soundLength , urls);
				if (null != handler) {
					handler.obtainMessage(-1 == resultWhats ? RESULT_OK : resultWhats, result).sendToTarget();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (null != handler) {
					handler.sendEmptyMessage(ERROR_REQUEST);
				}
			}
		});
	}

	@Override
	public String uploadImgAndSoundToServer(Context context, Map<String, Object> params, List<String> imageList, String soundUrl, String soundLength, String... urls)
			throws Exception {
		FormFile[] formFiles = null;
		int allSize = 0;
		if(!CheckHelper.listIsEmpty(imageList))
			allSize = imageList.size();
		if(!TextUtils.isEmpty(soundUrl))
			allSize += 1;
		
		formFiles = new FormFile[allSize];
		
		if(!CheckHelper.listIsEmpty(imageList)){
			int size = imageList.size();
			params.put("imagesNum", ""+size);
			
			for (int i = 0; i < size; i++) {
				String image = imageList.get(i);
				File localImageFile = new File(image);
				FormFile formFile = new FormFile(localImageFile.getName(), localImageFile, "image" + i, IMAGE_JPG);
				formFiles[i] = formFile;
			}
		}
		if(!TextUtils.isEmpty(soundUrl)){
			File localFile = new File(soundUrl);
			FormFile formFile = new FormFile(localFile.getName(), localFile, "sound", AUDIO_AMR);
			formFiles[allSize-1] = formFile;	
			//声音长度
			params.put("soundLength", "" + soundLength);	
		}
		String result = uploadFilesToServer(context , params , formFiles , 0 , urls);
		return result;
	}

	@Override
	public String uploadFilesToServer(Context context, Map<String, Object> params, List<String> imageList, String... urls) throws Exception {
		FormFile[] formFiles = null;
		if (CheckHelper.listIsEmpty(imageList)) {
			params.put("imagesNum", "0");
			formFiles = new FormFile[0];
		}else {
			int size = imageList.size();
			params.put("imagesNum", ""+size);
			formFiles = new FormFile[size];
			
			for (int i = 0; i < size; i++) {
				String image = imageList.get(i);
				File localImageFile = new File(image);
				FormFile formFile = new FormFile(localImageFile.getName(), localImageFile, "image" + i, IMAGE_JPG);
				formFiles[i] = formFile;
			}
		}
		String result = uploadFilesToServer(context , params , formFiles , 0 , urls);
		return result;
	}

	@Override
	public String uploadFilesToServer(Context context, Map<String, Object> params, FormFile[] files, int urlIndex, String... urls) throws Exception {
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成  
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        int TIME_OUT = getTimeOutSeconds() ;
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

			String userAgent = getUserAgent();
			if (null != userAgent && userAgent.length() > 5){
				conn.setRequestProperty("User-agent" , userAgent);
			}
			String authorization = getAuthorization();
			if (null != authorization && authorization.length() > 5){
				conn.setRequestProperty("Authorization" , authorization);
			}

            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            StringBuffer sb = new StringBuffer();
            // //将个人信息转换为map;
            for (Map.Entry<String, Object> entry : params.entrySet()) {// 构建表单字段内容
            	String key = entry.getKey();
            	Object value = entry.getValue();
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
            // 上传图像;
            if (files != null && files.length != 0) {
    			for (FormFile uploadFile : files) {
                    StringBuilder split = new StringBuilder();
                    split.append("--");
                    split.append(BOUNDARY);
                    split.append("\r\n");
                    split.append("Content-Disposition: form-data;name=\""+uploadFile.getParameterName()+"\";filename=\""+uploadFile.getFilname()+"\"\r\n");
                    split.append("Content-Type: "+uploadFile.getContentType()+"\r\n\r\n");
                    dos.write(split.toString().getBytes());

                    InputStream inputStream = uploadFile.getInStream();
                    if (inputStream != null) {
    					byte[] buffer = new byte[1024];
    					int len = 0;
    					while ((len = inputStream.read(buffer)) != -1) {
    						dos.write(buffer, 0, len);
    					}
                        inputStream.close();
    				} else {
    					dos.write(uploadFile.getData(), 0,
    							uploadFile.getData().length);
    				}
                    dos.write("\r\n".getBytes());
    			}
            }

            //多张图片时，在多张图片写完后，再写结束标识
            byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 数据结束标志
            dos.write(end_data);

            dos.flush();
            int responseCode = conn.getResponseCode();
            if (200 != responseCode && urlIndex < (urlSize-1)) {
            	urlIndex ++ ;
            	uploadFilesToServer(context , params , files, urlIndex , urls);
			}
            result = read2String(conn.getInputStream()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public abstract HashMap<String, Object> specialHandleParams(HashMap<String, Object> params);
	
	//请求超时
	protected int getTimeOutSeconds(){
		return 30*1000;
	}

//	protected String getMethod(){
//		return methodPost;
//	}

	protected String getUserAgent(){
		return "";
	}

	protected String getAuthorization() {
		return "";
	}

	/**
	 * 将输入流转为字符串
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	protected String read2String(InputStream inStream) throws Exception {
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
