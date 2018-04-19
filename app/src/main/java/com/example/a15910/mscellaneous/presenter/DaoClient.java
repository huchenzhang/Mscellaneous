package com.example.a15910.mscellaneous.presenter;

import com.example.a15910.mscellaneous.bean.AccountBean;
import com.example.a15910.mscellaneous.model.LocationData;

import java.util.List;

import io.reactivex.ObservableOnSubscribe;

/**
 * 数据库操作
 * Created by hu on 2018/4/19.
 */

public class DaoClient {

    private static DaoClient daoClient;

    private LocationData locationData = new LocationData();

    public static DaoClient getInstance() {
        return daoClient != null ? daoClient : (daoClient = new DaoClient());
    }

    private DaoClient(){

    }

    /**
     * 登陆时验证账号和密码
     * @param phoneNumber 手机号
     * @param password 密码的MD5值
     * @return ObservableOnSubscribe<List<AccountBean>>
     */
    public ObservableOnSubscribe<List<AccountBean>> login(String phoneNumber,String password){
       return locationData.login(phoneNumber,password);
    }
}
