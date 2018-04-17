package com.example.a15910.mscellaneous;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.a15910.mscellaneous.model.AccountBean;
import com.example.a15910.mscellaneous.databinding.ActivityHomeBinding;

import org.litepal.crud.DataSupport;

import java.util.List;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页
 * Created by hu on 2018/4/16.
 */

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCountView(this,R.layout.activity_home);
        initToolbar();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView(){
        //通过手机号查询用户信息
        getUserInfo(getIntent().getStringExtra("phonenumber"));
    }

    /***
     * 通过手机号查询数据库中用户信息，并更新ui
     * @param phoneNumber 手机号
     */
    private void getUserInfo(final String phoneNumber){
        if(TextUtils.isEmpty(phoneNumber)){
            return;
        }
        io.reactivex.Observable.create(new ObservableOnSubscribe<List<AccountBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AccountBean>> e) throws Exception {
                List<AccountBean> list = DataSupport.where("phonenumber like ?",phoneNumber).find(AccountBean.class);
                e.onNext(list);
            }
        }).subscribeOn(Schedulers.io())//数据库操作放在线程中
                .observeOn(AndroidSchedulers.mainThread())//结束之后放在主线程
                .subscribe(new Observer<List<AccountBean>>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<AccountBean> o) {
                        if(o != null && o.size()>0){
                            o.get(0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }
}
