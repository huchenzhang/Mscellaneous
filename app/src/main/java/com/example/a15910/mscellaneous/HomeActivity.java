package com.example.a15910.mscellaneous;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.a15910.mscellaneous.bean.AccountBean;
import com.example.a15910.mscellaneous.databinding.ActivityHomeBinding;

import org.litepal.crud.DataSupport;

import java.util.List;

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
        String phonenumber = getIntent().getStringExtra("phonenumber");
        List<AccountBean> infos = DataSupport.where("phonenumber like ?",phonenumber).find(AccountBean.class);
    }
}
