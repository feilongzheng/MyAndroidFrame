package com.lcworld.fitness.framework.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.lcworld.fitness.R;

public class PromptyManager {

	public static void showExitDialog(final Context context) {
		View content = View.inflate(context, R.layout.exit_dialog, null);
		final Dialog dialog = new Dialog(context, R.style.dialog);

		Button bt_cancle = (Button) content.findViewById(R.id.bt_cancle);
		Button bt_ok = (Button) content.findViewById(R.id.bt_ok);

		bt_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		bt_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "bt_ok", 0).show();
				dialog.dismiss();
			}
		});

		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		int y = outMetrics.heightPixels;
		int x = outMetrics.widthPixels;
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(x - dip2px(context, 40), LayoutParams.WRAP_CONTENT);

		dialog.show();
		dialog.setContentView(content, params);// 大小

	}

	public static int dip2px(Context context, float dip) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static float px2dip(Context context, int px) {
		float density = context.getResources().getDisplayMetrics().density;
		return (px / density + 0.5f);
	}

	/*
	 * static PopupWindow pop ; public static void showExitPopupWindow(final
	 * Activity activity){ View content = View.inflate(activity,
	 * R.layout.exit_dialog, null); Button bt_cancle = (Button)
	 * content.findViewById(R.id.bt_cancle); Button bt_ok = (Button)
	 * content.findViewById(R.id.bt_ok);
	 * 
	 * bt_cancle.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { pop.dismiss(); } });
	 * bt_ok.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { Toast.makeText(activity, "bt_ok",
	 * 0).show(); pop.dismiss(); } });
	 * 
	 * if(content.getLayoutParams() == null){ ViewGroup.LayoutParams params =
	 * new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
	 * LayoutParams.WRAP_CONTENT); content.setLayoutParams(params); }
	 * 
	 * pop = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
	 * ViewGroup.LayoutParams.WRAP_CONTENT, true); View parent =
	 * ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
	 * WindowManager manager = (WindowManager)
	 * activity.getSystemService(Context.WINDOW_SERVICE); DisplayMetrics
	 * outMetrics = new DisplayMetrics();
	 * manager.getDefaultDisplay().getMetrics(outMetrics); int y =
	 * outMetrics.heightPixels; int x = outMetrics.widthPixels;
	 * 
	 * pop.showAtLocation(parent, Gravity.RIGHT|Gravity.BOTTOM, 0, y/2);
	 * 
	 * }
	 */

	public static void showToast(Context context) {
		Toast toast = new Toast(context);
		View view = View.inflate(context, R.layout.exit_dialog, null);
		toast.setView(view);
		toast.setDuration(0);
		toast.show();
	}

}
