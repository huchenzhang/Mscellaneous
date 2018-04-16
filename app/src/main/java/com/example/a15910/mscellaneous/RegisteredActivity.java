package com.example.a15910.mscellaneous;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.a15910.mscellaneous.bean.AccountBean;
import com.example.a15910.mscellaneous.databinding.ActivityRegisteredBinding;
import com.example.a15910.mscellaneous.util.MD5Util;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册页面
 * Created by hu on 2018/4/16.
 */

public class RegisteredActivity extends BaseActivity<ActivityRegisteredBinding>{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCountView(this,R.layout.activity_registered);
        binding.setRegistered(this);
        initListener();
    }


    /**
     * 点击注册按钮
     */
    private void registered(){
        io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                AccountBean accountBean = new AccountBean();
                accountBean.setUsername(binding.teUsername.getText().toString());
                accountBean.setPhonenumber(binding.tePhonenumber.getText().toString());
                accountBean.setPassword(MD5Util.encrypt(binding.tePhonenumber.getText().toString()));
                accountBean.save();
            }
        }).subscribeOn(Schedulers.newThread())//数据库操作放在线程中
                .observeOn(AndroidSchedulers.mainThread())//结束之后放在主线程
                .subscribe(new Observer<String>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String o) {
                        Toast.makeText(RegisteredActivity.this,"保存完成",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisteredActivity.this,HomeActivity.class);
                        intent.putExtra("phonenumber",o);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                        Toast.makeText(RegisteredActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });

    }

    /**
     * 检查一下
     */
    public void check(){

        if(TextUtils.isEmpty(binding.teUsername.getText().toString())){
            binding.laUsername.setError("用户名不能为空");
        }

        if(TextUtils.isEmpty(binding.tePhonenumber.getText().toString())){
            binding.laPhonenumber.setError("手机号不能为空");
        }

        if(TextUtils.isEmpty(binding.tePassword1.getText().toString())){
            binding.laPassword1.setError("密码不能为空");
        }

        if(TextUtils.isEmpty(binding.tePassword2.getText().toString())){
            binding.laPassword2.setError("密码不能为空");
            return;
        }

        if(binding.tePassword1.getText().toString().equals(binding.tePassword1.getText().toString())){
            binding.laPassword2.setError("两次密码不一致");
            return;
        }


        //检查没问题后去保存
        registered();
    }

    /**
     * 注册用户名和手机号输入框的失去焦点事件
     * 失去焦点时检查当前输入框内用户名和手机号是否重复
     */
    private void initListener(){
        //手机号是否存在
        binding.tePhonenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //为空return
                if(binding.tePhonenumber.getText() == null || TextUtils.isEmpty(binding.tePhonenumber.getText().toString())){
                    return;
                }
                //失去焦点并且内容不为空时，检查手机号是或否存在
                if(!hasFocus){
                    checkPhonember();
                }
            }
        });

        //检查用户名是或否重复
        binding.teUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //为空return
                if(binding.teUsername.getText() == null || TextUtils.isEmpty(binding.teUsername.getText().toString())){
                    return;
                }
                //失去焦点并且内容不为空时，检查用户名是或否存在
                if(!hasFocus){
                    checkUsername();
                }
            }
        });
    }

    /**
     * 去数据库查询该手机号是否存在
     */
    private void checkPhonember(){
        io.reactivex.Observable.create(new ObservableOnSubscribe<List<AccountBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AccountBean>> e) throws Exception {
            e = (ObservableEmitter<List<AccountBean>>) DataSupport.where("phonenumber like ?",binding.tePhonenumber.getText().toString()).find(AccountBean.class);
            }
        }).subscribeOn(Schedulers.newThread())//数据库操作放在线程中
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
                            binding.laPhonenumber.setError("该手机号已经注册");
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

    /**
     * 去数据库查询该用户名是否存在
     */
    private void checkUsername(){
        io.reactivex.Observable.create(new ObservableOnSubscribe<List<AccountBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AccountBean>> e) throws Exception {
                e = (ObservableEmitter<List<AccountBean>>) DataSupport.where("username like ?",binding.teUsername.getText().toString()).find(AccountBean.class);
            }
        }).subscribeOn(Schedulers.newThread())//数据库操作放在线程中
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
                            binding.laUsername.setError("该用户名已存在");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                        Toast.makeText(RegisteredActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

}
