package com.lf.tempcore.tempModule.tempUtils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by longf on 2016/6/3.
 */
public class TempPermissionUtil {
    /**请求高德地图相关权限
     * @param cxt
     */
    public static void requestAmapPermission(Context cxt) {
        if (ContextCompat.checkSelfPermission(cxt, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(cxt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(cxt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(cxt, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(cxt, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(cxt, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(cxt, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(cxt, Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) cxt, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CHANGE_NETWORK_STATE,
            }, 0);

        }
    }


    public static void requestMapPer(Context context,int code){
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ((Activity) context).requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, code);
            }
        }

    }

  /*  private static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/

    /**检测照相机权限
     * @param context
     * @return
     */
    public static boolean checkCameraPermission(Context context){
        //检测sd卡读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ) {
                return false;
            }

        }
        return true;
    }
    /**请求照相机权限
     * @param context
     */
    public static void requestCameraPermission(final Context context,final int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
//                if (!((Activity)context).shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
//                    new AlertDialog.Builder(context)
//                            .setMessage("请允许相机使用，才能正常使用当前功能！")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @TargetApi(Build.VERSION_CODES.M)
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
//                                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
//
//                                }
//                            })
//                            .setNegativeButton("取消", null)
//                            .create()
//                            .show();
//                    return ;
//                }
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

            }

        }
    }

   /* *//**检测储存卡读写权限
     * @param context
     * @return
     *//*
    public static boolean checkWriteAndReadPermission( Context context) {
        //检测sd卡读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }
        return true;
    }

    *//*
    public static void requestWriteAndReadPermission(Context context,int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }*/

    /**
     * 请求打电话权限
     * @param context
     * @param requestCode
     */
    public static void requestCallPhonePermission(Context context,int requestCode){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(((Activity) context),new String[]{Manifest.permission.CALL_PHONE},requestCode);
            }

        }

    }
    /**请求储存卡读写权限
     * @param context
     */
    public static void requestWriteAndReadPermissionGroup(final Context context,final int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (!((Activity)context).shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&!((Activity)context).shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    new AlertDialog.Builder(context)
                            .setMessage("您需要允许内存卡使用权限，才能正常使用当前功能！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                    return ;
                }
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

            }

        }

    }

//    private void checkStoragePremission(Context context){

       /* if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showMessageOKCancel("你需要同意使用sd卡权限，才能正常使用当前应用！",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                                        0);
                            }});
                    }
        }*/

    public static void requestRecordVoicePermission(Context context,int requestCode){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(((Activity) context),new String[]{Manifest.permission.RECORD_AUDIO},requestCode);
            }

        }

    }

}

