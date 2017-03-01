package com.lf.tempcore.tempModule.tempWebComponment;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;


/**
 * js 调用android
 *
 * @author xcz
 */
public class TempJavaScriptInterface {

    private JsIngerfaceCallBack callBack;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public TempJavaScriptInterface(Context context) {
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     * onClick="window.android.onClickAndroid('seller')"
     *
     * @param data js点击返回数据
     */
    @JavascriptInterface
    public void onClickAndroid(final String data) {
        if (this.callBack != null) {
            callBack.callBack(data);
        }
    }

    public interface JsIngerfaceCallBack {
        void callBack(String data);
    }

    public void setJsCallBack(JsIngerfaceCallBack callback) {
        this.callBack = callback;
    }
}
