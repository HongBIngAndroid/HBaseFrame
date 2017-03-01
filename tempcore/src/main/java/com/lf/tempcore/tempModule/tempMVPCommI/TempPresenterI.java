package com.lf.tempcore.tempModule.tempMVPCommI;

/**
 * Created by longf on 2016/5/6.
 */
public interface TempPresenterI {
    void sendRequest();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
