package com.lf.tempcore.tempResponse;

/**
 * Created by longf on 2016/1/25.
 */
public class TempResponse {

    /**
     * flag : 1
     * msg : 查询个人资料成功
     * result : {"museId":"5","museSex":"2","museImage":"0201605171408038261.jpg","musePhone":"13983574928","museAddress":"","museEmail":"","museNickName":"从你的全世界路过_甜甜","museTrueName":"","museBirthday":"2016-05-20 00:00:00.0"}
     */

    private int flag;
    private String msg;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
