package com.lf.tempcore.tempModule.tempUtils;

import android.content.Context;

import com.lf.tempcore.tempModule.tempDebuger.Debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**拷贝asstes文件夹中的文件到android data/data中
 * Created by longf on 2016/5/19.
 */
public class TempAssetsUtil {
    private Context mContext;

    public TempAssetsUtil(Context context) {
        mContext = context;
    }

    /**
     * @param fileName 文件夹名称
     */
    //每次进入app，遍历assets目录下所有的文件，是否在data/data目录下都已经存在，不存在则拷贝
    public boolean initAssetsFile(String fileName) {
        boolean needCopy = false;
        // 创建data/data目录
        File file = mContext.getFilesDir();
        String path = file.getAbsolutePath() + "/"+fileName+"/";
        // 遍历assets目录下所有的文件，是否在data/data目录下都已经存在
        try {
            String[] fileNames = mContext.getAssets().list(fileName);
            for (int i = 0; fileNames != null && i < fileNames.length; i++) {
//                com.alipay.security.mobile.module.commonutils.FileUtil.createDirs()
                Debug.info("file name="+fileNames[i]);
                File tempFile = new File(path + fileNames[i]);
                if (!tempFile.exists()) {
                    Debug.info("文件不存在，保存文件");
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    return false;
    }

    /**
     * @param context
     * @param oldPath assets中文件夹名称
     * @param newPath dada/data文件夹路径
     */
    //将旧目录中的文件全部复制到新目录
    public  void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {

            // 获取assets目录下的所有文件及目录名
            String fileNames[] = context.getAssets().list(oldPath);

            // 如果是目录名，则将重复调用方法递归地将所有文件
            if (fileNames.length > 0) {
                File file = new File(newPath);
                file.mkdirs();
                for (String fileName : fileNames) {
                    Debug.info(fileName+"创建，进入递归");
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            }
            // 如果是文件，则循环从输入流读取字节写入
            else {
                Debug.info("开始读入文件");
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
