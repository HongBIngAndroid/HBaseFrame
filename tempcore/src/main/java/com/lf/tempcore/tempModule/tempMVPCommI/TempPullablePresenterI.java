package com.lf.tempcore.tempModule.tempMVPCommI;

/**
 * Created by longf on 2016/5/6.
 */
public interface TempPullablePresenterI {
    void requestInit();
    void requestRefresh();
    void requestLoadmore();
}
