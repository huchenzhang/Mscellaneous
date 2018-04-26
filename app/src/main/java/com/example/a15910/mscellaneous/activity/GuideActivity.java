package com.example.a15910.mscellaneous.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.a15910.mscellaneous.R;
import com.example.a15910.mscellaneous.adapter.GuidePageAdapter;
import com.example.a15910.mscellaneous.databinding.ActivityGuideBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * 程序首次启动的欢迎页
 * Created by hu on 2018/4/26.
 */

public class GuideActivity extends BaseActivity<ActivityGuideBinding> implements ViewPager.OnPageChangeListener{

    private int []imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    private ImageView []ivPointArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setCountView(this,R.layout.activity_guide);
        binding.setGuide(this);
        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
    }

    /**
     * 点击开始体验按钮
     */
    public void clickStart(){
        startActivity(new Intent(GuideActivity.this,LoginActivity.class));
        finish();
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0;i<size;i++){
            ImageView iv_point = new ImageView(this);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(20,20));
            iv_point.setPadding(30,0,30,0);//left,top,right,bottom

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(20, 0, 20, 0);
            iv_point.setLayoutParams(params);
            //设置外边距

            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0){
                iv_point.setBackgroundResource(R.drawable.ic_full_holo);
            }else{
                iv_point.setBackgroundResource(R.drawable.ic_empty_holo);
            }
            //将数组中的ImageView加入到ViewGroup
            binding.llPoint.addView(ivPointArray[i]);
        }
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        //实例化图片资源
        imageIdArray = new int[]{R.mipmap.ic_splash_1,R.mipmap.ic_splash_1,R.mipmap.ic_splash_1};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0;i<len;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        binding.vpGuide.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        binding.vpGuide.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0;i<length;i++){
            ivPointArray[position].setBackgroundResource(R.drawable.ic_full_holo);
            if (position != i){
                ivPointArray[i].setBackgroundResource(R.drawable.ic_empty_holo);
            }
        }

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1){
            binding.btStart.setVisibility(View.VISIBLE);
        }else {
            binding.btStart.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
