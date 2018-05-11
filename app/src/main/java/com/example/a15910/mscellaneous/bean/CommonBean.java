package com.example.a15910.mscellaneous.bean;

import java.io.Serializable;

/**
 * 两个参数的实体，公用
 * 1.侧边栏菜单，一个是图片，一个是功能名字
 * Created by hu on 2018/5/10.
 */

public class CommonBean implements Serializable {

    private int id;//第一个参数，图片
    private String str;//第二个参数，功能名字

    public CommonBean() {
    }

    public CommonBean(int id, String str) {
        this.id = id;
        this.str = str;
    }

    public int getMD5() {
        return id;
    }

    public void setMD5(int id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
