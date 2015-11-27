package com.lcworld.fitness.login.activity;

import android.view.View;
import android.widget.EditText;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.activity.BaseActivity;
import com.lcworld.fitness.framework.util.MyUtil;
import com.lcworld.fitness.framework.util.Utility;
import com.lcworld.fitness.framework.util.VerifyCheck;

/**
 * <!-- 重置密码 -->
 * 
 * @author zhengfeilong TODO
 */
public class ResetPasswordActivity extends BaseActivity {

	private EditText et_phone;


	@Override
	public void initView() {
        setContentViewInBase(R.layout.find_password, "重置密码");
		et_phone = (EditText) this.findViewById(R.id.et_phone);
		this.findViewById(R.id.bt_cenfirm).setOnClickListener(this);

	}

    @Override
    public void setData() {

    }


    @Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.bt_cenfirm:
			// 隐藏软键盘
			MyUtil.closeSoftKeyBoard(this);
			turnToResetPsd();
			break;
		}
	}

	private void turnToResetPsd() {
		String phone = et_phone.getText().toString().trim();
		if (!VerifyCheck.isMobilePhoneVerify(phone)) {
			Utility.showShortToast(mBaseContext, "请输入正确的手机号码！");
			return;
		}



	}

}
