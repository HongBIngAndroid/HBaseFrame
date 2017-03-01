package com.lf.tempcore.tempRecyclerView;

/**
 * Created by longf on 2016/7/9.
 */
public interface TempRVMultiItemTypeSupport<T> {
    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
