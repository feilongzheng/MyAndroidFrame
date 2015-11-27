package com.lcworld.fitness.framework.activity;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.lcworld.fitness.framework.util.Const;

/**
 * 分享按钮可控制，对里面的url做特殊处理的普通webview
 */
public class CustomWebActivity extends BaseWebViewActivity {
    public static final String showShareBtnKey = "showShareBtnKey";

	@Override
	public void afterInitView() {
        if (!getIntent().getBooleanExtra(showShareBtnKey, false)){
            //没有showShareBtnKey或者为false
            btn_title_right.setVisibility(View.GONE);
        }else {
            btn_title_right.setVisibility(View.VISIBLE);
        }

		ll_bottom.setVisibility(View.GONE);
	}

	@Override
	public String getWebviewUrl() {
		return getIntent().getStringExtra(Const.customWebUrlKey);
	}

	@Override
    public void onBackPressed() {
		super.onBackPressed();
	}

	public boolean shouldOverrideUrlLoadingInBase(WebView view, String url) {
		if (!TextUtils.isEmpty(url)) {
			webview.loadUrl(url);
		}
		return true;
	}

    @Override
    public void onStop(){
        this.webview.reload();

        super.onStop ();
    }





}
