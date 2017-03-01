package com.lf.tempcore.tempModule.tempUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 图片优化处理工具
 * 
 * @author wtj
 * 
 */
public class TempBitmapUtils {

	private final String TAG = "BitmapUtils";

	/**
	 * 改变图片加载的颜色模式，优化图片内存
	 * 
	 * @return
	 */
	public static Drawable getDrawable(Context context,int rid) {
		Options opt = new Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;// 使用这种模式一个像素占2个字节
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(rid);
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("BitmapUtils", "资源图输入流关闭失败" + e.toString());
		}// 关闭资源
		return new BitmapDrawable(context.getResources(), bitmap);
	}

	/**
	 * 将bitmap保存到指定文件夹
	 * 
	 * @param bitmap
	 * @param filepath
	 * @param filename
	 * @return
	 */
	public static String saveBitmapToFile(Bitmap bitmap, String filepath, String filename) {
		FileOutputStream fileOutStream = null;
		if (filepath == null) {
			return null;
		}
		File file = new File(filepath);
		if (!file.exists())
			file.mkdirs();// 创建文件夹
		file = new File(filepath, filename);
		try {
			fileOutStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutStream.flush();
				fileOutStream.close();
				bitmap.recycle();
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file.getAbsolutePath();
	}

	/**
	 * 获取第三方相册或者相机返回的图片数据
	 * 
	 * @param activity
	 * @return 图片所在的绝对路径
	 */
	public static String getFilePathFromIntent(Intent data, String path, Activity activity) {
		Uri uri = data.getData();
		String filepath = "";
		if (uri == null) {// 某些手机会返回Null
			Bundle bundle = data.getExtras();
			// 获取相机返回的数据，并转换为Bitmap图片格式(此图为缩略图)
			Bitmap bitmap = (Bitmap) bundle.get("data");
			String filename = String.valueOf(System.currentTimeMillis());
			filepath = TempBitmapUtils.saveBitmapToFile(bitmap, path, filename + ".jpg");
		} else {
			filepath = TempCharacterUtils.getAbsolutePathFromUri(uri, activity);// 需要根据不同版本处理
		}
		return filepath;
	}

	/**
	 * 将大图片转换为小图片
	 * 
	 * @param bigFilePath
	 *            大图片文件路径
	 * @param tofilepath
	 *            小图片准备保存的文件夹路径
	 * @param smallfilename
	 *            小图片名
	 * @param imageViewWidth
	 *            显示图片的控件宽,用于计算压缩比例
	 * @param imageViewHeight
	 *            显示图片的控件高,用于计算压缩比例
	 * @return 返回压缩后的小图片路径
	 */
	public static String big2Small(String bigFilePath, String tofilepath, String smallfilename, int imageViewWidth,
			int imageViewHeight) {
		String smallFilepath = "";

		Options opts = new Options();
		// 设置这个，只得到Bitmap的属性信息放入opts，而不把Bitmap加载到内存中
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(bigFilePath, opts);

		int bitmapWidth = opts.outWidth;
		int bitmapHeight = opts.outHeight;
		// 取最大的比例，保证整个图片的长或者宽必定在该屏幕中可以显示得下
		int scale = Math.max(bitmapWidth / imageViewWidth, bitmapHeight / imageViewHeight);

		// 缩放的比例
		if (scale <= 0)
			scale = 1;
		opts.inSampleSize = scale;
		// 内存不足时可被回收
		opts.inPurgeable = true;
		// 设置为false,表示不仅Bitmap的属性，也要加载bitmap
		opts.inJustDecodeBounds = false;

		Bitmap bitmap = rotaingImageView(readPictureDegree(bigFilePath), BitmapFactory.decodeFile(bigFilePath, opts));
		smallFilepath = saveBitmapToFile(bitmap, tofilepath, smallfilename);
		return smallFilepath;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path 图片绝对路径
	 * 
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
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
		}
		return degree;
	}

	/*
	 * 旋转图片
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	
	/**
	 * 将指定的图片按一定比例裁剪，并且保存到制定位置
	 * @param srcuri 原图uri
	 * @param targeturi 需要保存的路的uri
	 * @param activity 
	 * @param aspectX 图片宽的比例
	 * @param aspectY 图片高的比例
	 * @param outputX 输出图片宽度（单位像素）
	 * @param outputY 输出图片高度（单位像素）
	 * @param requestCode 请求码
	 * @param isChosePic 是否为从相册中选取图片。如果为true，参数srcuri无用，可以为null
	 */
	public static void cropImageUri(Uri srcuri, Uri targeturi,Activity activity,int aspectX,int aspectY,
			int outputX, int outputY, int requestCode,boolean isChosePic){
		Intent intent = new Intent();
		if(isChosePic){
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
		}else{
			intent.setAction("com.android.camera.action.CROP");
			intent.setDataAndType(srcuri, "image/*");
		}
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
	     intent.putExtra("crop", "true");
	     // aspectX aspectY 是宽高的比例
	     intent.putExtra("aspectX", aspectX);
	     intent.putExtra("aspectY", aspectY);
	     // outputX,outputY 是剪裁图片的宽高
	     intent.putExtra("outputX", outputX);
	     intent.putExtra("outputY", outputY);
	     intent.putExtra("return-data", false);
	     intent.putExtra("scale", true);
	     intent.putExtra(MediaStore.EXTRA_OUTPUT, targeturi);
	     intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	     intent.putExtra("noFaceDetection", true);// no face detection
	     activity.startActivityForResult(intent,requestCode);
	}
}
