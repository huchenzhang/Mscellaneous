package com.example.a15910.mscellaneous.model;

import com.example.a15910.mscellaneous.bean.AccountBean;
import com.example.a15910.mscellaneous.util.MD5Util;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 数据库操作
 * Created by hu on 2018/4/19.
 */

public class LocationData {

    /**
     * 登陆时验证账号和密码
     * @param phoneNumber 手机号
     * @param password 密码的MD5值
     * @return ObservableOnSubscribe<List<AccountBean>>
     */
    public ObservableOnSubscribe<List<AccountBean>> login(final String phoneNumber, final String password) {

        return new ObservableOnSubscribe<List<AccountBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AccountBean>> e) throws Exception {
                List<AccountBean> list = DataSupport.where(
                        "phonenumber like ? and password like ?", phoneNumber, MD5Util.encrypt(password))
                        .find(AccountBean.class);
                e.onNext(list);
            }
        };
    }



}
