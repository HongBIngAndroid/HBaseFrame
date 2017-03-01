package com.lf.tempcore.tempFragment;

import android.os.Build;
import android.widget.Toast;

import com.lf.tempcore.R;
import com.lf.tempcore.tempViews.TempCustomProgressDialog;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by longf on 2016/5/11.
 */
public abstract class TempFragment extends TempBaseFragment{


    private CompositeSubscription mCompositeSubscription;
    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }


    /**
     * 等待加载对话框
     */
    private TempCustomProgressDialog mProgressDailog;
    protected void showConnectedFaildToast(){
        showToast("获取数据失败！");
    }
    protected void showToast(String msg){
        if (getActivity()==null||msg==null){
            return;
        }
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 显示等待加载对话框
     * 默认不允许触摸隐藏
     */
    private void showProgressDialog() {
        if (mProgressDailog != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mProgressDailog.create();
            }
            mProgressDailog.show();
        }
    }
    /**
     * 显示等待加载对话框
     *
     * @param shouldCanceledOnTouchOutside 允许触摸隐藏
     */
    protected void showProgressDialog(boolean shouldCanceledOnTouchOutside) {

        if (mProgressDailog != null) {
            mProgressDailog.setCanceledOnTouchOutside(shouldCanceledOnTouchOutside);
            showProgressDialog();
        } else {
            mProgressDailog = new TempCustomProgressDialog(getActivity(), getResources().getString(R.string.loading));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mProgressDailog.create();
            }
            mProgressDailog.setCanceledOnTouchOutside(shouldCanceledOnTouchOutside);
            showProgressDialog();
        }
    }

    /**
     * 消失等待对话框
     */
    protected void dismissProgressDialog() {
        if (mProgressDailog != null) {
            mProgressDailog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (this.mCompositeSubscription != null) {
//            this.mCompositeSubscription.unsubscribe();
//        }
    }
}
