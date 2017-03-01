package com.lf.tempcore.tempModule.previewComponments.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.lf.tempcore.R;
import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempModule.photoview.PhotoView;
import com.lf.tempcore.tempModule.photoview.PhotoViewAttacher;
import com.lf.tempcore.tempModule.previewComponments.HackyViewPager;
import com.lf.tempcore.tempModule.previewComponments.ImageLoaders;
import com.lf.tempcore.tempModule.previewComponments.model.ImageBDInfo;
import com.lf.tempcore.tempModule.previewComponments.model.ImageInfo;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



/**
 * Created by longf on 2016/3/25.
 */
public class ActImagePreview extends TempActivity implements ViewPager.OnPageChangeListener {
    private Toast mToast;
    protected ImageView showimg;

    private final Spring mSpring = SpringSystem
            .create()
            .createSpring()
            .addListener(new ExampleSpringListener());

    private RelativeLayout MainView;

    protected ImageBDInfo bdInfo;
    protected ImageInfo imageInfo;
    private float size, size_h;

    private float img_w;
    private float img_h;

    //原图高
    private float y_img_h;
    protected float to_x = 0;
    protected float to_y = 0;
    private float tx;
    private float ty;
    private int statusBarHeight;
    private int titleBarHeight;

    private int index = 0;
    private ViewPager viewpager;
    private ArrayList<ImageInfo> ImgList;

    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private SamplePagerAdapter pagerAdapter;

    private float moveheight;
    private int type;//0：单点；1：listView进入；2：gridView进入；3：混合进入；-1：不用动画
    private int columnSize;
//    private LinearLayout AddLayout;
//    private View moveView;
    private TextView image_indicator;
    private RelativeLayout addrelative;


    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_image_preview_layout);

        MainView = (RelativeLayout)findViewById(R.id.MainView);
        viewpager = (HackyViewPager) findViewById(R.id.bi_viewpager);
