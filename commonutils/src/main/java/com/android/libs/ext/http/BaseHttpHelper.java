package com.android.libs.ext.http;

import java.util.HashMap;

/**
 * 网络访问类，主要用来轮询。
 * @author E
 */
public class BaseHttpHelper extends BaseHttpImp{
	
	private static BaseHttpHelper baseHttpHelper = null;
	
	public static BaseHttpHelper getInstance(){
		if (null == baseHttpHelper) {
			baseHttpHelper = new BaseHttpHelper();
		}
		return baseHttpHelper;
	}

	@Override
	public HashMap<String, Object> specialHandleParams(HashMap<String, Object> params) {
		return params;
	}

}
