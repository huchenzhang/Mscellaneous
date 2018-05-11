package com.example.a15910.mscellaneous.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 图片处理相关方法
 * Created by hu on 2018/5/10.
 */

public class ImageUtils {
    @BindingAdapter({"image"})
    public static void imageLoader(ImageView imageView,int id){
        Glide.with(imageView).load(id);
    }


}


