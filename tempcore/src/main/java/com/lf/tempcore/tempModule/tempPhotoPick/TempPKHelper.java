package com.lf.tempcore.tempModule.tempPhotoPick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by longf on 2016/6/13.
 */
public class TempPKHelper {
    public static final String TAG = "TempPKHelper";
    /**
     * request code of Activities or Fragments
     * You will have to change the values of the request codes below if they conflict with your own.
     */
    public static final int TEMP_REQUEST_CODE_CROP = 0X55;
    public static final int TEMP_REQUEST_CODE_CAMERA = 0X56;
    public static final int TEMP_REQUEST_CODE_GALLERY = 0X57;

    public static final String TEMP_FILE_NAME = "temp_cache_file.jpg";
    public static final String TEMP_FILE_NAME_COMPRESSED = "temp_cache_file_compressed.jpg";

    public static Uri makeUri(String name) {
        return Uri
                .fromFile(Environment.getExternalStorageDirectory())
                .buildUpon()
                .appendPath(name)
                .build();
    }

    public static boolean isPhotoCropped(Uri uri) {
        File file = new File(uri.getPath());
        long length = file.length();
        return length > 0;
    }

    /**
     * clear cached file
     *
     * @param uri
     * @return
     */
    public static boolean clearCachedByUri(Uri uri) {
        if (uri == null) return false;

        File file = new File(uri.getPath());
        if (file.exists()) {
            boolean result = file.delete();
            if (result)
                Log.i(TAG, "Cached file cleared.");
            else
                Log.e(TAG, "Failed to clear cached file.");
            return result;
        } else {
            Log.w(TAG, "Trying to clear cached file but it does not exist.");
        }
        return false;
    }

    /**
     * create crop intent
     *
     * @param params
     * @return
     */
    public static Intent makeCropIntent(TempPKParams params) {
        return makeCropIntent("com.android.camera.action.CROP", params);
    }

    /**
     * create gallery intent
     *
     * @param params
     * @return
     */
    public static Intent makeGalleryIntent(TempPKParams params) {
        if (params.crop) {
            return makeCropIntent(Intent.ACTION_PICK, params);
        }
        return new Intent(Intent.ACTION_PICK)
                .setType(params.type)
                .putExtra(MediaStore.EXTRA_OUTPUT, params.uri);
    }

    /**
     * create camera intent
     *
     * @param uri
     * @return
     */
    public static Intent makeCaptureIntent(Uri uri) {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, uri);
    }
    /**
     * create camera intent
     *
     * @param params
     * @return
     */
    public static Intent makeCaptureIntent(TempPKParams params) {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, params.uri);
    }
    private static Intent makeCropIntent(String action, TempPKParams params) {
//       Intent intent = new Intent(action, null);
            return new Intent(action, null)
                    .setDataAndType(params.uri, params.type)
                    //.setType(params.type)
                    .putExtra("crop", params.crop+"")
                    .putExtra("scale", params.scale)
                    .putExtra("aspectX", params.aspectX)
                    .putExtra("aspectY", params.aspectY)
                    .putExtra("outputX", params.outputX)
                    .putExtra("outputY", params.outputY)
                    .putExtra("return-data", params.returnData)
                    .putExtra("outputFormat", params.outputFormat)
                    .putExtra("noFaceDetection", params.noFaceDetection)
                    .putExtra("scaleUpIfNeeded", params.scaleUpIfNeeded)
                    .putExtra(MediaStore.EXTRA_OUTPUT, params.uri);
//        }
//         return new Intent(action)
//                    .setType(params.type)
//                    .putExtra(MediaStore.EXTRA_OUTPUT, params.uri);

    }

    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        if (context == null || uri == null) return null;

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static void onResult(TempPKHandler handler, int requestCode, int resultCode, Intent data) {
        if (handler == null) {
            Log.i(TAG, "the handler is null");
            return;
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            handler.onCancel();
        } else if (resultCode == Activity.RESULT_OK) {
            TempPKParams cropParams = handler.getPkParams();
            if (cropParams == null) {
                handler.onFailed("handler's params must not be null!");
                return;
            }
            switch (requestCode) {
                case TEMP_REQUEST_CODE_CROP:
                    if (data==null){
                        handler.onFailed("onResult callback intent is null!");
                        break;
                    }
//                    Log.i(TAG, "handle CROP data="+data.toString());
//                    String path = TempPKFileUtil.getSmartFilePath(handler.getContext(), data.getData());
//                    Debug.info(TAG,"org path="+path);
//                    if (path==null){
//                        handler.onFailed("the path callback is null!");
//                    }else if (!TempPKFileUtil.copyFile(path, handler.getPkParams().uri.getPath())) {
//                        handler.onFailed("unknown error occurred!");
//                    }else{
                        shouldCamearCompressBitmap(handler);
//                    }
                    break;
                case TEMP_REQUEST_CODE_GALLERY:
                    if (handler.getPkParams().crop){
                        shouldCamearCompressBitmap(handler);
                        break;
                    }
                    if (data==null){
                        handler.onFailed("onResult callback intent is null!");
                        break;
                    }
                    Log.i(TAG, "handle GALLERY path="+data.getData().getPath());
                    String orgPath = TempPKFileUtil.getSmartFilePath(handler.getContext(), data.getData());
                    if (!TempPKFileUtil.copyFile(orgPath, handler.getPkParams().uri.getPath())) {
                        handler.onFailed("unknown error occurred!");
                    }else{
                        shouldCamearCompressBitmap(handler);
                    }
                    break;
                case TEMP_REQUEST_CODE_CAMERA:
                    Log.i(TAG, "handle CAMERA ");
                    if (handler.getPkParams().crop) {
                        Log.i(TAG, "CAMERA start crop");
                        Intent intent = makeCropIntent(handler.getPkParams());
                        Activity context = handler.getContext();
                        if (context != null) {
                            context.startActivityForResult(intent, TEMP_REQUEST_CODE_CROP);
                        } else {
                            handler.onFailed("handler's context must not be null!");
                        }
                    } else {
                        Log.i(TAG, "camera callback");
//                        handler.onSucceed(handler.getPkParams().uri);
                        shouldCamearCompressBitmap(handler);
                    }

                    break;
            }
        }

    }
    private static void shouldCamearCompressBitmap(TempPKHandler handler) {
        if (handler.getPkParams().compress) {
            Log.i(TAG, "compress bitmap");
            Uri originUri = handler.getPkParams().uri;
            Uri compressUri = TempPKHelper.makeUri(TEMP_FILE_NAME_COMPRESSED);
            TempPKCompressUtil.compressBitmapByBitmapFactory(handler.getPkParams(), originUri, compressUri);
            handler.onSucceed(compressUri);
        } else {
            handler.onSucceed(handler.getPkParams().uri);
        }
    }
}
