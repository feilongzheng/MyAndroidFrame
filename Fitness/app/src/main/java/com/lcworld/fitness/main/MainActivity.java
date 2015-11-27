package com.lcworld.fitness.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.activity.BaseActivity;
import com.lcworld.fitness.framework.application.BaseApplication;
import com.lcworld.fitness.home.fragment.HomeFragment;
import com.lcworld.fitness.my.fragment.MyFragment;
import com.lcworld.fitness.nearby.fragment.NearbyFragment;
import com.lcworld.fitness.vip.fragment.VipFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author zhengfeilong
 */

public class MainActivity extends BaseActivity {

	ContentClass contentClass;
	private HomeFragment homeFragment;
	private MyFragment myFragment;
	private VipFragment vipFragment;
	private NearbyFragment nearbyFragment;
	private FragmentManager fragmentManager;
	int vipLoginRequestCode = 1;
	int myLoginRequestCode = 2;
	
	

    class ContentClass {
		@ViewInject(R.id.fl_content)
		FrameLayout fl_content;

		@ViewInject(R.id.rb_tab0)
		RadioButton rb_tab0;

		@ViewInject(R.id.rb_tab1)
		RadioButton rb_tab1;

		@ViewInject(R.id.rb_tab2)
		RadioButton rb_tab2;

		@ViewInject(R.id.rb_tab3)
		RadioButton rb_tab3;

		public void inject(BaseActivity page){
			ViewUtils.inject(contentClass, page);
			rb_tab0.setOnClickListener(page);
			rb_tab1.setOnClickListener(page);
			rb_tab2.setOnClickListener(page);
			rb_tab3.setOnClickListener(page);
		}
	}


	@Override
	public void initView() {
        setContentView(R.layout.activity_main);
		contentClass = new ContentClass();
		contentClass.inject(this);
		
		fragmentManager = getSupportFragmentManager();
		turnToHomeFragment();
	}

    @Override
    public void setData() {

    }


	public void onClick(View v) {
		switch (v.getId()) { // 首页
		case R.id.rb_tab0:
			turnToHomeFragment();
			break;

		case R.id.rb_tab1:
			turnToNearbyFragment();
			break;

		case R.id.rb_tab2:
            turnToVipFragment();
			break;

		case R.id.rb_tab3:
            turnToMyFragment();
			break;
		}
	}

	private void turnToNearbyFragment() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);

		if (nearbyFragment == null) {
			// 如果为空，则创建一个并添加到界面上
			nearbyFragment = new NearbyFragment();
			transaction.add(R.id.fl_content, nearbyFragment);
		} else {
			// 如果不为空，则直接将它显示出来
			transaction.show(nearbyFragment);
		}
		
		transaction.commit();
	}

	private void turnToHomeFragment() {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);

		if (homeFragment == null) {
			homeFragment = new HomeFragment();
			transaction.add(R.id.fl_content, homeFragment);
		} else {
			transaction.show(homeFragment);
		}
		
		transaction.commit();
	}

	
	protected void turnToMyFragment() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		
		if (myFragment == null) {
			// 如果为空，则创建一个并添加到界面上
			myFragment = new MyFragment();
			transaction.add(R.id.fl_content, myFragment);
		} else {
			// 如果不为空，则直接将它显示出来
			transaction.show(myFragment);
		}
		
		transaction.commit();
	}

	protected void turnToVipFragment() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		
		if (vipFragment == null) {
			// 如果为空，则创建一个并添加到界面上
			vipFragment = new VipFragment();
			transaction.add(R.id.fl_content, vipFragment);
		} else {
			// 如果不为空，则直接将它显示出来
			transaction.show(vipFragment);
		}
		
		transaction.commit();
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (nearbyFragment != null) {
			transaction.hide(nearbyFragment);
		}
		if (vipFragment != null) {
			transaction.hide(vipFragment);
		}
		if (myFragment != null) {
			transaction.hide(myFragment);
		}

	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog(); 
		}
		return false;
	}

	private void showExitDialog() {
		AlertDialog dialog = new AlertDialog.Builder(this)
		.setTitle("您要退出程序吗？")
		.setPositiveButton("退出", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BaseApplication.quit();//退出应用
			}
		})
		.setNegativeButton("取消",null)
		.create();
		dialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	

}
