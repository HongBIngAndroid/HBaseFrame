package com.lf.tempcore.tempModule.photoview;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lf.tempcore.R;
import com.lf.tempcore.tempActivity.TempActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 图片预览页面
 * @author wtj
 *
 */
public class ImageViewPreviewActivity extends TempActivity
	implements OnTouchListener{

	@Override
	protected void setContentView(Bundle savedInstanceState) {
		setContentView(R.layout.image_preview);
		imageView = (PhotoView) this.findViewById(R.id.image_preview_iv);
		progressBar = (ProgressBar) this.findViewById(R.id.image_preview_progressBar);
		clear = (ImageView)this.findViewById(R.id.preview_clear);
	}

	@Override
	protected void findViews() {

	}

	@Override
	protected void bindValues() {
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		imgPath = this.getIntent().getStringExtra(IMAGE_PATH);
//初始化数据
		saveMatrix = new Matrix();
		matrix = new Matrix();
		mode = NONE;

		start = new PointF();
		mid = new PointF();
		oldDist = 1f;

		mImageLoader = ImageLoader.getInstance();
		getdata();
	}


	@Nullable
	@Override
	protected void OnViewClicked(View v) {

	}
	public static final String IMAGE_PATH="image_path";
	private ProgressBar progressBar;
	/*
	 * 显示图片
	 */
	private ImageView imageView;
	private ImageView clear;
	/**
	 * 图片路径
	 */
	private String imgPath;
	
	/**
	 * 用于保存缩放的点阵
	 */
	private Matrix saveMatrix;
	private Matrix matrix;
	
	private static final int NONE = 0;// 无样式
	private static final int DRAG = 1;// 拖拽
	private static final int ZOOM = 2;// 缩放
	private int mode ;
	private PointF start;
	private PointF mid;
	
	private float oldDist;
	private Bitmap bitmap;
	private float minScaleR;// 最小缩放比例
	private static final float MAX_SCALE = 4f;// 最大缩放比例
	
	private ImageLoader mImageLoader;
	private PhotoViewAttacher mAttacher;
	


	/**
	 * 对控件设置监听器
	 */
	@Override
	protected void setListeners(){
		imageView.setOnTouchListener(this);
	}

	private void getdata() {

		if (this.imgPath.length() > 4) {
			String url = this.imgPath;
			if(mImageLoader != null){
				mImageLoader.displayImage(url, imageView,
						new ImageLoadingListener() {

							@Override
							public void onLoadingStarted(String imageUri, View view) {
								// TODO Auto-generated method stub
								progressBar.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
								// TODO Auto-generated method stub
								progressBar.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
								// TODO Auto-generated method stub
								progressBar.setVisibility(View.GONE);
								mAttacher = new PhotoViewAttacher(imageView);
								// issuccess = true;
							}

							@Override
							public void onLoadingCancelled(String imageUri, View view) {
								// TODO Auto-generated method stub
								progressBar.setVisibility(View.GONE);
							}
						});
			}
			
		}
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		ImageView view = (ImageView) v;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			saveMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			mode = DRAG;// 一个手指触碰，进行拖动
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(saveMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					matrix.set(saveMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);// 以多点触碰的中点为中心进行缩放
				}
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				saveMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;// 多点触碰，进行缩放
			}
			break;
		}
		view.setImageMatrix(matrix);
		CheckView();
		return true;
	}
	
	/**
	 * 计算两点间距离
	 * @param event
	 * @return
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float)Math.sqrt(x * x + y * y);
	}
	
	/**
	 * 
	 * 两点的中点
	 * @param point
	 * @param event
	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
	
	/**
	 * 限制最大最小缩放比例，自动居中
	 */
	private void CheckView() {
		float p[] = new float[9];
		matrix.getValues(p);
		if (mode == ZOOM) {
			if (p[0] < minScaleR) {
				matrix.setScale(minScaleR, minScaleR);
			}
			if (p[0] > MAX_SCALE) {
				matrix.set(saveMatrix);
			}
		}
	}
	

}
