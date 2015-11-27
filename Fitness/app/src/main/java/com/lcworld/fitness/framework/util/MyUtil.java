package com.lcworld.fitness.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.application.CrashHandler;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author zhengfeilong TODO
 */
public class MyUtil {

	/**
	 * 判断字符串是否为null或�?空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = false;
		if (null == str || "".equals(str.trim())) {
			result = true;
		}
		return result;
	}
	
	/**
	 * str != null && !("".equals(str))
	 * @param str
	 * @return
	 */
	public static boolean isNotNullOrEmpty(String str) {
		return !isNullOrEmpty(str);
	}

	/**
	 * 判断数组是否是null或size()==0
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNullOrEmpty(List<?> list) {
		boolean result = false;
		if (null == list || list.size() == 0) {
			result = true;
		}
		return result;
	}
	
	/**
	 * list != null && lists.size() != 0;
	 * @param list
	 * @return
	 */
	public static boolean isNotNullOrEmpty(List<?> list) {
		return !isNullOrEmpty(list);
	}

	public static int dip2px(Context context, float dip) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static int px2dip(Context context, int px) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (px / density + 0.5f);
	}

	/**
	 * 获取屏幕DisplayMetrics
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenMetrics(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}

	/**
	 * 根据rating设置不同的图片，实现ratingbar效果
	 * 
	 * @param iv
	 * @param rating
	 */
	public static void setRatingImage(ImageView iv, int rating) {
		switch (rating) {
		case 1:
			iv.setImageResource(R.drawable.rating1);
			break;
		case 2:
			iv.setImageResource(R.drawable.rating2);
			break;
		case 3:
			iv.setImageResource(R.drawable.rating3);
			break;
		case 4:
			iv.setImageResource(R.drawable.rating4);
			break;
		case 5:
			iv.setImageResource(R.drawable.rating5);
			break;
		}
	}

	private static Bitmap bitmap;

	public static void setRatingBarLayoutParams(RatingBar ratingbar, Context context) {
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_normal);
		}
		if (ratingbar.getParent() instanceof LinearLayout) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bitmap.getWidth() * 5, bitmap.getHeight());
			ratingbar.setLayoutParams(params);
		} else if (ratingbar.getParent() instanceof RelativeLayout) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bitmap.getWidth() * 5, bitmap.getHeight());
			ratingbar.setLayoutParams(params);
		} else {
			throw new IllegalArgumentException("ratingbar.getParent()不是LinearLayout也不是RelativeLayout");
		}
	}

	/**
	 * 打开系统软键盘
	 */
	public static void openSoftKeyBoard(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 关闭系统软键盘
	 */
	public static void closeSoftKeyBoard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && activity.getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static void closeSoftKeyBoard(Window window, Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && window.getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 要在application的onCreate()中调用
	 * 
	 * @param context
	 */
	public static void allowUncaughtExceptionHandler(Context context) {
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(context);
	}


	/**
	 * 是否已收藏或已关注
	 * @param isFav "1"已，“0”未
	 * @return
	 */
	public static boolean isCollectedOrAttented(String isFav){
		if(isFav.equalsIgnoreCase("1")){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            设置的需要判断的时间 //格式如2012-09-08
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static String getWeek(String pTime) {
		String Week = "周";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "日";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
		}

		return Week;
	}
	
	/**
	 * 上周天到今天的相距天数
	 * @param pTime "yyyy-MM-dd"格式
	 * @return
	 */
	public static int getDaysOfWeek7ToToday(String pTime){
		String week = getWeek(pTime);
		if(week.equals("周日")){
			return 0;
		}else if(week.equals("周一")){
			return 1;
		}else if(week.equals("周二")){
			return 2;
		}else if(week.equals("周三")){
			return 3;
		}else if(week.equals("周四")){
			return 4;
		}else if(week.equals("周五")){
			return 5;
		}else if(week.equals("周六")){
			return 6;
		}
		return 0;
	}
	
	
	/**
	 * 判断上面的getWeek()函数得到的week是不是周六或周天
	 * @param week
	 * @return
	 */
	public static boolean getWeekIsWeekend(String week){
		if(week.equals("周六") || week.equals("周日")){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 防止重复点击按钮 间隔30s
	 * 
	 * @param view
	 */
	public static void setViewEnableFalse(final View view) {
		view.setEnabled(false);
		final Timer timer = new Timer();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 122) {
					view.setEnabled(true);
					timer.cancel();
				}
				super.handleMessage(msg);
			}
		};

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(122);
			}
		}, 30000, 30000);
	}
	
	/**
	 * 设置frontStr是黑色latterStr是红色
	 * @param frontStr 黑色部分
	 * @param latterStr 红色部分
	 * @return 
	 */
	public static Spanned setStringBlack_Red(String frontStr, String latterStr){
		return Html.fromHtml(frontStr+"<font color='#ff0000'>"+latterStr+"</font>");
	}
	
	public static Spanned setStringRed_Black(String frontStr, String latterStr){
		return Html.fromHtml("<font color='#ff0000'>"+frontStr+"</font>"+latterStr);
	}
	
	/**
	 * 设置textview中划线
	 * @param textView
	 */
	public static void setTextViewLineThrough(TextView textView){
		textView.getPaint().setAntiAlias(true);//抗锯齿
		textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰 
//		textView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
	}
	
	/**
	 * 开启activity带参数
	 * @param packageContext
	 * @param cls
	 * @param extraKey
	 * @param extraValue
	 */
	public static void startActivity(Context packageContext, Class<?> cls, String extraKey, String extraValue){
		Intent intent = new Intent(packageContext, cls);
		intent.putExtra(extraKey, extraValue);
		packageContext.startActivity(intent);
	}
	
	/**
	 * 开启activity带参数
	 * @param packageContext
	 * @param cls
	 * @param extraKey
	 * @param extraValue
	 */
	public static void startActivity(Context packageContext, Class<?> cls, String extraKey, Serializable extraValue){
		Intent intent = new Intent(packageContext, cls);
		intent.putExtra(extraKey, extraValue);
		packageContext.startActivity(intent);
	}
	
	/**
	 * 开启activity不带参数
	 * @param packageContext
	 * @param cls
	 */
	public static void startActivity(Context packageContext, Class<?> cls){
		Intent intent = new Intent(packageContext, cls);
		packageContext.startActivity(intent);
	}
	
	
	
	

	
	
	
	
	
	
	
	

}
