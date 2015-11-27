package com.lcworld.fitness.framework.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.util.Const;
import com.lcworld.fitness.framework.util.Utility;


public abstract class BaseWebViewActivity extends BaseActivity {

    protected WebView webview;
    //title 信息
    public View titleView;
    protected TextView tv_title;
    protected LinearLayout ll_bottom;
    private ClipboardManager clipboard = null;
    protected Button btn_title_right;
    protected boolean isWebPageFinished = false;
    ProgressBar pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_webview);

        initMyView();
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        afterInitView();
    }


	@Override
	public void initView() {

	}

    @Override
    public void setData() {

    }

    public String getActivityTitle() {
        return "";
    }

    public abstract void afterInitView();

    public abstract String getWebviewUrl();

    private String getUrlNotNull(){
        return Utility.isNull(getWebviewUrl()) ? "" : getWebviewUrl();
    }

    private void initMyView() {

        pBar = (ProgressBar) findViewById(R.id.pb_web);
//        titleView = findViewById(R.id.photoviewer_title);
//        tv_title = (TextView) titleView.findViewById(R.id.tv_title);
//        titleView.findViewById(R.id.btn_title_left).setBackgroundResource(R.drawable.ic_back);
//
//        btn_title_right = (Button) titleView.findViewById(R.id.btn_title_right);
//        btn_title_right.setBackgroundResource(R.drawable.ic_share_black);
//        btn_title_right.setVisibility(View.VISIBLE);

        this.ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        this.webview = (WebView) findViewById(R.id.webview);
        initWebview(webview);

        if ( !fillWebviewWithHtmlData(webview) ) {
            this.webview.loadUrl(getUrlNotNull());
        }
    }

    private void initWebview(final WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        String userAgentString = webview.getSettings().getUserAgentString() + Const.PengsiWebAgentPlus;
        webview.getSettings().setUserAgentString(userAgentString);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(getActivityTitle())) {
                    tv_title.setText(title);
                } else {
                    tv_title.setText(getActivityTitle());
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pBar.setVisibility(View.GONE);
                } else {
                    if (pBar.getVisibility() == View.GONE)
                        pBar.setVisibility(View.VISIBLE);
                    pBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = getIntentFromUrl(mBaseContext, url);

                if (intent != null){
                    UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
                    sanitizer.setAllowUnregisteredParamaters(true);
                    String cb = sanitizer.getValue("cb");

                    startActivity(intent);
                    if (url.contains("show?showid") && "1".equalsIgnoreCase(cb)){
                        finish();
                    }
                    return true;
                }

                return shouldOverrideUrlLoadingInBase(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                onPageStartedInBase(webview, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isWebPageFinished = true;

//                view.loadUrl("javascript:window.pengsiid=" + Utility.getUserId());
//				view.loadUrl("javascript:window.demo.clickDemo1(" + "document.getElementsByTagName('title')[0].innerText" + ");");
//				view.loadUrl("javascript:window.demo.clickDemo2(" + "document.getElementsByName('description')[0].attributes['content'].value" + ");");
//				view.loadUrl("javascript:window.demo.clickDemo3(" + "document.getElementsByTagName('img')[0].attributes['src'].value" + ");");
//				view.loadUrl("javascript:window.demo.clickDemo4(" + "title" + ");");
//				view.loadUrl("javascript:window.demo.clickDemo5(" + "description" + ");");
//				view.loadUrl("javascript:window.demo.clickDemo6(" + "imgPath" + ");");
//				view.loadUrl("javascript:window.demo.clickDemo7(" + "isHideLink" + ");");
//				view.loadUrl("javascript:window.demo.clickDemo8(" + "pengsi_url" + ");");

                onPageFinishedInBase(view, url);
            }
        });

    }

    public static Intent getIntentFromUrl(Context mContext, String url){
        Intent intent = null;
        if (!TextUtils.isEmpty(url) && url.contains("pengsi://")) {
            url.replaceAll(" ","");

            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
            sanitizer.setAllowUnregisteredParamaters(true);

//            if (url.contains("user?userid")) {
//                String userid = sanitizer.getValue("userid");
//
//                if ( !TextUtils.isEmpty(userid)){
//                    intent = new Intent(mContext, Act_Home.class);
//                    intent.putExtra(Const.KEY_USERID, userid);
//                }
//            } else if (url.contains("showid=") && url.contains("showType=")) {
//                String showid = sanitizer.getValue("showid");
//                String showType = sanitizer.getValue("showType");
//
//                if (!TextUtils.isEmpty(showid) && !TextUtils.isEmpty(showType)){
//                    intent = new Intent(mContext, Act_ActivityDetial.class);
//                    intent.putExtra(Const.KEY_PARAM_SHOWID, showid);
//                    intent.putExtra(Const.KEY_PARAM_TYPE, showType);
//                }
//            } else if (url.contains("show?v")) {
//                String v = sanitizer.getValue("v");
//
//                if (!TextUtils.isEmpty(v)){
//                    intent = new Intent(mContext, Act_ActivityTouPiao.class);
//                    intent.putExtra(Const.KEY_PARAM_PLAYERID, v);
//                }
//            }

        }
        return intent;
    }


    public void onTitleBtnClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_title_left:
