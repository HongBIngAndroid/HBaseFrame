package com.lf.tempcore.tempModule.tempPhotoPick;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by longf on 2016/6/13.
 */
public interface TempPKHandler {
    void onSucceed(Uri uri);

    void onCancel();

    void onFailed(String message);

    TempPKParams getPkParams();

    Activity getContext();
}
