package com.lf.tempcore.tempViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/11/14.
 */
public class TempNestingListView extends ListView{
    public TempNestingListView(Context context) {
        super(context);
    }

    public TempNestingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TempNestingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
