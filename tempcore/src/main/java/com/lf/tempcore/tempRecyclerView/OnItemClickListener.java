package com.lf.tempcore.tempRecyclerView;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longf on 2016/7/9.
 */
public interface OnItemClickListener<T>
{
    void onItemClick(ViewGroup parent, View view, T t, int position);
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}
