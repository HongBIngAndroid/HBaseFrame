package com.lf.tempcore.tempModule.tempRemotComm;

/**
 * 用户信息访问接口实现
 * Created by Zhijun.Pu on 2016/1/12.
 */
public class TempUserInfoAccessableImpl implements TempUserInfoAccessable {
    private static TempUserInfoAccessableImpl instance = new TempUserInfoAccessableImpl();

    /** 用户名 */
    private String username;
    /** 用户id */
    private Long userId;
    /** android 本地用户标识 */
    private String localUserIdentity;
    /** 编码后的用户密码 */
    private String encryptPassword;

    public static TempUserInfoAccessableImpl getInstance() {
        return instance;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getLocalUserIdentity() {
        return localUserIdentity;
    }

    @Override
    public String getEncryptPassword() {
        return encryptPassword;
    }

    public static void setInstance(TempUserInfoAccessableImpl instance) {
        TempUserInfoAccessableImpl.instance = instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLocalUserIdentity(String localUserIdentity) {
        this.localUserIdentity = localUserIdentity;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }
}
