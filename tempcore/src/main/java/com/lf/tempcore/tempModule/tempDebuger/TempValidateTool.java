package com.lf.tempcore.tempModule.tempDebuger;



import java.util.Calendar;
import java.util.Date;


public class TempValidateTool {

    /**
     * 验证字符串是否为数字
     */
    public boolean isNumeric(String str) {

        return str.matches("(?:-?[1-9]\\d*\\.?\\d*)|-?(0\\.\\d*[1-9])");
    }

    /**
     * 验证Email地址是否正确
     *
     * @param str 验证字符串
     * @return 是否为Email
     */
    public boolean isEmail(String str) {
        return str
                .matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    /**
     * 验证用户性别是否为男
     *
     * @param sex 性别字符串
     * @return true 为男 false 为女
     */
    public boolean isSexMan(String sex) {
        return sex.matches("(^\u7537$)|(^man$)");
    }

    /**
     * 验证两个日期是否为同一天
     *
     * @param d1
     * @param d2
     * @return
     */
    public boolean isTheSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2
                .get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 比较两个日期是否为同一月
     *
     * @param d1
     * @param d2
     * @return
     */
    public boolean isTheSameMonth(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
    }

    /**
     * 检验用户浏览权限
     *
     * @param uidList 允许浏览用户的id字符串 以","隔开
     * @param currUid 当前用户id
     * @param uid     发布者id
     * @return -1 该用户没有权限 0 仅自己可见 1 可以浏览
     */
    public int scanPermission(String uidList, int currUid, int uid) {
        if (currUid == uid) {
            return 1;
        }
        String rex = "(^-1$)|(^-1,)|(^%d$)|(^%d,)|(,%d,)|(,%d$)";
        rex = String.format(rex, currUid, currUid, currUid, currUid);
        if (uidList != null && uidList.matches(rex)) {
            return 1;
        }
        if ("0".equals(uidList)) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 验证当前连接是否为图片连接字符串
     *
     * @param photoUrl 验证图片连接
     * @return
     */
    public boolean isPhotoUrl(String photoUrl) {
        return photoUrl != null && !"".equals(photoUrl);
    }

    /**
     * 验证字符串是否为空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmptString(String str) {
        return str == null || "".equals(str);
    }


}
