package com.lf.tempcore.tempModule.tempDataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lf.tempcore.tempModule.tempDebuger.Debug;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**城市数据库
 * Created by longf on 2016/5/19.
 */
public class TempDbAreaHelper {
    private Context mContext;
    //    private CityDb mCityDb;
    private SQLiteDatabase db;
//    String savePath = Environment.getExternalStorageDirectory()
//            .getAbsolutePath() + "/DSZ/cache/";
    private String mDbPath ;

    /**打开默认数据库文件
     * @param cxt
     */
    public TempDbAreaHelper(Context cxt) {
        Debug.info("构造dbhelper");
        this.mContext =cxt;
        this.mDbPath = cxt.getFilesDir().getAbsolutePath()+ "/tempDB/dbarea.db";
        Debug.info("mDbPath="+mDbPath);
        File f = new File(mDbPath);
        if (f.exists()){
            Debug.info("lfdb","数据库文件存在");
        }else{
            Debug.info("lfdb","数据库文件不存在");
        }
//        initDb();
    }

    /**打开给定目录数据库文件
     * @param cxt
     * @param dbPath
     */
    public TempDbAreaHelper(Context cxt,String dbPath) {
        this.mContext =cxt;
        this.mDbPath = dbPath;
//        initDb();
    }
public List<TempAreaBean> getCityByName(String a_name){
    if (db==null){
        db = SQLiteDatabase.openOrCreateDatabase(mDbPath, null);
    }
    Cursor cursor=null;
    try {
        if (db==null){
            Debug.info("db 为空");
        }else{
//                cursor = db.rawQuery("SELECT * FROM sys_area where a_parent_id = ?",new String[]{id});
            Debug.info("查询a_name="+a_name);
//            cursor = db.rawQuery("SELECT a_id , a_name FROM sys_area where a_parent_id = ?",new String[]{id});
//            cursor = db.rawQuery("select * from sys_area where a_name like '%?%'",new String[]{a_name});
            cursor = db.rawQuery("SELECT * FROM sys_area where a_name like ?",new String[]{"%"+a_name+"%"});
//            cursor = db.rawQuery("select * from sys_area where a_parent_id = ?",new String[]{"20238"});
//                cursor =  db.query("sys_area",new String[]{"a_id","a_name"},"where",null,null,null,null);
        }

    }catch (Exception e){
        e.printStackTrace();
    }
    Debug.info("返回 cursor");
    List<TempAreaBean> dataList =new ArrayList<>();
    dataList.clear();
    TempAreaBean bean;
    if (cursor==null){
        Debug.info("cursor 为空");
//            db.close();
        return dataList;
    }
    while (cursor.moveToNext()){ bean = new TempAreaBean();
        bean.setA_id(cursor.getInt(cursor.getColumnIndex("a_id")));
        bean.setA_name(cursor.getString(cursor.getColumnIndex("a_name")));
            bean.setA_parent_id(cursor.getString(cursor.getColumnIndex("a_parent_id")));
            bean.setA_level(cursor.getString(cursor.getColumnIndex("a_level")));
            bean.setA_relation(cursor.getString(cursor.getColumnIndex("a_relation")));
//            bean.setA_description(cursor.getString(cursor.getColumnIndex("a_description")));
//            bean.setA_sort(cursor.getString(cursor.getColumnIndex("a_sort")));
//            bean.setA_lag(cursor.getString(cursor.getColumnIndex("a_lag")));
//            bean.setA_lng(cursor.getString(cursor.getColumnIndex("a_lng")));
            bean.setA_pinyin(cursor.getString(cursor.getColumnIndex("a_pinyin")));
        bean.toString();
        dataList.add(bean);
    }
    Debug.info("保存完成----------");
    cursor.close();

    return dataList;
}
    /**根据id
     * @param id
     * @return
     */
    public List<TempAreaBean> getCityDataByParentId(String id){
        if (db==null){
            db = SQLiteDatabase.openOrCreateDatabase(mDbPath, null);
        }
        Cursor cursor=null;
        try {
            if (db==null){
                Debug.info("db 为空");
            }else{
//                cursor = db.rawQuery("SELECT * FROM sys_area where a_parent_id = ?",new String[]{id});
                Debug.info("查询id="+id);
                cursor = db.rawQuery("SELECT a_id , a_name FROM sys_area where a_parent_id = ?",new String[]{id});
//                cursor =  db.query("sys_area",new String[]{"a_id","a_name"},"where",null,null,null,null);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        Debug.info("返回 cursor");
        List<TempAreaBean> dataList =new ArrayList<>();
        dataList.clear();
        TempAreaBean bean;
        if (cursor==null){
            Debug.info("cursor 为空");
//            db.close();
            return dataList;
        }
        while (cursor.moveToNext()){ bean = new TempAreaBean();
            bean.setA_id(cursor.getInt(cursor.getColumnIndex("a_id")));
            bean.setA_name(cursor.getString(cursor.getColumnIndex("a_name")));
//            bean.setA_parent_id(cursor.getString(cursor.getColumnIndex("a_parent_id")));
//            bean.setA_level(cursor.getString(cursor.getColumnIndex("a_level")));
//            bean.setA_relation(cursor.getString(cursor.getColumnIndex("a_relation")));
//            bean.setA_description(cursor.getString(cursor.getColumnIndex("a_description")));
//            bean.setA_sort(cursor.getString(cursor.getColumnIndex("a_sort")));
//            bean.setA_lag(cursor.getString(cursor.getColumnIndex("a_lag")));
//            bean.setA_lng(cursor.getString(cursor.getColumnIndex("a_lng")));
//            bean.setA_pinyin(cursor.getString(cursor.getColumnIndex("a_pinyin")));
            bean.toString();
            dataList.add(bean);
        }
        Debug.info("保存完成----------");
        cursor.close();

        return dataList;
//        Observable.create(new Observable.OnSubscribe<CityBean>() {
//            @Override
//            public void call(Subscriber<? super CityBean> subscriber) {
//            }
//        })
//                .observeOn(AndroidSchedulers.mainThread()).subscribe();

    }
    /*private void initDb(){
        savePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/DSZ/cache/";
        saveFileName = savePath + "dbarea.db";
        File ApkFile = new File(saveFileName);
        if (!ApkFile.exists()){
            Debug.info("该数据库文件不存在,创建改数据库文件");
            try{
                boolean canceled =false;
                File dir = new File(savePath);
                if (!dir.exists()) {
                    Debug.info("创建数据库文件夹");
                    if (dir.mkdirs()){
                        Debug.info("创建数据库文件夹成功");
                    }

                }
                InputStream is = mContext.getResources().openRawResource(R.raw.dbarea);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];
                Debug.info("开始存文件");
                do {
                    int numread = is.read(buf);
                    count += numread;
                    if (numread <= 0) {
                        canceled = true;
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!canceled);// 点击取消就停止下载.
                Debug.info("结束存文件");
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    public void destory(){
        if (mContext!=null){
            mContext=null;
        }
        if (db!=null){
            db.close();
            db=null;
        }
    }
}
