package com.lf.tempcore.tempModule.tempUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 * @author wtj
 *
 */
public class TempMD5Util {
	
	public static String getMD5String(String str)
    {
		  try {   
			  MessageDigest md = MessageDigest.getInstance("MD5");
			  md.update(str.getBytes()); 
			  byte b[] = md.digest();  
			  int i; 
			  StringBuffer buf = new StringBuffer(""); 
			  for (int offset = 0; offset < b.length; offset++)
			  {   
				  i = b[offset];   
				  if (i < 0)     i += 256; 
				  if (i < 16)     buf.append("0");
				  buf.append(Integer.toHexString(i)); 
			  }  
			  str = buf.toString();
			  }catch (NoSuchAlgorithmException e) {  
				  e.printStackTrace(); 
			  }
			  return str;
    }
	
}
