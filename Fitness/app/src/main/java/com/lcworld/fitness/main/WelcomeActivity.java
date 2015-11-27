package com.lcworld.fitness.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.activity.BaseActivity;
import com.lcworld.fitness.framework.contant.Constants;
import com.lcworld.fitness.main.adapter.WelcomePagerAdapter;
import com.lcworld.fitness.main.adapter.WelcomePagerAdapter.WelcomePagerAdapterListener;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity {
	private ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome);

    }

    @Override
	public void initView() {

		SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_WELCOM, 0);
		if(sharedPreferences.getBoolean("isFirst", true)){
			viewpager = (ViewPager) this.findViewById(R.id.viewpager);
			LinearLayout ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
			WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter(new WelcomePagerAdapterListener() {
				@Override
				public void clickInto() {
					turnToMainActivity();
				}
			},getLayoutInflater(), ll_dot);
			
			List<Integer> imgUrls = new ArrayList<Integer>();
			imgUrls.add(R.drawable.welcome_img1);
			imgUrls.add(R.drawable.welcome_img2);
			imgUrls.add(R.drawable.welcome_img3);	
			imgUrls.add(R.drawable.welcome_img4);
			
			welcomePagerAdapter.setData(imgUrls);
			viewpager.setAdapter(welcomePagerAdapter);
			viewpager.setOnPageChangeListener(welcomePagerAdapter.new MyOnPageChangeListener());
			
			Editor editor = sharedPreferences.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}else{
			turnToMainActivity();
		}
		
		
	}

    @Override
    public void setData() {

    }

    public void turnToMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	




}
