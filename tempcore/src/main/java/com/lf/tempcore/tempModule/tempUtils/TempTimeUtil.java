package com.lf.tempcore.tempModule.tempUtils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类 注意
 * 
 * JAVA里以毫秒为基准
 * 
 * @author way
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TempTimeUtil extends CountDownTimer {
	private TextView tv;

	public TempTimeUtil(long millisInFuture, long countDownInterval, TextView tv) {
		super(millisInFuture, countDownInterval);
		this.tv = tv;
	}
	public static String converTime(long time) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - time / 1000;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 3 * 24 * 60 * 60) {
			timeStr = getDayTime(time) + " " + getMinTime(time);
		} else if (timeGap > 24 * 2 * 60 * 60) {// 2天以上就返回标准时间
			timeStr = "前天 " + getMinTime(time);
		} else if (timeGap > 24 * 60 * 60) {// 1天-2天
			timeStr = timeGap / (24 * 60 * 60) + "昨天 " + getMinTime(time);
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "今天 " + getMinTime(time);
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "今天 " + getMinTime(time);
		} else {// 1秒钟-59秒钟
			timeStr = "今天 " + getMinTime(time);
		}
		return timeStr;
	}

	public static String getChatTime(long time) {
		return getMinTime(time);
	}

	public static String getPrefix(long time) {
		long currentSeconds = System.currentTimeMillis();
		long timeGap = currentSeconds - time;// 与现在时间差
		String timeStr = null;
		if (timeGap > 24 * 3 * 60 * 60 * 1000) {
			timeStr = getDayTime(time) + " " + getMinTime(time);
		} else if (timeGap > 24 * 2 * 60 * 60 * 1000) {
			timeStr = "前天 " + getMinTime(time);
		} else if (timeGap > 24 * 60 * 60 * 1000) {
			timeStr = "昨天 " + getMinTime(time);
		} else {
			timeStr = "今天 " + getMinTime(time);
		}
		return timeStr;
	}

	public static String getDayTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		return format.format(new Date(time * 1000));
	}

	/**
	 * 时间戳转时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getMinTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		return format.format(new Date(time * 1000)); // 需要乘上1000 2015-1-4
														// 11:39:56
	}

	/**
	 * 时间戳转时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getMinTime2(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time * 1000)); // 需要乘上1000 2015-1-4
														// 11:39:56
	}

	/**
	 * 时间戳转时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getMinTime3(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH时mm分");
		return format.format(new Date(time * 1000)); // 需要乘上1000 2015-1-4
														// 11:39:56
	}

	/**
	 * 时间转时间戳
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static long getTimestamp(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = format.parse(time);
			long t = date.getTime();
			return t / 1000;
		} catch (ParseException e) {
			return 0;
		}

	}

	@Override
	public void onTick(long millisUntilFinished) {
		tv.setClickable(false);
		tv.setText(millisUntilFinished/1000+"秒后重新获取");

	}

	@Override
	public void onFinish() {
		tv.setText("重新获取验证码");
		tv.setClickable(true);

	}
}
