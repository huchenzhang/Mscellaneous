package com.example.a15910.mscellaneous.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a15910.mscellaneous.R;
import com.example.a15910.mscellaneous.bean.AccountBean;
import com.example.a15910.mscellaneous.databinding.ActivityHomeBinding;
import com.example.a15910.mscellaneous.util.SPUtil;
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

public class HomeActivity extends BaseActivity<ActivityHomeBinding>
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCountView(this, R.layout.activity_home);
        initToolbar("首页");
        initView();
        initData();
    }

    /***
     * 初始化一些监听
     */
    private void initView() {
        //侧边菜单点击事件监听
    }

    /**
     * 初始化view
     */
    private void initData(){
        //通过手机号查询用户信息
        String phoneNumber = getIntent().getStringExtra("phonenumber");
        getUserInfo(phoneNumber);
        //把手机号存到sp中，用于判断是否自动登陆
        SPUtil.getInstance().put("phonenumber",phoneNumber);
    }

    /***新ui
     * 通过手机号查询数据库中用户信息，并更
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
                        disposable.dispose();
                        if(o != null && o.size()>0){
                            setData(o.get(0));
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
     * 设置数据
     */
    private void setData(AccountBean bean){
        findViewById(R.id.tv_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 方法一
     * 点击返回按钮时，先判断用户是不是想关闭侧边菜单
     * 点击返回按钮时弹框提示
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果侧边菜单是打开状态，就先关闭菜单
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
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

    /***
     * menu的点击事件
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(this,"点击了camera",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this,"点击了gallery",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this,"点击了slideshow",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this,"点击了camera",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"点击了share",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this,"点击了send",Toast.LENGTH_SHORT).show();
        }
        //点击了菜单中的某项后关闭侧边菜单
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
