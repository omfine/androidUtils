package com.android.libs.ext.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class BaseAES {

	//这个KEY要前后台一致
//	private final static String KEY = "b312eb15ea404ea7a8smart9f3a31a6a";
	
	public abstract String getAESKey();
	
	/**
	 * Encodes a String in AES-256.
	 * @param stringToEncode
	 * @return String Base64 and AES encoded String
	 */
	@SuppressLint("TrulyRandom")
	public String encode(String stringToEncode){
	    if (TextUtils.isEmpty(stringToEncode)) {
			return "";
		}
	    try {
	        SecretKeySpec skeySpec = getKey(getAESKey());
	        byte[] clearText = stringToEncode.getBytes("UTF8");
	        // IMPORTANT TO GET SAME RESULTS ON iOS and ANDROID
	        final byte[] iv = new byte[16];
	        Arrays.fill(iv, (byte) 0x00);
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	        
	        // Cipher is not thread safe
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
	        
	        String encrypedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
	        return encrypedValue;
	    } catch (Exception e) {
	    	e.printStackTrace();
		}
	    return "";
	}
	 
	/**
	 * Decodes a String using AES-256 and Base64
	 * @param text
	 * @return decoded String
	 */
	public String decode(String text) {
	    if (TextUtils.isEmpty(text)) {
			return "";
		}
	    try {
	        SecretKey key = getKey(getAESKey());
	        // IMPORTANT TO GET SAME RESULTS ON iOS and ANDROID
	        final byte[] iv = new byte[16];
	        Arrays.fill(iv, (byte) 0x00);
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	        
	        byte[] encrypedPwdBytes = Base64.decode(text, Base64.DEFAULT);
	        // cipher is not thread safe
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
	        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
	        byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));
	        
	        String decrypedValue = new String(decrypedValueBytes);
	        return decrypedValue;
	    }catch (Exception e) {
	    	e.printStackTrace();
		}
	    return "";
	}
	 
	/**
	 * Generates a SecretKeySpec for given password
	 * @param password
	 * @return SecretKeySpec
	 * @throws UnsupportedEncodingException
	 */
	private static SecretKeySpec getKey(String password) throws UnsupportedEncodingException {
	    // You can change it to 128 if you wish
	    int keyLength = 256;
	    byte[] keyBytes = new byte[keyLength / 8];
	    // explicitly fill with zeros
	    Arrays.fill(keyBytes, (byte) 0x0);
	    
	    // if password is shorter then key length, it will be zero-padded
	    // to key length
	    byte[] passwordBytes = password.getBytes("UTF-8");
	    int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
	    System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	    return key;
	}
}
