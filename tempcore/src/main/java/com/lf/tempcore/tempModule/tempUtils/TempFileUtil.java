package com.lf.tempcore.tempModule.tempUtils;


import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;


/**
 * 文件工具
 *
 * @author longhui
 * @creation 2014-6-18
 *
 */
public class TempFileUtil {

    /** MB 单位B */
    private static int MB = 1024 * 1024;
    /**
     * 获取文件大小单位为B
     */
    public static final int SIZETYPE_B = 1;
    /**
     *  获取文件大小单位为KB
     */
    public static final int SIZETYPE_KB = 2;
    /**
     *  获取文件大小单位为MB
     */
    public static final int SIZETYPE_MB = 3;
    /**
     * 获取文件大小单位为GB
     */
    public static final int SIZETYPE_GB = 4;

    /**
     * 计算sdcard上的剩余空间
     */
    public static int getFreeSpaceOnSD() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
                .getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
                .getBlockSize()) / MB;
        return (int) sdFreeMB;
    }
    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
//            Debug.error(e);
        }
        return false;
    }
    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isSDCardEnable() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 根据文件的最后修改时间进行排序
     */
    public static class FileLastModifSort implements Comparator<File> {
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return -1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * 创建目录
     *
     * @param dirpath
     * @return
     */
    public static File createDir(String dirpath) {
//		String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        File dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
    /**
     * 在sd卡上创建文件
     *
     * @param fileName
     * @throws IOException
     */
    public static File createFile(String fileName) {
        File sd = Environment.getExternalStorageDirectory();
        System.out.println("lf>>>>>rootPath=" + sd.getPath());
        File file = new File(sd.getPath() + "/" + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }
    /**
     * 在sd卡上创建文件
     * @param dir
     * @param fileName
     * @throws IOException
     *
     */
    public static File createFile(String dir,String fileName) {
//		File sd = Environment.getExternalStorageDirectory();
//		System.out.println("lf>>>>>rootPath=" + sd.getPath());
        File file = new File( dir+fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
//                Debug.info("创建"+ dir+fileName+"文件夹");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 删除文件
     *
     * @param file
     *            需删除的文件
     */
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (File f : fs) {
                deleteFile(f);
            }
        }
        file.delete();
    }

    /**
     *
     * 描述：读取Assets目录的文件内容
     *
     * @param context
     * @param name
     * @return
     * @throws
     */
    public static String readAssetsByName(Context context, String name,
                                          String encoding) {
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getAssets().open(name));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(), encoding);
        } catch (Exception e) {
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
            }
        }
        return text;
    }
    /**
     * 获取指定文件大小
     * @param filepath
     * @return
     * @throws Exception
     */
    public static long getFileSize(String filepath,int sizeType) throws Exception {
        long size = 0;
        if(filepath == null){
            return 0;
        }
        File file = new File(filepath);
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        }
        switch(sizeType){
            case SIZETYPE_B:
                break;
            case SIZETYPE_KB:
                size /= 1024;
                break;
            case SIZETYPE_MB:
                size = size / (1024*1024);
                break;
            case SIZETYPE_GB:
                size = size / (1024*1024*1024);
                break;
        }
        return size;
    }

    /**
     * 检测网络资源是否存在　
     *
     * @param strUrl
     * @return
     */
    public static boolean isNetFileAvailable(String strUrl) {
        if (null == strUrl) {
            return false;
        }
        InputStream netFileInputStream = null;
        try {
            URL url = new URL(strUrl);
            URLConnection urlConn = url.openConnection();
            netFileInputStream = urlConn.getInputStream();
            if (null != netFileInputStream) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (netFileInputStream != null)
                    netFileInputStream.close();
            } catch (IOException e) {
            }
        }
    }
}
