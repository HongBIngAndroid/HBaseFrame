package com.lf.tempcore.tempEnum;

/**
 * Created by longf on 2016/6/1.
 */
public enum TempNetType {
    /**
     * 不可用
     */
    NET_DISABLED(0),
    /**
     * wifi
     */
    NET_WIFI(1),
    /**
     * 移动数据
     */
    NET_MOBILE(2),
    /**
     * 未知
     */
    NET_UNKNOWN(3);
    private int code;

    TempNetType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public TempNetType getTempNetType(int code){
        switch (code) {
            case 0 :
                return NET_DISABLED;
            case 1:
                return NET_WIFI;
            case 2:
                return NET_MOBILE;
            case 3:
                return NET_UNKNOWN;
        }
        return null;
    }

}
