package com.lf.tempcore.tempRecyclerView;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by longf on 2016/7/9.
 */
public abstract class TempMultiItemCommonAdapter<T> extends TempRVCommonAdapter<T>
{

    protected TempRVMultiItemTypeSupport<T> mMultiItemTypeSupport;

    public TempMultiItemCommonAdapter(Context context, List<T> datas,
                                  TempRVMultiItemTypeSupport<T> multiItemTypeSupport)
    {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;

        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }


    @Override
    public int getTempViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
        return super.getTempViewType(position);
    }


    @Override
    public TempRVHolder onTempCreateViewHolder(ViewGroup parent, int viewType) {

        if (mMultiItemTypeSupport == null) return super.onTempCreateViewHolder(parent, viewType);
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        TempRVHolder holder = TempRVHolder.get(mContext, null, parent, layoutId, -1);
        setListener(parent, holder, viewType);
        return holder;
    }
}