package com.lf.tempcore.tempRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.lf.tempcore.R;
import com.lf.tempcore.tempModule.tempDebuger.Debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by longf on 2016/7/9.
 */
public abstract class TempRVCommonAdapter<T> extends RecyclerView.Adapter<TempRVHolder>
{
    private final String TAG = "TempRVCommonAdapter";
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected TempRVEvent mEventDelegate;
    protected ArrayList<TempRVItemView> headers = new ArrayList<>();
    protected ArrayList<TempRVItemView> footers = new ArrayList<>();
    private final Object mLock = new Object();
    private boolean mNotifyOnChange = true;


    RecyclerView.AdapterDataObserver mObserver;
    private  final int DELAY = 138;
    private int mLastPosition = -1;

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    public TempRVCommonAdapter(Context context, int layoutId, List<T> datas)
    {
//        mContext = context;
//        mInflater = LayoutInflater.from(context);
//        mLayoutId = layoutId;
//        mDatas = datas;
        init(context, layoutId, datas);
    }
    public TempRVCommonAdapter(Context context,int layoutId)
    {
        init(context, layoutId, new ArrayList<T>());
    }
    public TempRVCommonAdapter(Context context,int layoutId, T[] objects)
    {
        init(context,layoutId, Arrays.asList(objects));
    }
    private void init(Context context ,int layoutId, List<T> objects) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = objects;
//        setNoMoreLayout();
    }

    @Deprecated
    @Override
    public TempRVHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        View view = createSpViewByType( parent,viewType);
        if (view!=null){
            return new TempRVHolder(mContext,view);
        }
        return onTempCreateViewHolder(parent ,viewType);
    }

    public TempRVHolder onTempCreateViewHolder( ViewGroup parent, int viewType){
        TempRVHolder viewHolder = TempRVHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    /**获取recyclerView position
     * @param viewHolder
     * @return
     */
    protected int getRealPosition(TempRVHolder viewHolder)
    {
        return viewHolder.getAdapterPosition();
    }

    /**获取recyclerView data相对位置
     * @param viewHolder
     * @return
     */
    protected int getRelatePosition(TempRVHolder viewHolder){
        return getRealPosition(viewHolder)-headers.size();
    }

    protected boolean isEnabled(int viewType)
    {
        return true;
    }


    protected void setListener(final ViewGroup parent, final TempRVHolder viewHolder, int viewType)
    {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    int position = getRealPosition(viewHolder)-headers.size();
                    mOnItemClickListener.onItemClick(parent, v, getData().get(position),position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    int position = getRealPosition(viewHolder)-headers.size();
                    return mOnItemClickListener.onItemLongClick(parent, v, getData().get(position),position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(TempRVHolder holder, int position)
    {
        if (headers.size()!=0 && position<headers.size()){
            headers.get(position).bindItemValues(holder.itemView);
//            bindHeaderValues(headers.get(position));
//            headers.get(position).onBindView(holder.itemView);
            return ;
        }

        int i = position - headers.size() - getData().size();
        if (footers.size()!=0 && i>=0){
            footers.get(i).bindItemValues(holder.itemView);
            /*if (footers.get(i) instanceof TempRVLoadMoreFooter){
                switch (((TempRVLoadMoreFooter)footers.get(i)).getFlag()){
                    case TempRVLoadMoreFooter.ShowMore:
                        ((TempRVEventDefault)getEventDelegate()).onMoreViewShowed();
                        break;
                    case TempRVLoadMoreFooter.ShowError:
                        ((TempRVEventDefault)getEventDelegate()).onErrorViewShowed();
                        break;
                }
//                ((TempRVLoadMoreFooter)footers.get(i))
            }else{
                bindFooterValues(footers.get(i));
            }*/

            return ;
        }
        holder.updatePosition(position);
        bindItemValues(holder, getData().get(getRelatePosition(holder)));
//        showItemAnim(holder.getConvertView(),position);//添加进入动画
    }

    public abstract void bindItemValues(TempRVHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return getData().size()+headers.size()+footers.size();
    }

    private View createSpViewByType(ViewGroup parent,int viewType){
        Debug.info(TAG,"createSpViewByType viewType="+viewType);
        for (TempRVItemView headerView:headers){
            if (headerView.hashCode() == viewType){
                View view = headerView.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams()!=null)
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                else
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }
        for (TempRVItemView footerview:footers){
            if (footerview.hashCode() == viewType){
                View view = footerview.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams()!=null)
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                else
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }
        return null;
    }
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        if (headers.size()!=0){
            if (position<headers.size())return headers.get(position).hashCode();
        }
        if (footers.size()!=0){
            int i = position - headers.size() - getData().size();
            if (i >= 0){
                return footers.get(i).hashCode();
            }
        }
        return getTempViewType(position-headers.size());
    }
    public int getTempViewType(int position){
        return 0;
    }
    /**
     * 获取Data item个数
     * @return
     */
    public int getCount(){
        return getData()==null?0:getData().size();
    }
    /**
     *  对item 添加进入动画
     * @param view
     * @param position
     */
    public void showItemAnim(final View view, final int position) {
        final Context context = view.getContext();
        if (position > mLastPosition) {
            view.setAlpha(0);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(context,
                            R.anim.temp_slide_in_right);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override public void onAnimationStart(Animation animation) {
                            view.setAlpha(1);
                        }


                        @Override public void onAnimationEnd(Animation animation) {}


                        @Override public void onAnimationRepeat(Animation animation) {}
                    });
                    view.startAnimation(animation);
                }
            },DELAY * position);
            mLastPosition = position;
        }
    }
    TempRVEvent getEventDelegate(){
        if (mEventDelegate == null)mEventDelegate  = new TempRVEventDefault(this);
        return mEventDelegate;
    }

    /**加载更多
     * @param res
     * @param listener
     * @return
     */
    public View setMore(final int res, final OnLoadMoreListener listener){
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View v = LayoutInflater.from(getContext()).inflate(res, container);
        getEventDelegate().setMore(v, listener);
        return container;
    }

    /**加载更多
     * @param view
     * @param listener
     * @return
     */
    public View setMore(final View view,OnLoadMoreListener listener){
        getEventDelegate().setMore(view, listener);
        return view;
    }

    /**加载更多提供默认布局
     * @param listener
     * @return
     */
    public View setMore(OnLoadMoreListener listener){
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(R.layout.temp_rv_load_more, container);
        getEventDelegate().setMore(container, listener);
        return container;
    }
    /**设置没有更多提示布局
     * @param res
     * @return
     */
    public View setNoMoreLayout(final int res) {
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(res, container);
        getEventDelegate().setNoMore(container);
        return container;
    }
    /**设置没有更多默认提示布局
     * @return
     */
    public View setNoMoreLayout() {
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(R.layout.temp_rv_no_more, container);
        getEventDelegate().setNoMore(container);
        return container;
    }
    /**设置没有更多提示布局
     * @param view
     * @return
     */
    public View setNoMoreLayout(final View view) {
        getEventDelegate().setNoMore(view);
        return view;
    }

    /**设置错误提示布局
     * @param res
     * @return
     */
    public View setErrorLayout(final int res) {
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(res, container);
        getEventDelegate().setErrorMore(container);
        return container;
    }

    /**设置错误提示布局
     * @param view
     * @return
     */
    public View setErrorLayout(final View view) {
        getEventDelegate().setErrorMore(view);
        return view;
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (observer instanceof TempRefreshRecyclerView.TempRVDataObserver){
            mObserver = observer;
        }else {
            super.registerAdapterDataObserver(observer);
        }
    }

    public void stopMore(){
        if (mEventDelegate == null)throw new NullPointerException("You should invoking setLoadMore() first");
        mEventDelegate.stopLoadMore();
    }

    public void pauseMore(){
        if (mEventDelegate == null)throw new NullPointerException("You should invoking setLoadMore() first");
        mEventDelegate.pauseLoadMore();
    }

    public void resumeMore(){
        if (mEventDelegate == null)throw new NullPointerException("You should invoking setLoadMore() first");
        mEventDelegate.resumeLoadMore();
    }
    public List<T> getData(){

        return mDatas;
    }
    public void addHeader(TempRVItemView view){
        if (view==null)throw new NullPointerException("ItemView can't be null");
        headers.add(view);
        notifyItemInserted(footers.size()-1);
    }

    public void addFooter(TempRVItemView view){
        if (view==null)throw new NullPointerException("ItemView can't be null");
        footers.add(view);
        notifyItemInserted(headers.size()+getCount()+footers.size()-1);
    }

    public void removeAllHeader(){
        int count = headers.size();
        headers.clear();
        notifyItemRangeRemoved(0,count);
    }

    public void removeAllFooter(){
        int count = footers.size();
        footers.clear();
        notifyItemRangeRemoved(headers.size()+getCount(),count);
    }
    public TempRVItemView getHeader(int index){
        return headers.get(index);
    }

    public TempRVItemView getFooter(int index){
        return footers.get(index);
    }

    public int getHeaderCount(){return headers.size();}

    public int getFooterCount(){return footers.size();}

    public void removeHeader(TempRVHolder view){
        int position = headers.indexOf(view);
        headers.remove(view);
        notifyItemRemoved(position);
    }

    public void removeFooter(TempRVHolder view){
        int position = headers.size()+getCount()+footers.indexOf(view);
        footers.remove(view);
        notifyItemRemoved(position);
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(T object) {
        if (mEventDelegate!=null)mEventDelegate.addData(object == null ? 0 : 1);
        if (object!=null){
            synchronized (mLock) {
                getData().add(object);
            }
        }
        if (mObserver!=null)mObserver.onItemRangeInserted(getCount()+1,1);
        if (mNotifyOnChange) notifyItemInserted(headers.size()+getCount()+1);
//        Debug.info("add notifyItemInserted "+(headers.size()+getCount()+1));
    }
    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     */
    public void addAll(Collection<? extends T> collection) {
        if (mEventDelegate!=null)mEventDelegate.addData(collection == null ? 0 : collection.size());
        if (collection!=null&&collection.size()!=0){
            synchronized (mLock) {
                getData().addAll(collection);
            }
        }
        int dataCount = collection==null?0:collection.size();
        if (mObserver!=null)mObserver.onItemRangeInserted(getCount()-dataCount+1,dataCount);
        if (mNotifyOnChange) notifyItemRangeInserted(headers.size()+getCount()-dataCount+1,dataCount);
//        Debug.info("addAll notifyItemRangeInserted "+(headers.size()+getCount()-dataCount+1)+","+(dataCount));

    }
    public void updateRefresh(Collection<? extends T> collection){
        clear();
        addAll(collection);
    }

    public void updateRefresh(T[] items){
        clear();
        addAll(items);
    }

    public void updateLoadMore(Collection<? extends T> collection){
        addAll(collection);
    }

    public void updateLoadMore(T[] items){
        addAll(items);
    }
    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(T[] items) {
        if (mEventDelegate!=null)mEventDelegate.addData(items==null?0:items.length);
        if (items!=null&&items.length!=0) {
            synchronized (mLock) {
                Collections.addAll(getData(), items);
            }
        }
        int dataCount = items==null?0:items.length;
        if (mObserver!=null)mObserver.onItemRangeInserted(getCount()-dataCount+1,dataCount);
        if (mNotifyOnChange) notifyItemRangeInserted(headers.size()+getCount()-dataCount+1,dataCount);
//        Debug.info("addAll notifyItemRangeInserted "+((headers.size()+getCount()-dataCount+1)+","+(dataCount)));
    }

    /**
     * 插入，不会触发任何事情
     *
     * @param object The object to insert into the array.
     * @param index The index at which the object must be inserted.
     */
    public void insert(T object, int index) {
        synchronized (mLock) {
            getData().add(index, object);
        }
        if (mObserver!=null)mObserver.onItemRangeInserted(index,1);
        if (mNotifyOnChange) notifyItemInserted(headers.size()+index+1);
//        Debug.info("insert notifyItemRangeInserted "+(headers.size()+index+1));
    }

    /**
     * 插入数组，不会触发任何事情
     *
     * @param object The object to insert into the array.
     * @param index The index at which the object must be inserted.
     */
    public void insertAll(T[] object, int index) {
        synchronized (mLock) {
            getData().addAll(index, Arrays.asList(object));
        }
        int dataCount = object==null?0:object.length;
        if (mObserver!=null)mObserver.onItemRangeInserted(index+1,dataCount);
        if (mNotifyOnChange) notifyItemRangeInserted(headers.size()+index+1,dataCount);
//        Debug.info("insertAll notifyItemRangeInserted "+((headers.size()+index+1)+","+(dataCount)));
    }

    /**
     * 插入数组，不会触发任何事情
     *
     * @param object The object to insert into the array.
     * @param index The index at which the object must be inserted.
     */
    public void insertAll(Collection<? extends T> object, int index) {
        synchronized (mLock) {
            getData().addAll(index, object);
        }
        int dataCount = object==null?0:object.size();
        if (mObserver!=null)mObserver.onItemRangeInserted(index+1,dataCount);
        if (mNotifyOnChange) notifyItemRangeInserted(headers.size()+index+1,dataCount);
//        Debug.info("insertAll notifyItemRangeInserted "+((headers.size()+index+1)+","+(dataCount)));
    }

    /**
     * 删除，不会触发任何事情
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        int position = getData().indexOf(object);
        synchronized (mLock) {
            if (getData().remove(object)){
                if (mObserver!=null)mObserver.onItemRangeRemoved(position,1);
                if (mNotifyOnChange) notifyItemRemoved(headers.size()+position);
//                Debug.info("remove notifyItemRemoved "+(headers.size()+position));
            }
        }
    }

    /**
     * 删除，不会触发任何事情
     *
     * @param position The position of the object to remove.
     */
    public void remove(int position) {
        synchronized (mLock) {
            getData().remove(position);
        }
        if (mObserver!=null)mObserver.onItemRangeRemoved(position,1);
        if (mNotifyOnChange) notifyItemRemoved(headers.size()+position);
//        Debug.info("remove notifyItemRemoved "+(headers.size()+position));
    }


    /**
     * 触发清空
     */
    public void clear() {
        int count = getData().size();
        if (mEventDelegate!=null)mEventDelegate.clear();
        synchronized (mLock) {
            getData().clear();
        }
        if (mObserver!=null)mObserver.onItemRangeRemoved(0,count);
        if (mNotifyOnChange) notifyItemRangeRemoved(headers.size(),count);
//        Debug.info("clear notifyItemRangeRemoved "+(headers.size())+","+(count));
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained
     *        in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            Collections.sort(getData(), comparator);
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

}