//                finish();
//                break;
//            case R.id.btn_title_right:// 分享
//                if (isWebPageFinished) {
//                    share();
//                } else {
//                    CommonUtil.showShortToast(mBaseContext, "请稍等，网页还未加载完成！");
//                }
//                break;
        }
    }

    public String getShareTitle() {
        if (!TextUtils.isEmpty(defaultTitle)) {
            return defaultTitle;
        } else if (!TextUtils.isEmpty(title)) {
            return title;
        }
        return "捧丝app分享";
    }

    public String getShareDescription() {
        if (!TextUtils.isEmpty(defaultDescription)) {
            return defaultDescription;
        } else if (!TextUtils.isEmpty(description)) {
            return description;
        }
        return getShareTitle();
    }

    public String getShareImage() {
        if (!TextUtils.isEmpty(defaultImgPath)) {
            return defaultImgPath;
        } else if (!TextUtils.isEmpty(imgPath)) {
            return imgPath;
        }
        return "";
    }

    String title, description, imgPath, defaultTitle, defaultDescription, defaultImgPath, defaultShareUrl;

    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        @JavascriptInterface
        public void clickDemo1(String s) {
            title = s;
        }

        @JavascriptInterface
        public void clickDemo2(String s) {
            description = s;
        }

        @JavascriptInterface
        public void clickDemo3(String s) {
            imgPath = s;
        }

        @JavascriptInterface
        public void clickDemo4(String s) {
            defaultTitle = s;
        }

        @JavascriptInterface
        public void clickDemo5(String s) {
            defaultDescription = s;
        }

        @JavascriptInterface
        public void clickDemo6(String s) {
            defaultImgPath = s;
        }

        @JavascriptInterface
        public void clickDemo7(String isHideLink) {
            if ("true".equalsIgnoreCase(isHideLink)) {
                btn_title_right.setVisibility(View.GONE);
            }
        }

        @JavascriptInterface
        public void clickDemo8(String s) {
            defaultShareUrl = s;
        }

//        @JavascriptInterface
//        public String getPSUserid() {
//            return Utility.getUserId();
//        }

    }

    

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * @param view
     * @param url
     * @return
     */
    public boolean shouldOverrideUrlLoadingInBase(WebView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        }
        return true;
    }

    public void onPageStartedInBase(WebView view, String url, Bitmap favicon) {

    }

    public void onPageFinishedInBase(WebView view, String url) {

    }


    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }

    public boolean fillWebviewWithHtmlData(WebView webview) {
        return false;
    }

}
