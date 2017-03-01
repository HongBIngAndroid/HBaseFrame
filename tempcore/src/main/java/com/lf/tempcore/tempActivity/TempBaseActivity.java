package com.lf.tempcore.tempActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * 基础activity，每个Activity都必须继承此activity
 * created by Longf on 2015/1/21 11:42
 */
public abstract class TempBaseActivity extends AutoLayoutActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(savedInstanceState);
    }
    protected abstract  void setContentView(Bundle savedInstanceState);
    /**
     * 初始化 Priority 1
     */
    protected abstract void findViews();

    /**
     * 对控件设置监听器 Priority 2
     */
    protected abstract void setListeners();

    /**
     * 绑定参数值 Priority 3
     */
    protected abstract void bindValues();

    /**View点击事件注册
     * @param v
     */
    protected  abstract void OnViewClicked(View v);
    /**
     * 当布局内容改变时重新寻找控件并设置相应监听器
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                findViews();
                setListeners();
                bindValues();

            }
        });
    }
}
