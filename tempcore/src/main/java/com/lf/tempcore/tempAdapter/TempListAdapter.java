package com.lf.tempcore.tempAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

public abstract class TempListAdapter<D, H> extends
        BaseAdapter {
    private List<D> data;
    WeakReference<Context> mContext;
    private LayoutInflater inflater;
    //    private Context context;
    private int srcId;

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }

    public TempListAdapter(List<D> data, Context context, int layoutId) {
        super();
        this.data = data;
        mContext = new WeakReference<>(context);
//        this.context = context;
        this.srcId = layoutId;
    }

    /**
     * 创建holder
     *
     * @return 返回holder对象
     */
    protected abstract H createHolder();

    /**
     * 初始化holder的成员
     */
    protected abstract void initHolder(int position, View v, H holder);

    /**
     * 绑定holer值用来显示
     */
    public abstract void bunldHolderValue(int position, H holder, D item);

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data == null ? 0 : data.size();
    }

    @Override
    public D getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View rootView = convertView;
        H holder;
        if (rootView != null) {
            holder = (H) rootView.getTag();
        } else {
            holder = createHolder();
            rootView = this.getLayoutInflater().inflate(srcId, null);
            initHolder(position, rootView, holder);
            rootView.setTag(holder);
        }
        bunldHolderValue(position, holder, getItem(position));
        return rootView;
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
//    protected String makeImageUrl(String url) {
//        if (TextUtils.isEmpty(url)) {
//            return "";
//        }
//        return context.getResources().getString(R.string.image_head) + url;
//    }
//
//    protected String makeImageUrl(String url, int width, int height) {
//        if (TextUtils.isEmpty(url))
//            return "";
//        if (TextUtils.isEmpty(width + "") || TextUtils.isEmpty(height + ""))
//            return context.getResources().getString(R.string.image_head) + url + "&imgwidth=" + width + "&imgheight=" + height;
//        return context.getResources().getString(R.string.image_head) + url;
//    }
//    /**
//     * ImageView绑定加载图片
//     *
//     * @param imageView
//     * @param url       图片地址 example：file:///sdcard/test.gif; assets://test.gif; http://www.test.com/x
//     */
//    protected void bindImageView(ImageView imageView, String url) {
////        Debug.info("BindImageUrl=" + url);
//        x.image().bind(imageView, url);
//    }
//
//    /**
//     * ImageView绑定加载图片
//     *
//     * @param imageView
//     * @param url       图片地址 example：file:///sdcard/test.gif; assets://test.gif; http://www.test.com/x
//     * @param callback  图片加载回调
//     */
//    protected void bindImageView(ImageView imageView, String url, Callback.CommonCallback<Drawable> callback) {
////        Debug.info("BindImageUrl=" + url);
//        x.image().bind(imageView, url, callback);
//    }
//
//    /**
//     * ImageView绑定加载图片
//     *
//     * @param imageView
//     * @param url       图片地址 example：file:///sdcard/test.gif; assets://test.gif; http://www.test.com/x
//     * @param option    加载设置类
//     */
//    protected void bindImageView(ImageView imageView, String url, ImageOptions option) {
////        Debug.info("BindImageUrl=" + url);
//        x.image().bind(imageView, url, option);
//    }
//
//    /**
//     * ImageView绑定加载图片
//     *
//     * @param imageView
//     * @param url       图片地址 example：file:///sdcard/test.gif; assets://test.gif; http://www.test.com/x
//     * @param option    加载设置类
//     * @param callback  加载回调
//     */
//    protected void binImageView(ImageView imageView, String url, ImageOptions option, Callback.CommonCallback<Drawable> callback) {
////        Debug.info("BindImageUrl=" + url);
//        x.image().bind(imageView, url, option, callback);
//    }
}
