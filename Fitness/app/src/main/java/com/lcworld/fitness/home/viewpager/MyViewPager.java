package com.lcworld.fitness.home.viewpager;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lidroid.xutils.util.LogUtils;

public class MyViewPager extends ViewPager{
	private onSimpleClickListener listener;

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewPager(Context context) {
		super(context);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}


	public interface onSimpleClickListener {
		void simpleClick(int position);
	}

	public void setOnSimpleClickListener(onSimpleClickListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			LogUtils.i("ACTION_DOWN");
			stopCycle();
			break;

		case MotionEvent.ACTION_MOVE:
			LogUtils.i("ACTION_MOVE");
			break;

		case MotionEvent.ACTION_UP:
			LogUtils.i("ACTION_UP");
			startCycle();
			break;

		}
		return super.onTouchEvent(event);
	}

	// 开启定时器让viewpager自动循环
	// 1.handler和message组合 2.timer和task 3.线程池
	private final long VIEWPAGER_FLIP_PERIOD = 5000;// viewpager 5秒滑动一次
	private final int VIEWPAGER_FLIP = 1;
	private Timer viewpagerTimer;

	public void startCycle() {
		viewpagerTimer = new Timer();
		viewpagerTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(VIEWPAGER_FLIP);
			}
		}, VIEWPAGER_FLIP_PERIOD, VIEWPAGER_FLIP_PERIOD);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case VIEWPAGER_FLIP:
				int curPosition = MyViewPager.this.getCurrentItem();
				if (curPosition < Integer.MAX_VALUE - 10) {
					MyViewPager.this.setCurrentItem(curPosition + 1);
				}
				break;
			}
		};

	};

	public void stopCycle() {
		if (viewpagerTimer != null) {
			viewpagerTimer.cancel();
		}
	}

}