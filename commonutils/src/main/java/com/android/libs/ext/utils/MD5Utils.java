package com.android.libs.ext.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/**
 * MD5加密，解密
 * @author E
 */
@SuppressLint("DefaultLocale")
public class MD5Utils {
	
	public static final String key="key=iwu2013";
	
	/**
	 * 默认utf-8的编码
	 * @param inStr
	 * @return
	 */
	  public static String toMD5(String inStr) {
		  return toMD5ByEncode(inStr, "UTF-8") ;   
	  }
	
    
    public static String toMD5ByEncode(String inStr, String encode) {
    	StringBuffer buf=new StringBuffer();
    	try{
    		byte[] source = inStr.getBytes(encode);
    		MessageDigest md = MessageDigest.getInstance("MD5");
    	    md.update(source);    	    
    	    for(byte b:md.digest())
    	    	buf.append(String.format("%02x", b&0xff) );
    	}catch( Exception e ){
    		e.printStackTrace(); return null;
    	}  
    	return buf.toString();
    }
  
    /**
     * 解密MD5
     * @param inStr
     * @return
     */
    public static String convertMD5(String inStr){
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);
        return s;  
    }

	/**
	 * 根据请求参数生成加密字符串密钥
	 * @param params
	 * @return
	 */
	public static String getSign(Map<String,String> params){
		String finalSign="";
		//TreeMap默认升序
		Map<String,String> signMap=new TreeMap<String, String>();
		for (Map.Entry<String,String> item : params.entrySet()) {
			//清除掉""
			if(!TextUtils.isEmpty(item.getValue())){
				//过滤地区字段
				if(!item.getKey().equals("region")){
					signMap.put(item.getKey(),item.getValue());
				}
			}
		}
		//拼接字符串为原始码
		String code="";
		for (Map.Entry<String,String> m : signMap.entrySet()) {
			code+=(m.getKey()+"="+m.getValue()+"&");
		}
		//最后加上key
		code += key;
//		code=code.substring(0, code.lastIndexOf("&"));
		//MD5
		finalSign=MD5Utils.toMD5(code);
		//大写
		if (null != finalSign) {
			finalSign.toUpperCase();
		}
		return finalSign;
	}
    
}
