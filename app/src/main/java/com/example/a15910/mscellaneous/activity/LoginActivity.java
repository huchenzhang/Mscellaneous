package com.example.a15910.mscellaneous.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;
import com.example.a15910.mscellaneous.R;
import com.example.a15910.mscellaneous.databinding.ActivityLoginBinding;
import com.example.a15910.mscellaneous.bean.AccountBean;
import com.example.a15910.mscellaneous.presenter.DaoClient;
import com.example.a15910.mscellaneous.view.ProgressDialogController;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCountView(this,R.layout.activity_login);
        binding.setLogin(this);
        initListener();
    }

    /**
     * 监听手机号和密码的输入
     */
    private void initListener(){
        //手机号的监听
        binding.tePhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(binding.tePhonenumber.getText().toString())){
                    binding.laPhonenumber.setError("密码不能为空");
                }else{
                    binding.laPhonenumber.setErrorEnabled(false);
                }
            }
        });

        //密码的监听
        binding.tePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(binding.tePassword.getText().toString())){
                    binding.laPassword.setError("密码不能为空");
                }else{
                    binding.laPassword.setErrorEnabled(false);
                }
            }
        });
    }

    /**
     * 登陆
     */
    public void login(){
        //先判空
        if(TextUtils.isEmpty(binding.tePhonenumber.getText().toString())){
            binding.laPhonenumber.setError("手机号不能为空");
        }

        //先判空
        if(TextUtils.isEmpty(binding.tePassword.getText().toString())){
            binding.laPassword.setError("密码不能为空");
            return;
        }

        //弹出菊花
        ProgressDialogController.getInstance().showProgressDialog(this,"登陆中....",null);

        //去登陆
        Observable.create(
                DaoClient.getInstance().login(binding.tePhonenumber.getText().toString(),binding.tePassword.getText().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AccountBean>>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<AccountBean> list) {
                        disposable.dispose();
                        ProgressDialogController.getInstance().cancelProgressDialog();
                        if(list == null || list.isEmpty()){
                            binding.laPassword.setError("用户名或密码不正确");
                            return;
                        }
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        intent.putExtra("phonenumber",list.get(0).getPhonenumber());
                        startActivity(intent);
                        finish();
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
     * 去注册
     */
    public void register(){
        Toast.makeText(this, "去注册", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,RegisteredActivity.class);
        startActivity(intent);
    }

    /**
     * 忘记密码
     */
    public void forget(){
        Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
    }

}
