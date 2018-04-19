package com.example.a15910.mscellaneous.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.example.a15910.mscellaneous.R;
import com.example.a15910.mscellaneous.bean.AccountBean;
import com.example.a15910.mscellaneous.databinding.ActivityHomeBinding;
import com.example.a15910.mscellaneous.view.AlertDialogController;
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
        setCountView(this, R.layout.activity_home);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 方法一
     * 点击返回按钮时弹框提示
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            AlertDialogController.getInstance().showDialog(HomeActivity.this,"",
                    "确定退出程序吗？",
                    new AlertDialogController.DialogCallBack() {
                        @Override
                        public void positiveButton() {
                            exit();//退出程序
                        }
                    });
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //用来计算返回键的点击间隔时间
//    private long exitTime = 0;
//    /***
//     * 方法二
//     * 两秒内点击两次直接退出程序
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                //弹出提示，可以有多种方式
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                exit();//退出
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
