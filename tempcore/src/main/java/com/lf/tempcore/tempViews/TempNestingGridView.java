package com.lf.tempcore.tempViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Author：longf on 2015/11/12 15:48
 * Created by Administrator on 2015/11/12.
 */
public class TempNestingGridView extends GridView {
    public TempNestingGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public TempNestingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public TempNestingGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
//        if(ev.getAction() == MotionEvent.ACTION_MOVE){
//            return true;//禁止Gridview进行滑动
//        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
