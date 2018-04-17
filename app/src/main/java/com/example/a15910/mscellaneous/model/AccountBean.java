package com.example.a15910.mscellaneous.model;

import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;
import java.io.Serializable;

/**
 * 用户信息实体
 * Created by hu on 2018/4/16.
 */

public class AccountBean extends DataSupport implements Serializable {

    private String username;//用户名
    private String phonenumber;//手机号
    @Encrypt(algorithm = MD5)//加密了
    private String password;//密码
    private long createTime;//用户创建时间
//    @Column(defaultValue = )
    private long updateTime;//用户信息更新时间
    private String imageHead;//头像路径

    public AccountBean(){

    }

    public AccountBean(String username, String phonenumber, String password, long createTime, long updateTime, String imageHead) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.password = password;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.imageHead = imageHead;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long upatedTime) {
        this.updateTime = upatedTime;
    }

    public String getImageHead() {
        return imageHead;
    }

    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String account) {
        this.phonenumber = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
