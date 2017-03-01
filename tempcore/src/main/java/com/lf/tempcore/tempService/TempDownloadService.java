/**
 * @projectName ${O2OService}
 * @version V1.0
 * @address http://www.yingmob.com/
 * @copyright 本内容仅限于淮安爱赢互通科技有限公司内部使用，禁止转发.
 */
package com.lf.tempcore.tempService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.lf.tempcore.R;
import com.lf.tempcore.tempApplication.TempApplication;
import com.lf.tempcore.tempModule.tempDebuger.Debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Author：longf on 2015/12/3 15:45
 * Created by Administrator on 2015/12/3.
 */
public class TempDownloadService extends Service {
    private final String Tag = "DownloadService";
    private final String apkName = "/housekeeping.apk";
    private static final int NOTIFY_ID = 0;
    private int progress;
    private static NotificationManager mNotificationManager;
    Notification mNotification;
    private boolean canceled;
    // 返回的安装包url
    private String apkUrl;
    /* 下载包安装路径 */
    private String savePath;

    private String saveFileName;
    private TempApplication mApplication;
    private boolean serviceIsDestroy = false;
    private MyHandler mHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        private final WeakReference<TempDownloadService> mService;

        MyHandler(TempDownloadService service) {
            mService = new WeakReference<TempDownloadService>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            TempDownloadService service = mService.get();
            if (service != null) {
                switch (msg.what) {
                    case 1:
                        int rate = msg.arg1;
                        service.mApplication.setIsDownload(true);
                        if (rate < 100) {
                            RemoteViews contentview = service.mNotification.contentView;
                            contentview.setTextViewText(R.id.tv_progress, rate
                                    + "%");
                            contentview.setProgressBar(R.id.progressbar, 100, rate,
                                    false);
                        } else {
                            // 下载完毕后变换通知形式
//                            Debug.error("DownloadService apk 下载完成");
                            service.mNotification.contentView = null;

                            File apkfile = new File(service.saveFileName);
                            if (!apkfile.exists()) {
                                return;
                            }
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setDataAndType(
                                    Uri.parse("file://" + apkfile.toString()),
                                    "application/vnd.android.package-archive");
                            service.startActivity(intent);
                            // 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
                            PendingIntent contentIntent = PendingIntent
                                    .getActivity(service, 0, intent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
//						service.mNotification.setLatestEventInfo(service,
//								"下载完成", "文件已下载完毕", contentIntent);
                            if (Build.VERSION.SDK_INT < 16) {//当前sdk版本小于16
                                Notification.Builder builder = new Notification.Builder(service)
                                        .setAutoCancel(true)
                                        .setContentTitle("下载完成")
                                        .setContentText("文件已下载完毕,点击安装")
                                        .setContentIntent(contentIntent)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .setWhen(System.currentTimeMillis());
                                service.mNotification = builder.getNotification();
                            } else {
                                service.mNotification = new Notification.Builder(service).
                                        setAutoCancel(true)
                                        .setContentTitle("下载完成")
                                        .setContentText("文件已下载完毕，点击安装")
                                        .setContentIntent(contentIntent)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setWhen(System.currentTimeMillis())
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .build();
                            }
                            service.mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                            service.serviceIsDestroy = true;
                            service.downLoadThread = null;
                            service.stopSelf();// 停掉服务自身
                        }
                        mNotificationManager.notify(NOTIFY_ID,
                                service.mNotification);
                        break;
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Debug.error(Tag,"onCreate");
        mApplication = TempApplication.getInstance();
        mNotificationManager = (NotificationManager) mApplication.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Debug.error(Tag,"onStartCommand");
        if (intent!=null&&intent.getExtras()!=null){
        apkUrl = intent.getExtras().getString("url");
        savePath = intent.getExtras().getString("savePath");
        Debug.error(Tag,"url="+apkUrl+"savePath="+savePath);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
//        Debug.error(Tag,"onStart");
        if (intent==null||intent.getExtras()==null) {
            return;
        }
        if (downLoadThread == null) {

            progress = 0;
            setUpNotification();
            new Thread() {
                public void run() {
                    // 下载
                    startDownload();
                }

                ;
            }.start();
        } else {
        }

    }

    ;

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
//        Debug.error(Tag, "onDestroy");
        super.onDestroy();
        // 假如被销毁了，无论如何都默认取消了。
        mApplication.setIsDownload(false);
    }

    private void startDownload() {

        canceled = false;
        downloadApk();
    }

    // 通知栏

    /**
     * 创建通知
     */
    private void setUpNotification() {
        RemoteViews contentView = new RemoteViews(getPackageName(),
                R.layout.temp_download_notification_layout);
        contentView.setTextViewText(R.id.name, "正在下载...");
        // 指定个性化视图
//        mNotification.contentView = contentView;
//        long when = System.currentTimeMillis();
//        mNotification = new Notification(icon, tickerText, when);
        // 放置在"正在运行"栏目中
//        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        // 指定内容意图
//        mNotification.contentIntent = contentIntent;
        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT <= 16) {//当前sdk版本小于等于16
            Notification.Builder builder = new Notification.Builder(this)
                    .setContent(contentView)
                    .setAutoCancel(true)
                    .setContentTitle("开始下载")
                    .setContentText("正在下载最新版本")
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true);
            mNotification = builder.getNotification();
        } else {
            mNotification = new Notification.Builder(this).
                    setAutoCancel(true)
                    .setContent(contentView)
                    .setContentTitle("开始下载")
                    .setContentText("正在下载最新版本")
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true)
                    .build();
        }

        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }



    private Thread downLoadThread;

    /**
     * 开始下载apk
     *
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    private int lastRate = 0;
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
//                Debug.error("准备下载");
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
//                Debug.error("开始下载length="+length);
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                saveFileName = savePath+ apkName;
//                saveFileName = savePath + "/" + "AoXinTong.apk";
                File ApkFile = new File(saveFileName);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
//                    Debug.error("count="+count);
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress >= lastRate + 1) {
                        mHandler.sendMessage(msg);
                        lastRate = progress;
                    }
                    if (numread <= 0) {
                        // 下载完了，cancelled也要设置
                        canceled = true;
                        break;
                    }

                    fos.write(buf, 0, numread);
                } while (!canceled);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 清除notifycation
     */
    public static void cancelNotify() {
        mNotificationManager.cancel(NOTIFY_ID);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

}
