package com.lf.tempcore.tempModule.tempRemotComm;

/**
 * 本地用户信息访问接口
 * Created by Zhijun.Pu on 2015/12/12.
 */
public interface TempUserInfoAccessable {

    /**
     * 获取用户名
     * @return
     */
    String getUsername();

    /**
     * 获取用户id
     * @return
     */
    Long getUserId();

    /**
     * 获取用户在本地存储的标识符
     * @return
     */
    String getLocalUserIdentity();

    /**
     * 获取用户密码，采用sha1加密过后的
     * @return
     */
    String getEncryptPassword();
}
