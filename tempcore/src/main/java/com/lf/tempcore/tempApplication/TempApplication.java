package com.lf.tempcore.tempApplication;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import com.amap.api.location.AMapLocation;
import com.lf.tempcore.R;
import com.lf.tempcore.tempConfig.CountDownConfig;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempUtils.TempAssetsUtil;
import com.lf.tempcore.tempModule.tempUtils.TempNetUtils;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by longf on 2016/5/7.
 */

public class TempApplication extends Application{
    private final String TAG="MainApplication";
    public static Typeface typeFace;
    private boolean isDownload = false;
    private Map<String, Object> mExtralData;
    private String orderId;
    public   static DisplayImageOptions defaultOptions;
    /**
     * 网络状态
     */
    private TempNetType netType;
//    private TempAssetsUtil mTempAssetsUtil;
//    private MainApplication  instance;
//    private DbManager mDBManager_City=null;
//    private DBManager_City mDBManager_City;
//    org.xutils.core.module.db.DBManager_City db;
//    private DBManager_City mDBManager_City;
    /**
     * 系统应用实例
     */
    private static TempApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        this.initSDCard();
//        initXUtils();
        initImageLoader();
//        initAssetsUtil("tempDB");
//        DBLoginConfig.sf_saveLoginState(false);
//        initUserInfoAccessable();
//        initSnappydb();
//        setTypeface();
        startTimer();
    }

    /**
     * 初始化XTtils工具
     */
//    private void initXUtils() {
//        x.Ext.setDebug(BuildConfig.DEBUG);
//        x.Ext.init(getInstance());
//    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void initAssetsUtil(String filename){
        TempAssetsUtil assetsUtil = new TempAssetsUtil(getApplicationContext());
//         filename = "db_area";
        if (assetsUtil.initAssetsFile(filename)){
            assetsUtil.copyFilesFassets(getApplicationContext(),filename,getApplicationContext().getFilesDir().getAbsolutePath()+ "/"+filename+"/");
        }
    }
    /**
     * 获取实例
     *
     * @return
     */
    public static TempApplication getInstance() {
        return instance;
    }

    /**
     * 初始化系统运行数据
     */
