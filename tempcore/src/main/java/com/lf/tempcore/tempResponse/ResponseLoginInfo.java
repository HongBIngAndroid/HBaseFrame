package com.lf.tempcore.tempResponse;

/**
 * Created by Administrator on 2016/5/14.
 */
public class ResponseLoginInfo {

    /**
     * flag : 1
     * msg : 登录成功
     * result : {"mgNumber":0,"mgqNumber":0,"mgqtNumber":0,"museDepart":"","museEducation":"","museId":3620692658483957,"museIdentify":"","museIdolNum":0,"museImToken":"LBR6TCncZTm65bf3CHFSq0BIb60M0Qs6GReMYDjEyT4oJGOL83cD6iOP6rm7Y90JEC7jjAhVqvzIyoX29ZXoMyVtrB484nkwylAmh0/x5cg=","museImage":"","museIsReal":0,"museNickName":"口腔派_4928","museOnlineTag":"6070efea-234c-4b87-8ce9-963fd1db98c4","musePhone":"13983574928","musePwd":"e10adc3949ba59abbe56e057f20f883e","museUserName":"13983574928","museWechatOpenid":""}
     */

    private int flag;
    private String msg;
    /**
     * mgNumber : 0
     * mgqNumber : 0
     * mgqtNumber : 0
     * museDepart :
     * museEducation :
     * museId : 3620692658483957
     * museIdentify :
     * museIdolNum : 0
     * museImToken : LBR6TCncZTm65bf3CHFSq0BIb60M0Qs6GReMYDjEyT4oJGOL83cD6iOP6rm7Y90JEC7jjAhVqvzIyoX29ZXoMyVtrB484nkwylAmh0/x5cg=
     * museImage :
     * museIsReal : 0
     * museNickName : 口腔派_4928
     * museOnlineTag : 6070efea-234c-4b87-8ce9-963fd1db98c4
     * musePhone : 13983574928
     * musePwd : e10adc3949ba59abbe56e057f20f883e
     * museUserName : 13983574928
     * museWechatOpenid :
     */

    private ResultEntity result;

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

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String mgNumber;
        private String mgqNumber;
        private String mgqtNumber;
        private String museDepart;
        private String museEducation;
        private long museId;
        private String museIdentify;
        private String museIdolNum;
        private String museImToken;
        private String museImage;
        private int museIsReal;
        private String museNickName;
        private String museOnlineTag;
        private String musePhone;
        private String musePwd;
        private String museUserName;
        private String museWechatOpenid;

        public String getMgNumber() {
            return mgNumber;
        }

        public void setMgNumber(String mgNumber) {
            this.mgNumber = mgNumber;
        }

        public String getMgqNumber() {
            return mgqNumber;
        }

        public void setMgqNumber(String mgqNumber) {
            this.mgqNumber = mgqNumber;
        }

        public String getMgqtNumber() {
            return mgqtNumber;
        }

        public void setMgqtNumber(String mgqtNumber) {
            this.mgqtNumber = mgqtNumber;
        }

        public String getMuseDepart() {
            return museDepart;
        }

        public void setMuseDepart(String museDepart) {
            this.museDepart = museDepart;
        }

        public String getMuseEducation() {
            return museEducation;
        }

        public void setMuseEducation(String museEducation) {
            this.museEducation = museEducation;
        }

        public long getMuseId() {
            return museId;
        }

        public void setMuseId(long museId) {
            this.museId = museId;
        }

        public String getMuseIdentify() {
            return museIdentify;
        }

        public void setMuseIdentify(String museIdentify) {
            this.museIdentify = museIdentify;
        }

        public String getMuseIdolNum() {
            return museIdolNum;
        }

        public void setMuseIdolNum(String museIdolNum) {
            this.museIdolNum = museIdolNum;
        }

        public String getMuseImToken() {
            return museImToken;
        }

        public void setMuseImToken(String museImToken) {
            this.museImToken = museImToken;
        }

        public String getMuseImage() {
            return museImage;
        }

        public void setMuseImage(String museImage) {
            this.museImage = museImage;
        }

        public int getMuseIsReal() {
            return museIsReal;
        }

        public void setMuseIsReal(int museIsReal) {
            this.museIsReal = museIsReal;
        }

        public String getMuseNickName() {
            return museNickName;
        }

        public void setMuseNickName(String museNickName) {
            this.museNickName = museNickName;
        }

        public String getMuseOnlineTag() {
            return museOnlineTag;
        }

        public void setMuseOnlineTag(String museOnlineTag) {
            this.museOnlineTag = museOnlineTag;
        }

        public String getMusePhone() {
            return musePhone;
        }

        public void setMusePhone(String musePhone) {
            this.musePhone = musePhone;
        }

        public String getMusePwd() {
            return musePwd;
        }

        public void setMusePwd(String musePwd) {
            this.musePwd = musePwd;
        }

        public String getMuseUserName() {
            return museUserName;
        }

        public void setMuseUserName(String museUserName) {
            this.museUserName = museUserName;
        }

        public String getMuseWechatOpenid() {
            return museWechatOpenid;
        }

        public void setMuseWechatOpenid(String museWechatOpenid) {
            this.museWechatOpenid = museWechatOpenid;
        }
    }
}
