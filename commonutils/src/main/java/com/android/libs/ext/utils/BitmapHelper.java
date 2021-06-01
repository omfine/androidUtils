package com.android.libs.ext.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.DisplayMetrics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图像处理类。
 * @author E
 */
public class BitmapHelper {

	/**
	 * 返回经处理后的图片在本地的存储地址。
	 * @param context 上下文环境
	 * @param uri 选择图片返回的URI
	 * @return 图片在本地的存储地址
	 */
	public static String getSuitableBmpPath(Context context , Uri uri){
		Bitmap bitmap = getSuitableBmp(context, uri);
		if (null == bitmap) {
			return null;
		}
		String path = saveBmpToFile(getFileName(), bitmap, 100);
		return path;
	}
	
	/**
	 * 将图片压缩成一定大小的图片后输出。
	 * @param image 原始图片
	 * @param outputSize 期望输出的大小(以KB为单位)
	 * @return 一定大小的图片
	 */
	public static Bitmap compressImage(Bitmap image , int outputSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024 > outputSize) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 5;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		try {
			baos.flush();
			baos.close();
			isBm.close();
		} catch (Exception e) {
		}
		return bitmap;
	}
	
	/**
	 * 根据图片路径生成适合屏幕大小的图片。
	 * @param context 上下文环境
	 * @param uri 图片路径
	 * @return Bitmap 合屏幕大小的图片
	 */
	public static Bitmap getSuitableBmp(Context context , Uri uri){
		try {
			DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
			int dw = displayMetrics.widthPixels;
			int dh = displayMetrics.heightPixels;
			InputStream inputStream = context.getContentResolver().openInputStream(uri);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds=true;
			BitmapFactory.decodeStream(inputStream,null,options);
			int heightRation = (int)Math.ceil(options.outHeight/(float)dh);
			int widthRation = (int)Math.ceil(options.outWidth/(float)dw);
			if (heightRation>=1 && widthRation>=1) {
				if (heightRation > widthRation) {
					options.inSampleSize=heightRation;
				}else {
					options.inSampleSize=widthRation;
				}
			}
			options.inJustDecodeBounds=false;
			InputStream originalInputStream = context.getContentResolver().openInputStream(uri);
			Bitmap suitableBitmap = BitmapFactory.decodeStream(originalInputStream,null,options);
			if (null != inputStream) {
				inputStream.close();
			}
			if (null != originalInputStream) {
				originalInputStream.close();
			}
			return suitableBitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据图片路径生成适合屏幕大小的图片。
	 * @param context 上下文环境
	 * @param imageFilePath 图片路径
	 * @return Bitmap 适合屏幕大小的图片
	 */
	public static Bitmap getSuitableBmp(Context context, String imageFilePath){
		if (imageFilePath.startsWith("file://")){
			imageFilePath = imageFilePath.substring(7);
		}

		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int dw = DeviceInfo.px2dip(context, displayMetrics.widthPixels);
		int dh = DeviceInfo.px2dip(context, displayMetrics.heightPixels);
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds=true;
		Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
		int heightRation = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)dh);
		int widthRation = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)dw);
		if (heightRation>=1&&widthRation>=1) {
			if (heightRation>widthRation) {
				bmpFactoryOptions.inSampleSize=heightRation;
			}else {
				bmpFactoryOptions.inSampleSize=widthRation;
			}
		}
		bmpFactoryOptions.inJustDecodeBounds=false;
		bmp =BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
		return bmp;
	}

	public static Bitmap getSuitableBmp2(Context context, String imageFilePath){
		if (imageFilePath.startsWith("file://")){
			imageFilePath = imageFilePath.substring(7);
		}
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inSampleSize= 1;
		bmpFactoryOptions.inJustDecodeBounds=false;
		Bitmap bmp =BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
		return bmp;
	}
	
	/**
	 * 将图片存入文件并返回图片地址。
	 * @param bitmap 要存入的图片
	 * @param fileName 文件名
	 * @return 图片地址
	 */
	public static String saveBmpToFile(Bitmap bitmap , String fileName){
		String path = saveBmpToFile(fileName, bitmap, 100);
		return path;
	}
	
	/**
	 * 将图片存入本地并返回图片文件。
	 * @param bitmap 要存入的图片
	 * @return 图片文件
	 */
	public static File saveBmpToFile(Bitmap bitmap){
		File file = saveBmpToFile(bitmap, getFileName(), 100);
		return file;
	}
	
	public static String saveBitmapToFile(Bitmap bitmap){
		File file = saveBmpToFile(bitmap, getFileName(), 100);
		return null == file ? null : file.getAbsolutePath();
	}
	
    /**
     * 将图片存入文件并返回存储路径。 
     * @param fileName 文件名
     * @param bitmap Bitmap
     * @param quality quality
     * @return 返回存储路径。
     */
	public static String saveBmpToFile(String fileName, Bitmap bitmap, int quality) {
		File file = saveBmpToFile(bitmap, fileName, quality);
		if (null == file) {
			return null;
		}
		return file.getAbsolutePath();
	}
	
	/**
	 * 将图片存入本地并返回图片文件。
	 * @param bitmap 要存入的图片
	 * @param fileName 图片文件名
	 * @param quality 图片质量0 ~ 100
	 * @return 图片文件
	 */
	public static File saveBmpToFile(Bitmap bitmap , String fileName , int quality){
		boolean sdExist = CheckHelper.sdCardExist();
		if (null == bitmap || !sdExist || null == fileName ||"".equals(fileName)) {
			return null;
		}
		try {
			File file = new File(FileHelper.getImageDirFile() , fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, quality, fOut);
			fOut.flush();
			fOut.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static File saveBmpToFile(byte[] data){
		try {
			File file = new File(FileHelper.getImageDirFile() , getFileName());
			FileOutputStream fOut = new FileOutputStream(file);
			fOut.write(data , 0 , data.length);
			fOut.flush();
			fOut.close();
			return file;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据图片网络地址取得Drawable对象。
	 * @param imageUrl 图片网络地址
	 * @return Drawable
	 */
	public static Drawable getNetDrawable(String imageUrl){
		if (!CheckHelper.isRightFormatNetImageUrl(imageUrl)) {
			return null;
		}
		String imageName = getImageName(imageUrl);
		if (null == imageName) {
			return null;
		}
		if (FileHelper.imageExist(imageName)) {
			File file = FileHelper.getImageFile(imageName);
			Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
			return drawable;
		}
		try {
			URL url = new URL(imageUrl);
			Drawable drawable = Drawable.createFromStream(url.openStream(), null);
			saveDrawable(drawable, imageName);
			return drawable;
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 从网络获取图片，先检查本地是否已存在。
	 * @param imageUrl 图片的网络URL
	 * @return Bitmap
	 */
	public static Bitmap getNetBmp(String imageUrl){
		Drawable drawable = getNetDrawable(imageUrl);
		if (null == drawable) {
			return null;
		}
		return drawableToBitmap(drawable);
	}
	
	public static File getNetBitmap(String imageUrl){
		try {
			URL url = new URL(imageUrl);
			Drawable drawable = Drawable.createFromStream(url.openStream(), null);
			String path = saveDrawable(drawable, null);
			return new File(path);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 根据图片名取得本地的DRAWABLE图片。
	 * @param imageName 图片名
	 * @return DRAWABLE
	 */
	public static Drawable getLocalDrawable(String imageName){
		if (!CheckHelper.isRightImageName(imageName)) {
			return null;
		}
		if (FileHelper.imageExist(imageName)) {
			File file = FileHelper.getImageFile(imageName);
			Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
			return drawable;
		}
		return null;
	}
	
	/**
	 * 将Drawable对象转化为Bitmap对象后存入本地。
	 * @param drawable
	 * @param imageFileName 文件名
	 */
	public static String saveDrawable(Drawable drawable , String imageFileName){
		if (null == drawable) {
			return null;
		}
		if (CheckHelper.isNullOrEmpty(imageFileName)) {
			imageFileName = getFileName();
		}
		Bitmap bitmap = (((BitmapDrawable)drawable).getBitmap());
		String path = saveBmpToFile(bitmap, imageFileName);
		return path;
	}
	
	/**
	 * 将Drawable转化为Bitmap。
	 * @param drawable
	 * @return Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),
                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
}
	
	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return degree;
		}
		return degree;
	}
	
	/**
	 * 旋转图片，使图片保持正确的方向。
	 * @param bitmap 原始图片
	 * @param degrees 原始图片的角度
	 * @return Bitmap 旋转后的图片
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
		if (degrees == 0 || null == bitmap) {
			return bitmap;
		}
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		if (null != bitmap) {
			bitmap.recycle();
		}
		return bmp;
	}
	
	/**
	 * 用当前时间给取得的图片命名。
	 * @return 唯一的值，做为文件名
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getFileName() {
		Date date = new Date(System.currentTimeMillis());
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String addName = (""+System.currentTimeMillis()).substring(9);
		return dateFormat.format(date)  + addName + ".png";
	}
	
	/**
	 * 通过图片URI得到相应的图片名
	 * @param imageUri 图片URI
	 * @return 图片名
	 */
	public static String getImageName(String imageUri){
		if (null == imageUri || "".equals(imageUri)) {
			return null;
		}
		if (!imageUri.contains("/")) {
			return null;
		}
		String iamgeName = imageUri.substring(imageUri.lastIndexOf("/") + 1);
		return iamgeName;
	}
}
