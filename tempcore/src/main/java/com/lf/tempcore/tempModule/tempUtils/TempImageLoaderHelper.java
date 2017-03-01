package com.lf.tempcore.tempModule.tempUtils;

import android.graphics.Bitmap;

import com.lf.tempcore.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by longf on 2016/1/29.
 */
public class TempImageLoaderHelper {
    public static DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisk(true)
            .showImageForEmptyUri(R.drawable.temp_image_default)
            .showImageOnFail(R.drawable.temp_image_load_fail)
            .showImageOnLoading(R.drawable.temp_image_default)
            .considerExifParams(true) //是否考虑JPEG图像EXIF参数（旋转，翻转）
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .build();
}
