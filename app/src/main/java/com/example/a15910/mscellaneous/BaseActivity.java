package com.example.a15910.mscellaneous;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Activity的基类
 * Created by hu on 2018/4/13.
 */

public abstract class BaseActivity <T extends ViewDataBinding> extends AppCompatActivity {
    private Toolbar toolbar;
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("SubTitle");
        toolbar.setLogo(R.mipmap.ic_launcher);

        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);

    }


    /**
     * 添加布局，初始化binding
     * @param activity activity
     * @param layoutResID 布局地址
     */
    public void setCountView(Activity activity, @LayoutRes int layoutResID){
        binding = DataBindingUtil.setContentView(activity,layoutResID);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
