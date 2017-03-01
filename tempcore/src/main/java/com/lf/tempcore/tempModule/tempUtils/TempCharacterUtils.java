package com.lf.tempcore.tempModule.tempUtils;

import android.app.Activity;
import android.net.Uri;

/**
 * 处理字符串
 * @author wtj
 *
 */
public class TempCharacterUtils {
	/**
	  * 去除掉字符串中的特殊字符
	  * @param url
	  */
	 public static String trimStr(String url){
		  String substring = null;
		  substring = url.replaceAll("[^\\w]", "");
		  return substring;
	 }
	 /**去掉匹配任何不可见字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]和<和>和\和~
		 * @param str
		 * @return
		 */
		public static String trim(String str){
			
			 String a =  str.replaceAll("[\\s*|<*|>*|~*|\\\\*|{*|}*]", "");
			 return a;
		 }
	  /**
	     * 判断是否为null或空值
	     *
	     * @param str String
	     * @return true or false
	     */
	    public static boolean isNullOrEmpty(String str) {
	        return str == null || str.trim().length() == 0;
	    }

	    /**
	     * 判断str1和str2是否相同
	     *
	     * @param str1 str1
	     * @param str2 str2
	     * @return true or false
	     */
	    public static boolean equals(String str1, String str2) {
	        return str1 == str2 || str1 != null && str1.equals(str2);
	    }

	    /**
	     * 判断str1和str2是否相同(不区分大小写)
	     *
	     * @param str1 str1
	     * @param str2 str2
	     * @return true or false
	     */
	    public static boolean equalsIgnoreCase(String str1, String str2) {
	        return str1 != null && str1.equalsIgnoreCase(str2);
	    }

	    /**
	     * 判断字符串str1是否包含字符串str2
	     *
	     * @param str1 源字符串
	     * @param str2 指定字符串
	     * @return true源字符串包含指定字符串，false源字符串不包含指定字符串
	     */
	    public static boolean contains(String str1, String str2) {
	        return str1 != null && str1.contains(str2);
	    }

	    /**
	     * 判断字符串是否为空，为空则返回一个空值，不为空则返回原字符串
	     *
	     * @param str 待判断字符串
	     * @return 判断后的字符串
	     */
	    public static String getString(String str) {
	        return str == null ? "" : str;
	    }
	    
	    /**
		 * 从uri中获取文件的绝对路径
		 * @param uri
		 * @return
		 */
	    public static String getAbsolutePathFromUri(Uri uri, Activity activity) {
	        return TempGetAbsolutePath.getPhotoPath(activity, uri);
	    }
}