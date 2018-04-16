package com.example.a15910.mscellaneous.bean;

import com.example.a15910.mscellaneous.util.MD5Util;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 账号实体
 * Created by hu on 2018/4/16.
 */

public class AccountBean extends DataSupport implements Serializable {

    private String username;//用户名
    private String phonenumber;//手机号
    private String password;//密码

    public AccountBean(){

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
        this.password = MD5Util.encrypt(password);
    }
}
