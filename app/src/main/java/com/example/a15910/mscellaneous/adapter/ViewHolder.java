package com.example.a15910.mscellaneous.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hu on 2018/5/10.
 */

public class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    T binding;

    public T getBinding() {
        return binding;
    }

    public ViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}