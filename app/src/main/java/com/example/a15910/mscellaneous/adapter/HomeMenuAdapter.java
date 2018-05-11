package com.example.a15910.mscellaneous.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.a15910.mscellaneous.R;
import com.example.a15910.mscellaneous.bean.CommonBean;
import com.example.a15910.mscellaneous.databinding.ItemMenuBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * 侧边栏菜单列表
 * Created by hu on 2018/5/10.
 */

public class HomeMenuAdapter extends BaseRecyclerAdapter<ItemMenuBinding>{

    /** 侧边功能列表 **/
    private List<CommonBean> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    /***
     * 构造方法
     * @param context 上下文
     * @param list 储存功能列表的数据集合
     */
    public HomeMenuAdapter (Context context , List<CommonBean> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder<ItemMenuBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMenuBinding itemMenuBinding = DataBindingUtil.inflate(inflater, R.layout.item_menu,parent,false);
        return new ViewHolder<>(itemMenuBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder<ItemMenuBinding> holder, int position) {
        holder.getBinding().setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ?  0 : list.size();
    }
}
