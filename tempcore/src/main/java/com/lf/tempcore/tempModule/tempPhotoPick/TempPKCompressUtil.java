package com.lf.tempcore.tempModule.tempPhotoPick;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by longf on 2016/6/13.
 */
public class TempPKCompressUtil {
    public static final String TAG="TempPKCompressUtil";
    private static int DEFAULT_QUALITY = 95;
    /**压缩bitmap 压缩质量DEFAULT_QUALITY=95
     * @param bit 被压缩的bitmap
     * @param fileName 压缩后文件路径 exp：data/data/packagename/xxx.jpg
     * @param optimize 压缩优化 建议 true
     *
     */
   /* public static void compressBitmap(Bitmap bit, String fileName,
                                      boolean optimize) {
        compressBitmap(bit, DEFAULT_QUALITY, fileName, optimize);

    }*/

    /**压缩bitmap
     * @param bit 被压缩的bitmap
     * @param quality 压缩质量 exp：100，90，80。。。
     * @param fileName 压缩后文件路径 exp：data/data/packagename/xxx.jpg
     * @param optimize 压缩优化 建议 true
     */
   /* public static void compressBitmap(Bitmap bit, int quality, String fileName,
                                      boolean optimize) {
        Log.d("native", "compress of native");

        // if (bit.getConfig() != Config.ARGB_8888) {
        Bitmap result = null;

        result = Bitmap.createBitmap(bit.getWidth() / 2, bit.getHeight() / 2,
                Bitmap.Config.ARGB_8888);// 缩小2倍
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bit.getWidth(), bit.getHeight());// original
        rect = new Rect(0, 0, bit.getWidth() / 3, bit.getHeight() / 3);// 缩小3倍
        canvas.drawBitmap(bit, null, rect, null);
//        saveBitmap(result, quality, fileName, optimize);
        result.recycle();
        // } else {
        // saveBitmap(bit, quality, fileName, optimize);
        // }

    }*/

    /**通过第三方工具来压缩bitmap
     * @param cropParams
     * @param originUri
     * @param compressUri
     * @param quality
     * @param optimize
     */
   /* public static void compressBitmapByUtil(TempPKParams cropParams, Uri originUri, Uri compressUri,int quality,boolean optimize){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
//        Bitmap orgBitmap = BitmapFactory.decodeFile(originUri.getPath(), options);
        compressBitmap(BitmapFactory.decodeFile(originUri.getPath(), options),quality,compressUri.getPath(),true);
    }*/
    /**通过第三方工具来压缩bitmap
     * @param cropParams
     * @param originUri
     * @param compressUri
     *
     */
   /* public static void compressBitmapByUtil(TempPKParams cropParams, Uri originUri, Uri compressUri,boolean optimize){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
//        Bitmap orgBitmap = BitmapFactory.decodeFile(originUri.getPath(), options);
        compressBitmap(BitmapFactory.decodeFile(originUri.getPath(), options),compressUri.getPath(),true);
    }*/
    public static void compressBitmapByBitmapFactory(TempPKParams cropParams, Uri originUri, Uri compressUri) {
        Bitmap bitmap = null;
        OutputStream out = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originUri.getPath(), options);
            // Calculate inSampleSize
            int minSideLength = cropParams.compressWidth > cropParams.compressHeight
                    ? cropParams.compressHeight : cropParams.compressWidth;
            options.inSampleSize = computeSampleSize(options, minSideLength, cropParams.compressWidth * cropParams.compressHeight);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(originUri.getPath(), options);
            File compressFile = new File(compressUri.getPath());
            if (!compressFile.exists()) {
                boolean result = compressFile.createNewFile();
                Log.d(TAG, "Target " + compressUri + " not exist, create a new one " + result);
            }
            out = new FileOutputStream(compressFile);
            boolean result = bitmap.compress(Bitmap.CompressFormat.JPEG, cropParams.compressQuality, out);
            Log.d(TAG, "Compress bitmap " + (result ? "succeed" : "failed"));
        } catch (Exception e) {
            Log.e(TAG, "compressInputStreamToOutputStream", e);
        } finally {
            if (bitmap != null)
                bitmap.recycle();
                System.gc();
            try {
                if (out != null)
                    out.close();
            } catch (IOException ignore) {
            }
        }

    }
    public static void compressBitmap(TempPKParams params, String fileName,
                                      boolean optimize) {

    }
    /*private static void saveBitmap(Bitmap bit, int quality, String fileName,
                                   boolean optimize) {
        Debug.info(TAG,"saveBitmap");
        compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality,
                fileName.getBytes(), optimize);

    }*/
    public static void compressImageFile(TempPKParams cropParams, Uri originUri, Uri compressUri) {
        Bitmap bitmap = null;
        OutputStream out = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originUri.getPath(), options);
            // Calculate inSampleSize
            int minSideLength = cropParams.compressWidth > cropParams.compressHeight
                    ? cropParams.compressHeight : cropParams.compressWidth;
            options.inSampleSize = computeSampleSize(options, minSideLength, cropParams.compressWidth * cropParams.compressHeight);
            // Decode bitmap with inSampleSize set
            Log.i(TAG,"compressImage inSampleSize="+options.inSampleSize);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(originUri.getPath(), options);
            File compressFile = new File(compressUri.getPath());
            if (!compressFile.exists()) {
                boolean result = compressFile.createNewFile();
                Log.d(TAG, "Target " + compressUri + " not exist, create a new one " + result);
            }
            out = new FileOutputStream(compressFile);
            boolean result = bitmap.compress(Bitmap.CompressFormat.JPEG, cropParams.compressQuality, out);
            Log.d(TAG, "Compress bitmap " + (result ? "succeed" : "failed"));
        } catch (Exception e) {
            Log.e(TAG, "compressInputStreamToOutputStream", e);
        } finally {
            if (bitmap != null)
                bitmap.recycle();
                System.gc();
            try {
                if (out != null)
                    out.close();
            } catch (IOException ignore) {
            }
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

  /*  private static native String compressBitmap(Bitmap bit, int w, int h,
                                                int quality, byte[] fileNameBytes, boolean optimize);

    static {
        Debug.info(TAG,"load so文件");
        System.loadLibrary("jpegbither");
        System.loadLibrary("bitherjni");

    }*/

}
