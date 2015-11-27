package com.lcworld.fitness.my.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.fragment.BaseFragment;

public class MyFragment extends BaseFragment {

    @Override
    public View initView(LayoutInflater inflater) {
        View contentView = inflater.inflate(R.layout.vip_fragment, null);
        return contentView;
    }

    @Override
    public void setData(View view) {

    }


}
