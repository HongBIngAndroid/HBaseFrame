package com.lf.tempcore.tempAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by longf on 2016/4/14.
 */
public abstract class TempExpandableListAdapter<D> extends BaseExpandableListAdapter{
    private List<D> data;
    private ExpandableListView a;
    private ListView l;
    WeakReference<Context> mContext;
    private LayoutInflater inflater;
    //    private Context context;
    private int srcId;
    public TempExpandableListAdapter(Context context,List<D> data){
        this.data =data;
        this.mContext  =new WeakReference<>(context);
    }
    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }

    /**
     * 添加新数据
     *
     * @param data 新数据
     */
    public void upDateLoadMore(List<D> data) {
        if (this.data!=null){

            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 刷新数据
     *
     * @param data 刷新数据
     */
    public void upDateReflash(List<D> data) {
//        this.data.clear();
//        this.data.addAll(data);
        if (this.data!=null){
            this.data = data;
            notifyDataSetChanged();
        }
    }
    private LayoutInflater getLayoutInflater() {
//		if (inflater==null) {
//			inflater = LayoutInflater.from(context);
//		}

        return inflater == null ? LayoutInflater.from(getTempContext()) : inflater;
    }

    public Context getTempContext() {
//        if (mContext.get() == null) {
//            mContext = new WeakReference<>(TempActivity.this);
//        }
        return mContext.get();
    }
}
