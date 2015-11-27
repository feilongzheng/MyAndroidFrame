package com.lcworld.fitness.login.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.activity.BaseActivity;
import com.lcworld.fitness.framework.util.Utility;
import com.lcworld.fitness.framework.util.VerifyCheck;
import com.lcworld.fitness.regist.activity.RegistActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 *
 * @author zhengfeilong TODO
 */
public class LoginActivity extends BaseActivity{
	ContentClass contentClass;


	@Override
	public void initView() {
        setContentViewInBase(R.layout.login, "登录");
		contentClass = new ContentClass();
		ViewUtils.inject(contentClass, this);

		contentClass.tv_forget_password.setText(Html.fromHtml("<u>忘记密码？</u>"));
		contentClass.tv_regist.setText(Html.fromHtml("<u>注册</u>"));
		contentClass.tv_regist.setOnClickListener(this);
		contentClass.bt_login.setOnClickListener(this);
		contentClass.tv_forget_password.setOnClickListener(this);
		contentClass.bt_qq.setOnClickListener(this);
		contentClass.bt_sina.setOnClickListener(this);

	}


    @Override
    public void setData() {

    }

    class ContentClass {
		@ViewInject(R.id.et_username)
		EditText et_username;
		@ViewInject(R.id.et_password)
		EditText et_password;
		@ViewInject(R.id.tv_forget_password)
		TextView tv_forget_password;
		@ViewInject(R.id.tv_regist)
		TextView tv_regist;
		@ViewInject(R.id.cb_remember)
		CheckBox cb_remember;
		@ViewInject(R.id.bt_login)
		Button bt_login;
		@ViewInject(R.id.bt_qq)
		Button bt_qq;
		@ViewInject(R.id.bt_sina)
		Button bt_sina;
	}



	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_regist:
			// 注册
			turnToRegist();
			break;

		case R.id.bt_login:
			// 登录
			turnToLogin();
			break;

		case R.id.tv_forget_password:
			// 找回密码
			turnToFindPassword();
			break;

		case R.id.bt_qq:
			// 找回密码
			turnToLoginByQQ();
			break;

		case R.id.bt_sina:
			// 找回密码
			turnToLoginBySina();
			break;

		}

	}

	private void turnToLoginBySina() {

	}

	private void turnToLoginByQQ() {
	}

	private void turnToFindPassword() {
		Intent intent = new Intent(this, ResetPasswordActivity.class);
		this.startActivity(intent);

	}

	private void turnToLogin() {
		String mobile = contentClass.et_username.getText().toString().trim();
		String password = contentClass.et_password.getText().toString().trim();

		if (!VerifyCheck.isMobilePhoneVerify(mobile)) {
			Utility.showShortToast(mBaseContext, R.string.phone_format_error);
			return;
		}
		if (!VerifyCheck.isPasswordVerify(password)) {
            Utility.showShortToast(mBaseContext, R.string.pwd_format_error);
			return;
		}


	}


	private void turnToRegist() {
		Intent intent = new Intent(this, RegistActivity.class);
		startActivity(intent);
	}


}
