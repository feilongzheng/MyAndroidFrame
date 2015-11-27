package com.lcworld.fitness.regist.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.activity.BaseActivity;
import com.lcworld.fitness.framework.util.MyUtil;
import com.lcworld.fitness.framework.util.Utility;
import com.lcworld.fitness.framework.util.VerifyCheck;
import com.lcworld.fitness.regist.dialog.RegistAgreementDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <!-- 注册 -->
 * 
 * @author zhengfeilong TODO
 */
public class RegistActivity extends BaseActivity {
	ContentClass contentClass;




	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_regist: // 注册
			doRegist();
			break;
		case R.id.bt_getCheckWord:// 获取验证码
			getCheckCode();
			break;
		case R.id.tv_agreement:// 获取验证码
			turnToLookAgreement();
			break;

		}

	}

    @Override
    public void initView() {
        setContentViewInBase(R.layout.regist, "注册");
        ContentClass contentClass = new ContentClass();
        contentClass.inject(this);
    }

    @Override
    public void setData() {

    }

    private void turnToLookAgreement() {
		RegistAgreementDialog dialog = new RegistAgreementDialog(this);
		dialog.show();
	}

	class ContentClass {
		@ViewInject(R.id.et_phone)
		EditText et_phone;
		@ViewInject(R.id.et_check_code)
		EditText et_check_code;
		@ViewInject(R.id.bt_getCheckWord)
		Button bt_getCheckWord;
		@ViewInject(R.id.et_password)
		EditText et_password;
		@ViewInject(R.id.et_cenfirmPassword)
		EditText et_cenfirmPassword;
		@ViewInject(R.id.bt_regist)
		Button bt_regist;
		@ViewInject(R.id.tv_agreement)
		TextView tv_agreement;
		@ViewInject(R.id.cb)
		CheckBox cb;

		public ContentClass() {
		}

		public void inject(OnClickListener l) {
			ViewUtils.inject(contentClass, RegistActivity.this);

			bt_regist.setOnClickListener(l);
			bt_getCheckWord.setOnClickListener(l);
			tv_agreement.setOnClickListener(l);
			tv_agreement.setText(Html.fromHtml("我已阅读并同意<font color='#0000ff'><u>《用户使用协议》</u></font>"));
		}
	}

	private void getCheckCode() {
		String phone = contentClass.et_phone.getText().toString().trim();

		if (!VerifyCheck.isMobilePhoneVerify(phone)) {
			Utility.showShortToast(mBaseContext, "请输入正确的手机号！");
			contentClass.et_phone.setText("");
			return;
		}

		// 隐藏软键盘
		MyUtil.closeSoftKeyBoard(this);
	}

	private final int HANDLER_COUNTDOWN = 1;
	private int timeCount;
	private final int MAXTIME = 30;
	private Timer timer;

	public void startCountdown() {
		timeCount = MAXTIME;
		contentClass.bt_getCheckWord.setEnabled(false);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				msg.what = HANDLER_COUNTDOWN;
				msg.obj = timeCount--;
				myHandler.sendMessage(msg);
			}
		}, 1000, 1000);
	}

	Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == HANDLER_COUNTDOWN) {
				int curCount = (Integer) msg.obj;
				contentClass.bt_getCheckWord.setText(String.valueOf(curCount));
				if (curCount == 0) {// 结束倒计时
					timer.cancel();
					contentClass.bt_getCheckWord.setText("重新发送");
					contentClass.bt_getCheckWord.setEnabled(true);
				}
			}
		};
	};

	private void doRegist() {
		String mobile = contentClass.et_phone.getText().toString().trim();
		String checkCode = contentClass.et_check_code.getText().toString().trim();
		String password = contentClass.et_password.getText().toString().trim();
		// String cenfirmPassword =
		// contentClass.et_cenfirmPassword.getText().toString().trim();


	}

}
