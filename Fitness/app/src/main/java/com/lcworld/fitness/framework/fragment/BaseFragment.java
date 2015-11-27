package com.lcworld.fitness.framework.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.lcworld.fitness.framework.application.BaseApplication;

public abstract class BaseFragment extends Fragment implements OnClickListener {

	protected View view;
	public Application mApplication;
	public Context mBaseContext;
	public Bundle savedInstanceState;


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mApplication = BaseApplication.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBaseContext = this.getActivity();
		this.savedInstanceState=savedInstanceState;
		view = initView(inflater);
		if (view != null) {
			setData(view);
		}
		return view;
	}

	/**
	 * 初始化布局
	 */
	public abstract View initView(LayoutInflater inflater);


	/**
	 * 初始化数据
	 * 
	 */
	public abstract void setData(View view);

	/**
	 * 点击事件
	 * 
	 * @param view
	 */
	protected void onclickEvent(View view){

    }

    /**
     * 界面已经存在，只刷新数据
     */
    public void reloadData(){

    }

	@Override
	public void onClick(View v) {
		onclickEvent(v);
	}

	@Override
	public void onResume() {
		super.onResume();
        fragmentShowOrResumeRefreshData();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	

    /**
     * 首页四个fragment，当前显示的fragment是这个fragment时刷新数据
     */
    protected void fragmentShowOrResumeRefreshData(){

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if ( !hidden){
            fragmentShowOrResumeRefreshData();
        }

    }

}
