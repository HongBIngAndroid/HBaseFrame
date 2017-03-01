package com.lf.tempcore.tempRecyclerView;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longf on 2016/7/19.
 */
public interface TempRVItemView {
    View onCreateView(ViewGroup parent);
    void bindItemValues(View headerView);
}
