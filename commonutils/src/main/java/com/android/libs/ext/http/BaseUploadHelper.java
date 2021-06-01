package com.android.libs.ext.http;

import java.util.HashMap;

/**
 * 文件上传调用类。
 * @author E
 */
public class BaseUploadHelper extends BaseUploadImp {

	private static BaseUploadHelper baseUploadHelper = null;
	
	public static BaseUploadHelper getInstance(){
		if (null == baseUploadHelper) {
			baseUploadHelper = new BaseUploadHelper();
		}
		return baseUploadHelper;
	}

	@Override
	public HashMap<String, Object> specialHandleParams(HashMap<String, Object> params) {
		return params;
	}

}
