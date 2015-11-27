package com.lcworld.fitness.home.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.cacheimage.NetWorkImageView;
import com.lcworld.fitness.framework.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
	List<RadioButton> rbList = null;
	List<AdBean> data = null;
	LayoutInflater myInflater = null;
	LinearLayout ll_dot = null;

	public MyPagerAdapter(LayoutInflater myInflater2, LinearLayout ll_dot2) {
		rbList = new ArrayList<RadioButton>();
		myInflater = myInflater2;
		ll_dot = ll_dot2;

	}

	public void setData(List<AdBean> data) {
		this.data = data;

		if(!MyUtil.isNullOrEmpty(data)){
			initDots(data.size());
		}
	}

	public List<AdBean> getData() {
		return data;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		object = null;

	}

	@Override
	public int getItemPosition(Object object) {

		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		AdBean cur = data.get(position % data.size());

		View itemView = myInflater.inflate(R.layout.home_viewpager_item, null);
		NetWorkImageView nwiv = (NetWorkImageView) itemView.findViewById(R.id.nwiv);
		nwiv.loadBitmap(cur.adUrl, R.drawable.empty_photo, true);

		container.addView(itemView);
		return itemView;
	}

	public void initDots(int number) {
		for (int i = 0; i < number; i++) {
			LinearLayout child = (LinearLayout) myInflater.inflate(R.layout.home_viewpager_dot, null);
			RadioButton rb = (RadioButton) child.findViewById(R.id.rb);
			rbList.add(rb);
			ll_dot.addView(child);
		}
		if (rbList.get(0) != null) {
			rbList.get(0).setChecked(true);
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			clearChecked();
			int curPosition = (position) % data.size();
			rbList.get(curPosition).setChecked(true);
		}

		public void clearChecked() {
			for (RadioButton rb : rbList) {
				if (rb != null) {
					rb.setChecked(false);
				}
			}

		}
	}

    class AdBean{
        public String adUrl;

    }

}
