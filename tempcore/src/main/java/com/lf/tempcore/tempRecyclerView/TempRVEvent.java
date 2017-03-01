package com.lf.tempcore.tempRecyclerView;

import android.view.View;

/**
 * Created by longf on 2016/7/18.
 */
public interface TempRVEvent {
    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, TempRVCommonAdapter.OnLoadMoreListener listener);
    void setNoMore(View view);
    void setErrorMore(View view);
}
