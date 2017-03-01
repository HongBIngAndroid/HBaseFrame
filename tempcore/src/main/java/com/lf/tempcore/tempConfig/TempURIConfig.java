package com.lf.tempcore.tempConfig;

import android.text.TextUtils;

/**
 * Created by Administrator on 2015/11/14.
 */
public class TempURIConfig {


//    public final static String BASE_SERVICE_URL = "http://192.168.0.2:8099/housekeeping/";
//    public static final String BASE_SERVICE_URL = "http://192.168.0.145:8080/housekeeping/";
//    public final static String BASE_SERVICE_URL = "http://192.168.0.7:8090/housekeeping/";
//
//    public final static String BASE_SERVICE_URL = "http://192.168.0.144:8080/";
//    public final static String BASE_SERVICE_URL = "http://192.168.1.161:8080/";
//    public final static String BASE_SERVICE_URL = "http://192.168.0.7:8099/";
////    public final static String BASE_SERVICE_URL = "http://192.168.0.2:8080/";
//    public final static String BASE_IMG_URL = BASE_SERVICE_URL+"dentistpi/common/file/download.do?storeFileName=";
//    public final static String BASE_SERVICE_URL = "http://115.28.55.39:8080/";//外网地址
    public final static String BASE_SERVICE_URL = "http://www.dentistpie.com/";//外网地址
    public final static String BASE_IMG_URL = BASE_SERVICE_URL+"common/file/download.do?storeFileName=";//外//外
//    public final static String BASE_IMG_URL = "http://139.129.18.81:8083/filemanage/download.do?storeFileName=";
//    public final static String BASE_SERVICE_URL = "http://192.168.0.8:8080/housekeeping/";
//    public final static String BASE_SERVICE_URL = "http://192.168.0.200:8080/housekeeping/";
//    public final static String BASE_SERVICE_URL = "http://139.129.18.81:8083/housekeeping/";
//    public final static String BASE_SERVICE_URL = "http://139.129.18.81:8081/housekeeping/";

    public static String makeImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return BASE_IMG_URL + url;
    }

    public static String makeImageUrl(String url, int width, int height) {
        if (TextUtils.isEmpty(url))
            return "";
        if (TextUtils.isEmpty(width + "") || TextUtils.isEmpty(height + ""))
            return BASE_IMG_URL + url + "&imgwidth=" + width + "&imgheight=" + height;
        return BASE_IMG_URL + url;
    }


    //<!--以下为JSP或者HTML页面-->
    //关于我们
//    public final static String PLARMINFO = BASE_SERVICE_URL+"dentistpi/app/public/mall/queryProject.do";
//    //用户协议
//    public final static String USER_AGRESSMENT = BASE_SERVICE_URL+"userAgreement.html";
//    //广告详情
//    public final static String AD_DETAIL = BASE_SERVICE_URL+"app/public/mall/queryAdvertismentDetails.do?id=";
//    //消息中心
//    public final static String MESSAGE_CENTER = BASE_SERVICE_URL+"dentistpi/app/private/mall/queryMallMessageDetail.do?museId=";
//
//    //app下载地址
//    public final static String APP_DOWNLOAD = BASE_SERVICE_URL+"dentistpi/portal/appDownload.do";

    //<!--以下为外网--->
    //关于我们
    public final static String PLARMINFO = BASE_SERVICE_URL+"app/public/mall/queryProject.do";
    //用户协议
    public final static String USER_AGRESSMENT = BASE_SERVICE_URL+"userAgreement.html";
    //广告详情
    public final static String AD_DETAIL = BASE_SERVICE_URL+"app/public/mall/queryAdvertismentDetails.do?id=";
    //消息中心
    public final static String MESSAGE_CENTER = BASE_SERVICE_URL+"app/private/mall/queryMallMessageDetail.do?museId=";

    //app下载地址
    public final static String APP_DOWNLOAD = BASE_SERVICE_URL+"portal/appDownload.do";


}