//        AddLayout = (LinearLayout)findViewById(R.id.AddLayout);
//        moveView = (View)findViewById(R.id.moveView);
        image_indicator = (TextView) findViewById(R.id.image_indicator);
        addrelative = (RelativeLayout)findViewById(R.id.addrelative);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void bindValues() {
        initData();
    }

    @Override
    protected void setListeners() {
        viewpager.setOnPageChangeListener(ActImagePreview.this);
    }


    @Nullable
    @Override
    protected void OnViewClicked(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        moveView.setX(dip2px(5) + dip2px(10) * position + dip2px(10) * positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        image_indicator.setText(String.format("%d/%d", position+1, ImgList.size()));
        if (showimg == null){
            return;
        }
        ImageInfo info = ImgList.get(position);
        ImageLoaders.setsendimg(info.url, showimg);
        if (type == 1){
            int move_index = position - index;
            to_y = move_index * moveheight;
        }else if (type == 2){
            int	a = index / columnSize;
            int b = index % columnSize;
            int a1 = position / columnSize;
            int b1 = position % columnSize;
            to_y = (a1 - a) * moveheight + (a1 - a) * dip2px(2);
            to_x = (b1 - b) * moveheight + (b1 - b) * dip2px(2);
        }else if (type == 3){
            int	a = index / columnSize;
            int b = index % columnSize;
            int a1 = position / columnSize;
            int b1 = position % columnSize;
            to_y = (a1 - a) * moveheight + (a1 - a) * dip2px(1);
            to_x = (b1 - b) * moveheight + (b1 - b) * dip2px(1);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ExampleSpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            double CurrentValue = spring.getCurrentValue();
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size);
            float mapy = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size_h);
            showimg.setScaleX(mappedValue);
            showimg.setScaleY(mapy);
            if (CurrentValue == 1) {
                EndSoring();
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (type==-1){
            finish();
        }else{
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                viewpager.setVisibility(View.GONE);
                if (showimg!=null)
                showimg.setVisibility(View.VISIBLE);
                setShowimage();
            }
        }

        return true;
    }
    protected void EndSoring() {
        viewpager.setVisibility(View.VISIBLE);
        showimg.setVisibility(View.GONE);
    }
    protected  void EndMove(){
        finish();
    }
    public void initData() {
        index = getIntent().getIntExtra("index", 0);
        type = getIntent().getIntExtra("type",-1);
        columnSize = getIntent().getIntExtra("columnSize",0);
        ImgList = (ArrayList<ImageInfo>)getIntent().getSerializableExtra("data");
        Log.e("1",ImgList.size()+"数量");
        imageInfo = ImgList.get(index);
        bdInfo = (ImageBDInfo)getIntent().getSerializableExtra("bdinfo");
        AddInstructionsView();//添加指示器
        image_indicator.setText(String.format("%d/%d", index+1, ImgList.size()));
        pagerAdapter = new SamplePagerAdapter();
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(index);

//        moveView.setX(dip2px(10) * index);
        if (ImgList.size() == 0){
            addrelative.setVisibility(View.GONE);
        }
        if (type==-1){
            viewpager.setVisibility(View.VISIBLE);
            return;
        }else if (type == 1){
            moveheight = dip2px(70);
        }else if (type == 2){
            moveheight = (getWidth() - columnSize * dip2px(2))/columnSize;
            Log.i("lf","moveheight="+moveheight);
            Log.i("lf","columnSize * dip2px(2)="+columnSize * dip2px(2));
        }else if (type == 3){
            moveheight = (getWidth() - dip2px(80) - dip2px(2))/columnSize;
        }
        getValue();

    }
    protected void getValue() {
        showimg = new ImageView(this);
        showimg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoaders.setsendimg(imageInfo.url, showimg);
        img_w = bdInfo.width;
        img_h = bdInfo.height;
        size = getWidth() / img_w;
        Log.i("lf","img_w="+img_w);
        Log.i("lf","Width="+getWidth());
        // Wait for layout.
        y_img_h = imageInfo.height * getWidth() / imageInfo.width;
//        y_img_h = img_w * getWidth() / img_w;
        size_h = y_img_h / img_h;
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((int) bdInfo.width, (int) bdInfo.height);
        showimg.setLayoutParams(p);
        p.setMargins((int) bdInfo.x, (int) bdInfo.y, (int) (getWidth() - (bdInfo.x + bdInfo.width)), (int) (getHeight() - (bdInfo.y + bdInfo.height)));
        MainView.addView(showimg);
        showimg.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setShowimage();
            }
        }, 300);

    }
    protected void setShowimage(){
        if (mSpring.getEndValue() == 0){
            mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(170, 5));
            tx = getWidth()/2 - (bdInfo.x+img_w/2);
            ty = getHeight()/2-(bdInfo.y+img_h/2);
            Debug.debug("MoveView()");
            MoveView();
            return;
        }
        mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(1, 5));
        mSpring.setEndValue(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                Debug.debug("MoveBackView()");
                MoveBackView();
            }
        }, 300);

    }
    private void MoveView(){

        ObjectAnimator.ofFloat(MainView, "alpha", 0.8f).setDuration(0).start();
        MainView.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(showimg, "translationX", tx).setDuration(200),
                ObjectAnimator.ofFloat(showimg, "translationY", ty).setDuration(200),
                ObjectAnimator.ofFloat(MainView, "alpha", 1).setDuration(200)

        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                showimg.setScaleType(ImageView.ScaleType.FIT_XY);
                mSpring.setEndValue(1);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();

    }

    private void MoveBackView(){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(showimg, "translationX", to_x).setDuration(200),
                ObjectAnimator.ofFloat(showimg, "translationY", to_y).setDuration(200)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                showimg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                EndMove();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
    }

    private class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(loadedImage);
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ImgList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            String path = ImgList.get(position).url;
            ImageLoader.getInstance().displayImage(path, photoView, options,
                    animateFirstListener);
            // Now just add PhotoView to ViewPager and return it
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

                @Override
                public void onViewTap(View arg0, float arg1, float arg2) {
                    if (type==-1){
                       finish();
                    }else{
                        viewpager.setVisibility(View.GONE);
                        showimg.setVisibility(View.VISIBLE);
                        setShowimage();
                    }
//                    finish();
                }
            });
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private void AddInstructionsView(){
//        for (int i = 0; i < ImgList.size() ; i++){
//            View addview = new View(ActImagePreview.this);
//            addview.setBackgroundColor(0xffffffff);
//            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(dip2px(5),dip2px(5));
//            addview.setLayoutParams(p);
//            p.setMargins(dip2px(5),0,0,0);
//            AddLayout.addView(addview);
//        }
    }
}
