package com.lf.tempcore.tempModule.tempPhotoPick;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by longf on 2016/6/13.
 */
public class TempPKParams {
    public static final String CROP_TYPE = "image/*";
    public static final String OUTPUT_FORMAT = Bitmap.CompressFormat.JPEG.toString();

    public static final int DEFAULT_ASPECT = 1;
    public static final int DEFAULT_OUTPUT = 300;
    public static final int DEFAULT_COMPRESS_WIDTH = 640;
    public static final int DEFAULT_COMPRESS_HEIGHT = 854;
    public static final int DEFAULT_COMPRESS_QUALITY = 40;
    public Uri uri;

    public String type;
    public String outputFormat;

    public boolean crop;
    public boolean scale;
    public boolean returnData;
    public boolean noFaceDetection;
    public boolean scaleUpIfNeeded;
    public boolean compress;

    public int aspectX;
    public int aspectY;

    public int outputX;
    public int outputY;

    public int compressWidth;
    public int compressHeight;
    public int compressQuality;

    public TempPKParams() {
        uri = TempPKHelper.makeUri(TempPKHelper.TEMP_FILE_NAME);
        type = CROP_TYPE;
        outputFormat = OUTPUT_FORMAT;
        crop = false;
        scale = true;
        compress=true;
        returnData = false;
        noFaceDetection = true;
        scaleUpIfNeeded = true;
        compressWidth =DEFAULT_COMPRESS_WIDTH;
        compressHeight =DEFAULT_COMPRESS_HEIGHT;
        compressQuality =DEFAULT_COMPRESS_QUALITY;
        aspectX = DEFAULT_ASPECT;
        aspectY = DEFAULT_ASPECT;
        outputX = DEFAULT_OUTPUT;
        outputY = DEFAULT_OUTPUT;
    }
}
