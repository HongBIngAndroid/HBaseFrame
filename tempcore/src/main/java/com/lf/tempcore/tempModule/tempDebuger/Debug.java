package com.lf.tempcore.tempModule.tempDebuger;

import android.util.Log;


/**
 * Debug 测试跟踪打印类对象
 *
 * @author shx
 *
 */
public class Debug {

    /**
     * 是否开启debug模式
     */
    public static boolean DEBUG = true;
    /**
     * 在开启debug模式下是否打印日志
     */
    public static boolean PrintLog=false;
    /**
     * debug输出的默认标签名称
     */
    public static String TAG = "LF_debug";
    /**
     * 错误日志记录文件
     */
    private static final String logName="error.log";
    private static final String debugLogName="debug.log";

    public static void debug(String paramString) {
        if (DEBUG){
            Log.d(TAG, paramString);
            if(PrintLog){
                TempWriteLog.printString(TAG+"\n"+paramString,debugLogName);
            }
        }

    }

    public static void debug(String tag, String paramString) {
        if (DEBUG){
            Log.d(tag, paramString);
            if(PrintLog){
                TempWriteLog.printString(tag+"\n"+paramString,debugLogName);
            }
        }

    }

    public static void error(String paramString) {
        if (DEBUG){
            Log.e(TAG, paramString);
            if(PrintLog){
                TempWriteLog.printString(TAG + "\n" + paramString, logName);
            }
        }

    }

    public static void error(Exception e) {
        if (DEBUG) {
            e.printStackTrace();
            StringBuffer sb=new StringBuffer();
            sb.append(e.getMessage());
            for (StackTraceElement ement : e.getStackTrace()) {
                sb.append("\n");
                sb.append(ement.toString());
            }
//			Log.e(TAG,sb.toString());
            if (PrintLog){
                TempWriteLog.printString(sb.toString(),logName);
            }

        }
    }

    public static void error(String tag, String paramString) {
        if (DEBUG) {
            Log.e(tag, paramString);
            if (PrintLog) {
                TempWriteLog.printString(tag + "\n" + paramString, logName);
            }
        }
    }

    public static void error(String tag, Exception e) {
        if (DEBUG) {
            e.printStackTrace();
            StringBuffer sb=new StringBuffer();
            sb.append(tag);
            sb.append(e.getMessage());
            for (StackTraceElement ement : e.getStackTrace()) {
                sb.append("\n");
                sb.append(ement.toString());
            }
//			Log.e(TAG, sb.toString());
            TempWriteLog.printString(sb.toString(),logName);
        }
    }

    public static void info(String paramString) {
        if (DEBUG)
            Log.i(TAG, paramString);
    }

    public static void info(String paramString1, String paramString2) {
        if (DEBUG)
            Log.i(paramString1, paramString2);
    }

    public static void trace(String paramString) {
        if (DEBUG)
            Log.v(TAG, paramString);
    }

    public static void trace(String paramString1, String paramString2) {
        if (DEBUG)
            Log.v(paramString1, paramString2);
    }

    public static void warn(String paramString) {
        if (DEBUG) {
            String str = "Warning: " + paramString;
            Log.w(TAG, str);
        }
    }

    public static void warn(String paramString1, String paramString2) {
        if (DEBUG) {
            String str = "Warning: " + paramString2;
            Log.w(paramString1, str);
        }
    }
}

