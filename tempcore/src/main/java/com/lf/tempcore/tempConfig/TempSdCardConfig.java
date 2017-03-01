package com.lf.tempcore.tempConfig;

import android.os.Environment;

/**
 * Author：longf on 2016/1/16 16:10
 * Created by Administrator on 2016/1/16.
 */
public class TempSdCardConfig {
    public final static String SDCARD_CACHE_NAME;
    /*****手机缓存路径,所有缓存文件全部放入该路径下*******/

    public final static String SDCARD_CACHE_PATH;
    public final static String SDCARD_CACHE_PATH_DATABASE;
    public final static String SDCARD_CACHE_PATH_APKPATH;

    static{
        SDCARD_CACHE_NAME="dentisipi";
        SDCARD_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+SDCARD_CACHE_NAME+"/cache/";
        SDCARD_CACHE_PATH_DATABASE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+SDCARD_CACHE_NAME+ "/tempDB";
        SDCARD_CACHE_PATH_APKPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+SDCARD_CACHE_NAME+"/apk";
    }

    /*软件使用标记*/
    public final static String APPUSED_TABLE_NAME;//保存软件被使用情况
    public final static String ISUSED;//是否被使用，被使用值为true
    static{
        APPUSED_TABLE_NAME = "appused_info";
        ISUSED = "isused";
    }

}
