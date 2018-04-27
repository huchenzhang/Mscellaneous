package com.example.a15910.mscellaneous.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.example.a15910.mscellaneous.R;
import com.example.a15910.mscellaneous.databinding.ActivitySplashBinding;
import com.example.a15910.mscellaneous.util.SPUtil;

import java.io.File;


/**
 * 启动页
 * Created by hu on 2018/4/26.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding>{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setCountView(this, R.layout.activity_splash);
//        translucentStatusBar();
        iniImage();
    }
    //先看看效果
//    private void translucentStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//    }

    /**
     * 加载启动页图片
     */
    private void iniImage() {
        //先检查本地是否有启动页图片
        File dir = getFilesDir();
        File imageFile = new File(dir, "start.jpg");
        if(imageFile.exists()) {
            binding.ivSplash.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        }else {
            //如果本地没有启动图，就加载资源文件中的图片
            binding.ivSplash.setImageResource(R.mipmap.ic_splash_1);
        }
        //实现一个放大的动画效果
        ScaleAnimation scaleAnim = new ScaleAnimation(
                1.0f,
                1.2f,
                1.0f,
                1.2f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );

        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //在这里做一些初始化的操作
                //跳转到指定的Activity
                startActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.ivSplash.startAnimation(scaleAnim);
    }

    /**
     * 动画结束后，判断
     * 1.首次启动进入欢迎页->登陆页
     * 2.第二次分两种：a,存有账号和密码，直接登陆到首页，b,不存在账号和密码，进入登陆页
     */
    private void startActivity(){
        boolean fast = SPUtil.getInstance().getBoolean("first",true);
        Intent intent = new Intent();
        if(fast){//第一次
            SPUtil.getInstance().put("first",false);
            Toast.makeText(SplashActivity.this, "第一次", Toast.LENGTH_LONG).show();
            intent.setClass(this,GuideActivity.class);
        }else{//不是第一次
            //获取sp中存储的手机号
            String phoneNumber = SPUtil.getInstance().getString("phonenumber");
            //判断是否自动登陆
            if(TextUtils.isEmpty(phoneNumber)){
                Toast.makeText(SplashActivity.this, "不是第一次，且不自动登陆", Toast.LENGTH_LONG).show();
                intent.setClass(this,LoginActivity.class);
            }else {
                Toast.makeText(SplashActivity.this, "不是第一次，自动登陆" + phoneNumber, Toast.LENGTH_LONG).show();
                intent.putExtra("phonenumber",phoneNumber);
                intent.setClass(this,HomeActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }

}
