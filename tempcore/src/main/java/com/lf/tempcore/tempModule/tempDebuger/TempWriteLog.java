package com.lf.tempcore.tempModule.tempDebuger;


import android.os.Environment;

import com.lf.tempcore.tempModule.tempUtils.TempFileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class TempWriteLog {
    /**
     * 打印错误日志
     *
     * @param str
     *            错误信息
     */
    public static void printString(String str) {
        printString(str, "errorLog.log");
    }
    /**
     * 打印错误日志
     *
     * @param str
     *            错误信息
     */
    public static void printString(String str,String name) {
        printString(str, "/LF_debug/", name);
    }
    /**
     * 打印错误日志
     * @param str 日志内容
     * @param path 日志存储路径
     * @param name 日志存储文件名称
     */
    public static synchronized void printString(String str,String path,String name) {
        Debug.info(str);
//		Log.d(Debug.TAG, str);
        File root=Environment.getExternalStorageDirectory();
        if(!TempFileUtil.isCanUseSD()){
            return;
        }
        BufferedWriter output=null;
        try {
            File f=new File(root,path);
            if(!f.exists()){
                f.mkdirs();
            }
//			File file = new File(f,name);
            File file = new File(getParentFile(f),name);
            if (!file.exists()) {
                file.createNewFile();
            }
            output = new BufferedWriter(new FileWriter(file,
                    true));
            output.append(format.format(Calendar.getInstance().getTime()));
            output.append("\n\n");
            output.write(str);
            output.append("\n\n");
            output.flush();
        } catch (Exception e) {
//			if(this.debug)
            e.printStackTrace();
        }finally{
            if(output!=null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 获取私有文件
     * @param path
     * @return
     */
    private static long oldTime=0;
    private static long diverTime=6*60*60*1000;
    private static String parentName;//父目录名称
    private static final SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");

    private static File getParentFile(File f){
//		File f=new File(path);
        if(TempValidateTool.isEmptString(parentName)||(System.currentTimeMillis()-oldTime)>diverTime){
            oldTime=System.currentTimeMillis();
            parentName=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            if(f.listFiles().length>3){
                File[] fs=f.listFiles(new FilenameFilter() {

                    @Override
                    public boolean accept(File dir, String filename) {
                        return !"download".equals(filename);
                    }
                });
                Arrays.sort(fs, new TempFileUtil.FileLastModifSort());
                for(int i=fs.length-1;i>3;i--){
                    TempFileUtil.deleteFile(fs[i]);
                }
            }
        }
        File nf=new File(f,parentName);
        if(!nf.exists()){
            nf.mkdirs();
        }
        return nf;
    }
}

