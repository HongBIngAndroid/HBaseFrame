package com.lf.tempcore.tempFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by longf on 2016/5/11.
 */
public abstract class TempBaseFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initViews(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        bundleValues();
    }

    /**
     * 初始化 Priority 1
     */
    protected abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    /**
     * 对控件设置监听器 Priority 2
     */
    protected abstract void setListeners();

    /**
     * 绑定参数值 Priority 3
     */
    protected abstract void bundleValues();
    protected  abstract void OnViewClicked(View v);
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
