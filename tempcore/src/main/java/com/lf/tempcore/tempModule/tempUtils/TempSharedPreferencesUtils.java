package com.lf.tempcore.tempModule.tempUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * 操作SharedPreferences的工具
 * @author wtj
 *
 */
public class TempSharedPreferencesUtils {
	public static final String TAG = "SharedPreferencesUtils";
	/**
	 * 将数据对象中的数据保存到SharedPreferences中
	 * @param name SharedPreferences的文件名
	 * @param data 数据对象
	 */
	public static void saveDataToSharedPreferences(Context context,String name,Object data){
		SharedPreferences sharedPrefs = context.getApplicationContext()
				.getSharedPreferences(name, Context.MODE_PRIVATE);
		 Editor editor = sharedPrefs.edit();
		 
		 if (data == null)  {
			 Log.i("SharedPreferencesUtils", "saveDataToSharedPreferences()...传入的数据对象为null");
	            return;  
		 }
		 Field[] fields = data.getClass().getDeclaredFields();
		 for(int i = 0;i < fields.length;i++){
			// 类中的成员变量为private,故必须进行此操作
			 fields[i].setAccessible(true);
			 String fieldName = fields[i].getName();//获取属性名字
			 try {
				Object o = fields[i].get(data);//获取属性值
				if(o != null){
					String type = fields[i].getType().toString();
					if(type.endsWith("boolean")){
						editor.putBoolean(fieldName, (Boolean)o);
					}else if(type.endsWith("float")){
						editor.putFloat(fieldName, (Float)o);
					}else if(type.endsWith("int")){
						editor.putInt(fieldName, (Integer)o);
					}else if(type.endsWith("long")){
						editor.putLong(fieldName, (Long)o);
					}else if(type.endsWith("String")){
						editor.putString(fieldName, o.toString());
					}
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "saveDataToSharedPreferences()...属性访问异常:" + e.toString());
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "saveDataToSharedPreferences()...属性访问异常:" + e.toString());
				e.printStackTrace();
			}
		 }
		 editor.commit();
	}
	
	public static Object getDataFromSharedPreferences(Context context,String name,Object data){
		SharedPreferences sharedPrefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		 Field[] fields = data.getClass().getDeclaredFields();
		 for(int i = 0;i < fields.length;i++){
			// 类中的成员变量为private,故必须进行此操作
	         fields[i].setAccessible(true);
			 String fieldName = fields[i].getName();//获取属性名字
			 String type = fields[i].getType().toString();
			 try {
					if(type.endsWith("boolean")){
							fields[i].set(data,sharedPrefs.getBoolean(fieldName,false));
					}else if(type.endsWith("float")){
						    fields[i].set(data,sharedPrefs.getFloat(fieldName, 0));
					}else if(type.endsWith("int")){
							fields[i].set(data,sharedPrefs.getInt(fieldName, 0));
					}else if(type.endsWith("long")){
							fields[i].set(data,sharedPrefs.getLong(fieldName,0));
					}else if(type.endsWith("String")){
						String a = sharedPrefs.getString(fieldName, "");
							fields[i].set(data,a);
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "getDataFromSharedPreferences()...属性访问异常:" + e.toString());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "getDataFromSharedPreferences()...属性访问异常:" + e.toString());
					e.printStackTrace();
				}
		 } 
		 return data;
	}
	
}
