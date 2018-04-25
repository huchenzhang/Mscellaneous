package com.example.a15910.mscellaneous.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.a15910.mscellaneous.App;
import com.example.a15910.mscellaneous.R;

/**
 * Activity的基类
 * Created by hu on 2018/4/13.
 */

public abstract class BaseActivity <T extends ViewDataBinding> extends AppCompatActivity {
    protected T binding;
    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App)getApplication();
        app.addActivity(this);
    }

    protected void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");

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
        closeKeyboard();
        app.removeActivity(this);
    }

    /**
     * 退出程序
     */
    public void exit(){
        app.clearAllActivity();
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
