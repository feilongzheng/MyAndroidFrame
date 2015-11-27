package com.lcworld.fitness.regist.dialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.contant.Constants;
import com.lcworld.fitness.framework.util.MyUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegistAgreementDialog extends Dialog implements android.view.View.OnClickListener {
	ContentClass contentClass;
	Context context;

	public RegistAgreementDialog(Context context) {
		super(context, R.style.regist_agreement);
		this.context = context;
		initContentView();
	}

	private void initContentView() {
		contentClass = new ContentClass();
		contentClass.inject(this);
	}

	class ContentClass {
		View dialogView;

		@ViewInject(R.id.bar_back)
		ImageView bar_back;
		@ViewInject(R.id.tv_content)
		TextView tv_content;

		public ContentClass() {

		}

		public void inject(android.view.View.OnClickListener l) {
			dialogView = getLayoutInflater().inflate(R.layout.regist_agreement, null);
			ViewUtils.inject(contentClass, dialogView);

			bar_back.setOnClickListener(l);
			AssetManager assetManager = getContext().getResources().getAssets();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				InputStream inputStream = assetManager.open(Constants.FILE_NAME_REGIST_AGREEMENT);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			tv_content.setText(baos.toString());
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bar_back:
			this.dismiss();
			break;
		}
	}

	@Override
	public void show() {
		super.show();

		setContent();
		setLocation(this);
	}

	public void setLocation(RegistAgreementDialog dialog) {
		Window window = dialog.getWindow();
		android.view.WindowManager.LayoutParams attributes = window.getAttributes();
		attributes.x = 0;
		attributes.y = 0;
		attributes.gravity = Gravity.BOTTOM;
		window.setAttributes(attributes);
	}

	private void setContent() {
		DisplayMetrics screenMetrics = MyUtil.getScreenMetrics(context);
		LayoutParams params = new LayoutParams(screenMetrics.widthPixels, screenMetrics.heightPixels);
		setContentView(contentClass.dialogView, params);
	}

}
