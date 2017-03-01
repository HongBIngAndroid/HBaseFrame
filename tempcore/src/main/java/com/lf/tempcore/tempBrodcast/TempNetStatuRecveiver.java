package com.lf.tempcore.tempBrodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lf.tempcore.tempApplication.TempApplication;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempUtils.TempNetUtils;

/**网络状态改变接收器
 * Created by longf on 2016/6/1.
 */
public class TempNetStatuRecveiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Debug.info("TempNetConnectionRecv", "网络状态改变");
        TempApplication.getInstance().setNetType(TempNetUtils.getNetType(context));
    }
}
