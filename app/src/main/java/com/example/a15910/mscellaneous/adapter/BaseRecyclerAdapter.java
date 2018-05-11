package com.example.a15910.mscellaneous.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hu on 2018/5/10.
 */

public abstract class BaseRecyclerAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<ViewHolder<T>> {
    @Override
    public abstract void onBindViewHolder(ViewHolder<T> holder, int position);
}