//    protected void initSDCard() {
//        if (!FileUtil.isSDCardEnable()) {
//            Toast.makeText(getApplicationContext(), "sd卡不可用，请检查设置！",
//                    Toast.LENGTH_SHORT).show();
//        } else if (FileUtil.getFreeSpaceOnSD() < 40) {
//            Toast.makeText(getApplicationContext(),
//                    "sd卡内存不足，快去清理一下吧！", Toast.LENGTH_SHORT).show();
//        }
//    }

    /**
     * 初始化imageLoader
     */
    private void initImageLoader() {
//        File dir  = FileUtil.createDir(SdCardConfig.SDCARD_CACHE_PATH);

        defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.temp_image_default)
                .showImageOnFail(R.drawable.temp_image_load_fail)
                .showImageOnLoading(R.drawable.temp_image_default)
                .considerExifParams(true) //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(800))
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(8)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheExtraOptions(1080, 1920)
                .memoryCacheSize(2 * 1024 * 1024)
                .defaultDisplayImageOptions(defaultOptions);
        ImageLoader.getInstance().init(builder.build());
    }


    public static DisplayImageOptions getOptions() {
        return defaultOptions;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {

            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }
    public TempNetType getNetType() {
        if (netType==null){
            return TempNetUtils.getNetType(getApplicationContext());
        }
        return netType;
    }

    public void setNetType(TempNetType netType) {
        Debug.info(TAG,"设置网络状态="+netType.getCode());
        this.netType = netType;
    }

    /**
     * 存放对象到application，用来临时保存activity之间传递对象数据，存放下一次数据是，上一次的数据会被清除
     *
     * @param key
     * @param data
     */
    public void putExtralsObj(String key, Object data) {
        if (this.mExtralData == null) {
            this.mExtralData = new HashMap<String, Object>();
        }
        if (!this.mExtralData.isEmpty()) {
            this.mExtralData.clear();
        }
        this.mExtralData.put(key, data);
    }

    /**
     * 获取到临时存放到application的
     *
     * @param key
     * @return
     */
    public Object getExtralObj(String key) {
        if (this.mExtralData != null && !this.mExtralData.isEmpty()) {
            return this.mExtralData.get(key);
        }
        return null;
    }

    /**
     * 清除临时存放到application的数据
     */
    public void clearExtralObj() {
        if (this.mExtralData != null && !this.mExtralData.isEmpty()) {
            this.mExtralData.clear();
            this.mExtralData = null;
        }
    }

//    public void setTypeface(){
//
//
//        File dir = new File(SdCardConfig.SDCARD_CACHE_PATH);
//        if (!dir.exists()){
//            dir.mkdirs();
//        }
//        File file = new File(SdCardConfig.SDCARD_CACHE_PATH+"aaa.ttf");
//        if (file.exists()){
//            try
//            {
//                Debug.info("文件存在直接打开");
//                typeFace = Typeface.createFromFile(file);
//                Field field = Typeface.class.getDeclaredField("SERIF");
//                field.setAccessible(true);
//                field.set(null, typeFace);
//                Debug.info("设置字体完成");
//            }
//            catch (NoSuchFieldException e)
//            {
//                e.printStackTrace();
//            }
//            catch (IllegalAccessException e)
//            {
//                e.printStackTrace();
//            }
//        }else{
//            InputStream is =getResources().openRawResource(R.raw.aaa);
//            Debug.info("文件不存在开始存文件");
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(file);
//                int count = 0;
//                boolean canceled =false;
//                byte buf[] = new byte[1024];
//                do {
//                    int numread = is.read(buf);
//                    count += numread;
//                    if (numread <= 0) {
//                        canceled = true;
//                        break;
//                    }
//                    fos.write(buf, 0, numread);
//                } while (!canceled);// 点击取消就停止下载.
//                Debug.info("结束存文件,设置字体");
//                typeFace = Typeface.createFromFile(file);
//                Field field = Typeface.class.getDeclaredField("SERIF");
//                field.setAccessible(true);
//                field.set(null, typeFace);
//                Debug.info("设置字体完成");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }catch (IOException e){
//                e.printStackTrace();
//            }catch(NoSuchFieldException e){
//                e.printStackTrace();
//            }catch (IllegalAccessException e){
//                e.printStackTrace();
//            }
//        }
//
//
//    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }


    /**
     * 初始化用户信息
     */
//    public  void initUserInfoAccessable(){
//        if (!DBLoginConfig.sf_getLoginState()){
//            Debug.info("用户未登录，初始化登录信息");
//            RespActLogin info = new RespActLogin();
//            RespActLogin.ResultEntity loginInfo = new RespActLogin.ResultEntity();
//            loginInfo.setSuseId(0);
//            loginInfo.setSuseLoginName("");
//            loginInfo.setSuseType(0);
//            info.setResult(loginInfo);
//            DBLoginConfig.sf_saveLoginInfo(info);
//            DBLoginConfig.sf_saveUsernameAndPsw("","");
//            UserInfoAccessableImpl.getInstance().setUserId(Long.valueOf(DBLoginConfig.sf_getSueid() == null ? "0" : DBLoginConfig.sf_getSueid()));
//            UserInfoAccessableImpl.getInstance().setUsername(DBLoginConfig.sf_getUserAcount());
//            UserInfoAccessableImpl.getInstance().setLocalUserIdentity("");
//            UserInfoAccessableImpl.getInstance().setEncryptPassword("");
//
//        }else{
//            //已经登录
//            Debug.info("用户已经登录，初始化登录信息");
//            UserInfoAccessableImpl.getInstance().setUserId(Long.valueOf(DBLoginConfig.sf_getSueid()==null?"0":DBLoginConfig.sf_getSueid()));
//            UserInfoAccessableImpl.getInstance().setUsername(DBLoginConfig.sf_getUserAcount());
//            TelephonyManager tm = (TelephonyManager)(getInstance().getSystemService(Context.TELEPHONY_SERVICE));
//            UserInfoAccessableImpl.getInstance().setLocalUserIdentity(tm.getDeviceId());
//            UserInfoAccessableImpl.getInstance().setEncryptPassword(com.chenling.ibds.android.common.SHA1.hex_sha1(DBLoginConfig.sf_getUsernameAndPwd().get(DBLoginConfig.LOGIN_PASSWORD)));
//        }
//
//    }



    public String getGetLongitude() {
        return getLongitude;
    }

    public void setGetLongitude(String getLongitude) {

        this.getLongitude = getLongitude;
    }

    /**
     * 系统应用实例
     */

    public String getLongitude;

    public String getGetLatitude() {
        return getLatitude;
    }

    public void setGetLatitude(String getLatitude) {
        this.getLatitude = getLatitude;
    }

    public String getLatitude;
    public AMapLocation mapLocation;
    public  AMapLocation getMapLocation() {

        Debug.error("================="+mapLocation.getLatitude());
        return mapLocation;
    }

    public void setMapLocation(AMapLocation mapLocation) {
        Debug.error("保存MapLocation");
        this.mapLocation = mapLocation;

    }


    private Timer timer;//全局定时器
    private TimerTask task;//全局定时器
    private CountDownConfig mCountDownConfig;
    /**
     * 初始化定时器
     */
    public void startTimer() {
        Debug.info("开启定时");
        if (timer!=null){
            timer.purge();
            timer.cancel();
            timer=null;
        }
        timer = new Timer();
        timer.schedule(getTask(), 1000, getmCountDownConfig().getInterval());
    }
    private TimerTask getTask() {
        if (task!=null){
            task.cancel();
            task=null;
        }
        task = new TimerTask() {
            @Override
            public void run() {
                getmCountDownConfig().down();
            }
        };
        return task;
    }

    public CountDownConfig getmCountDownConfig() {
        if (mCountDownConfig == null) {
            mCountDownConfig = new CountDownConfig();
        }
        return mCountDownConfig;
    }

    public void setmCountDownConfig(CountDownConfig mCountDownConfig) {
        this.mCountDownConfig = mCountDownConfig;
    }

    public void stopTimer() {
        if (timer != null) {
            Debug.info("关闭定时器");
            timer.cancel();
        }
//        NoUserOperationManager manager = new NoUserOperationManager();
//        manager.stop(getBaseContext());
    }

}
