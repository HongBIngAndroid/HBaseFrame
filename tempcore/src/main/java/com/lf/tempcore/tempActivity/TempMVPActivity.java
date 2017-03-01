package com.lf.tempcore.tempActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lf.tempcore.R;
import com.lf.tempcore.tempViews.TempCustomProgressDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by longf on 2016/4/25.
 */
public abstract class TempMVPActivity extends AutoLayoutActivity {
    private WeakReference<TempMVPActivity> mContext = new WeakReference<>(TempMVPActivity.this);

    /**
     * 等待加载对话框
     */
    private TempCustomProgressDialog mProgressDailog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContent(savedInstanceState);
        ButterKnife.bind(this);
        getExtraData();
        setListener();
        bindingData();

    }
    protected abstract  void setContent(Bundle savedInstanceState);
    protected abstract  void getExtraData();
    protected abstract  void setListener();
    protected abstract  void bindingData();


    /**View点击事件注册
     * @param v
     */
    protected  abstract void OnViewClicked(View v);
    public TempMVPActivity getTempContext() {
        if (mContext.get() == null) {
            mContext = new WeakReference<>(TempMVPActivity.this);
        }
        return mContext.get();
    }
    public void performBackClicked(){
        onBackPressed();
    }
//    /**
//     * 导航返回上一页
//     */
//    private void returnBack() {
//        View back = findViewById(R.id.actionbar_back);
//
//        if (back != null) {
//            back.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onBackPressed();
//                }
//            });
//        }
//    }

    /**
     *  显示等待加载对话框
     * 默认不允许触摸隐藏
     */
    private void showProgressDialog() {
        if (mProgressDailog != null) {
            mProgressDailog.show();
        }
    }

    /**显示等待加载对话框
     * @param shouldCanceledOnTouchOutside 允许触摸隐藏
     */
    protected void showProgress(boolean shouldCanceledOnTouchOutside) {

        if (mProgressDailog != null) {
            mProgressDailog.setCanceledOnTouchOutside(shouldCanceledOnTouchOutside);
            showProgressDialog();
        } else {
            mProgressDailog = new TempCustomProgressDialog(this, getResources().getString(R.string.temp_loading));
            mProgressDailog.setCanceledOnTouchOutside(shouldCanceledOnTouchOutside);
            showProgressDialog();
        }
    }


    /**
     * 消失等待对话框
     */
    protected void dismissProgress() {
        if (mProgressDailog != null) {
            mProgressDailog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDailog!=null){
            mProgressDailog.dismiss();
            mProgressDailog=null;
        }
    }
    protected void showConnectedFaildToast(){
        showToast("连接失败,请稍后重试！");
    }
    protected void showToast(String msg){
        if (mContext.get()!=null){
            Toast.makeText( mContext.get(),msg, Toast.LENGTH_SHORT).show();
        }

    }
}
