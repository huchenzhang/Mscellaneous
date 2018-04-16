package com.example.a15910.mscellaneous;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.a15910.mscellaneous.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCountView(this,R.layout.activity_login);
        binding.setLogin(this);
    }


    /**
     * 登陆
     */
    public void login(){
        Toast.makeText(this, "点击登陆", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
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
