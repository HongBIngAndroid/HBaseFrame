package com.lf.tempcore.tempModule.tempUtils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/11/15.
 */
public class TempFormatUtil {

    /**
     * double类型保留N位小数转换成字符串
     * @param v 被转换的数据
     * @param retain 需要保留多少位小数必须大于等于0
     * @return
     */
    public static String doubleToString(double v,int retain) {
        if (v == 0f)
            return "0.00";
        if (retain < 0) {
            throw new IllegalArgumentException("retain can not be negative");
        }
        if (retain == 0) {
            int temp = (int) v;
            return String.valueOf(temp);
        }
        StringBuilder builder = new StringBuilder("0.");
        for (int i=0;i<retain;i++) {
            builder.append("0");
        }
        DecimalFormat format = new DecimalFormat(builder.toString());
        return format.format(v);
    }

    /**
     * double类型保留N位小数转换成字符串
     * @param v 被转换的数据
     * @param retain 需要保留多少位小数必须大于等于0
     * @return
     */
    public static String floatToString(float v,int retain) {
        if (v == 0f)
            return "0.00";
        if (retain < 0) {
            throw new IllegalArgumentException("retain can not be negative");
        }
        if (retain == 0) {
            int temp = (int) v;
            return String.valueOf(temp);
        }
        StringBuilder builder = new StringBuilder("0.");
        for (int i=0;i<retain;i++) {
            builder.append("0");
        }
        DecimalFormat format = new DecimalFormat(builder.toString());
        return format.format(v);
    }

}
