/**
 * @projectName ${O2OService}
 * @version V1.0
 * @address http://www.yingmob.com/
 * @copyright 本内容仅限于淮安爱赢互通科技有限公司内部使用，禁止转发.
 */

package com.lf.tempcore.tempViews;

import android.support.v4.view.ViewPager;

/**
 * A PageIndicator is responsible to show an visual indicator on the total views
 * number and the current visible view.
 */
public interface TempCirclePageIndicator extends ViewPager.OnPageChangeListener {
	/**
	 * Bind the indicator to a ViewPager.
	 * 
	 * @param view
	 */
	void setViewPager(ViewPager view);

	/**
	 * Bind the indicator to a ViewPager.
	 * 
	 * @param view
	 * @param initialPosition
	 */
	void setViewPager(ViewPager view, int initialPosition);

	/**
	 * <p>
	 * Set the current page of both the ViewPager and indicator.
	 * </p>
	 * 
	 * <p>
	 * This <strong>must</strong> be used if you need to set the page before the
	 * views are drawn on screen (e.g., default start page).
	 * </p>
	 * 
	 * @param item
	 */
	void setCurrentItem(int item);

	/**
	 * Set a page change listener which will receive forwarded events.
	 * 
	 * @param listener
	 */
	void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

	/**
	 * Notify the indicator that the fragment list has changed.
	 */
	void notifyDataSetChanged();
}
