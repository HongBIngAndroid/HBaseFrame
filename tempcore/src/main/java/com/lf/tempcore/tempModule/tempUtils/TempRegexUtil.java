package com.lf.tempcore.tempModule.tempUtils;

/**
 * Author：longf on 2015/11/16 13:50
 * Created by Administrator on 2015/11/16.
 */
public class TempRegexUtil {

    /**
     * 检查 email输入是否正确
     * 正确的书写格 式为 username@domain
     * @param value
     * @return
     */
    public boolean checkEmail(String value, int length) {
        return value.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")&&value.length()<=length;
    }

    /**
     * 检查电话输入 是否正确
     * 正确格 式 012-87654321、0123-87654321、0123－7654321
     * @param value
     * @return
     */
    public boolean checkTel(String value) {
        return value.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)");
    }

    /**
     * 检查手机输入 是否正确
     *
     * @param value
     * @return
     */
    public boolean checkMobile(String value) {
        return value.matches("^[1][3,5,7,8]+\\d{9}");
    }

    /**
     * 检查中文名输 入是否正确
     *
     * @param value
     * @return
     */
    public boolean checkChineseName(String value, int length) {
        return value.matches("^[\u4e00-\u9fa5]+$")&&value.length()<=length;
    }
    /**
     * 检查HTML 中首尾空行或空格
     * @param value
     * @return
     */
    public boolean checkBlank(String value){
        return value.matches("^\\s*|\\s*$");
    }
    /**
     * 检查字符串是 否含有HTML标签
     * @param value
     * @return
     */

    public boolean checkHtmlTag(String value){
        return value.matches("<(\\S*?)[^>]*>.*?</\\1>|<.*? />");
    }
    /**
     * 检查URL是 否合法
     * @param value
     * @return
     */
    public boolean checkURL(String value){
        return value.matches("[a-zA-z]+://[^\\s]*");
    }
    /**
     * 检查IP是否 合法
     * @param value
     * @return
     */
    public boolean checkIP(String value){
        return value.matches("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
    }
    /**
     * 检查ID是否 合法，开头必须是大小写字母，其他位可以有大小写字符、数字、下划线
     * @param value
     * @return
     */
    public boolean checkID(String value){
        return value.matches("[a-zA-Z][a-zA-Z0-9_]{4,15}$");
    }
    /**
     * 检查QQ是否 合法，必须是数字，且首位不能为0，最长15位
     * @param value
     * @return
     */

    public boolean checkQQ(String value){
        return value.matches("[1-9][0-9]{4,13}");
    }
    /**
     * 检查邮编是否 合法
     * @param value
     * @return
     */
    public boolean checkPostCode(String value){
        return value.matches("[1-9]\\d{5}(?!\\d)");
    }
    /**
     * 检查身份证是 否合法,15位或18位
     * @param value
     * @return
     */
    public boolean checkIDCard(String value){
        return value.matches("\\d{15}|\\d{18}");
    }
    /**
     * 检查输入是否 超出规定长度
     * @param length
     * @param value
     * @return
     */
    public boolean checkLength(String value, int length) {
        return ((value == null || "".equals(value.trim())) ? 0 : value.length()) <= length;
    }

    /**
     * 检查是否为空 字符串,空：true,不空:false
     *
     * @param value
     * @return
     */
    public boolean checkNull(String value){
        return value == null || "".equals(value.trim());
    }

}
