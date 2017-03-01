package com.lf.tempcore.tempModule.tempUtils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.lf.tempcore.tempConfig.TempSdCardConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by whb on 2016/8/2.
 * 邮箱 673337538@qq.com
 */
public class TempDownFileUtil {

    public TempDownFileUtil(final String newurl, final String fileName, final Context context,Handler mhandler) {
        downloadFile(newurl, fileName, context,mhandler);
    }

    public  void downloadFile(final String newurl, final String fileName, final Context context,final Handler handler) {

     final   String savePath = TempSdCardConfig.SDCARD_CACHE_PATH;

        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("gongwen Url", newurl);
                try {
                    URL url = new URL(newurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String saveFileName = savePath + fileName;
                    File ApkFile = new File(saveFileName);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    byte buf[] = new byte[1024/2];
                    int len = 0;
                    while  ((len  =  is.read(buf)) !=-1 ){
                        fos.write(buf,  0 , len);
                    }

                    fos.flush();
                    fos.close();
                    is.close();
                    handler.sendEmptyMessage(0);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
