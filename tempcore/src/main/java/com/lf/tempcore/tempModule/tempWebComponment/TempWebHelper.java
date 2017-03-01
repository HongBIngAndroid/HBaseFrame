package com.lf.tempcore.tempModule.tempWebComponment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lf.tempcore.tempModule.tempDebuger.Debug;


/**
 * Author：longf on 2015/11/28 15:35
 * Created by Administrator on 2015/11/28.
 */
public class TempWebHelper {
    private WebView mWebView;
    private Context mContext;
    public TempWebHelper(WebView webView, Context context){
        this.mWebView = webView;
        this.mContext = context;
    }

    public void callJsWithoutArg(String JsMethodName){
        mWebView.loadUrl("javascript:+"+JsMethodName+"()");
//        mWebView.loadUrl("javascript:+"+JsMethodName+"("+"'5'"+")");
    }
    /**
     * loadWeb 加载页面
     *
     * @param url
     *            void
     *
     */
    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    public void loadWeb(String url) {
        Debug.error("web_url=" + url);
        try {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(
                    true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(
                    true);
            mWebView.addJavascriptInterface(new TempJavaScriptInterface(
                    mContext), "android");
            // mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            // 清除页面缓存（临时用）
            mWebView.loadUrl(url);
            // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
            // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
            mWebView.setWebViewClient(new WebViewClient() {

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // view.clearCache(true);
                    view.loadUrl(url);
                    return true;
                };

                public void onPageStarted(WebView view, String url,
                                          android.graphics.Bitmap favicon) {
                };

                public void onPageFinished(WebView view, String url) {
                };

                public void onFormResubmission(WebView view,
                                               android.os.Message dontResend, android.os.Message resend) {
                };
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void destroy(){
        if (mWebView!=null){
            mWebView.destroy();
            mWebView=null;
        }
        if (mContext!=null){
            mContext=null;
        }
    }
}
