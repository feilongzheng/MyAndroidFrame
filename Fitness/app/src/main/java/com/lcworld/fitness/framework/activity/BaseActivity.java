package com.lcworld.fitness.framework.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcworld.fitness.R;
import com.lcworld.fitness.framework.application.BaseApplication;
import com.lcworld.fitness.framework.util.CommonUtil;
import com.lcworld.fitness.selfviews.dialog.LoadingDialog;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * 调了setContentViewInBase(..)才会生成baseLayout
     */
	public BaseLayout baseLayout = null;
    public Context mBaseContext = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mBaseContext = this;
		BaseApplication.undestroyActivities.add(this);

        initView();
		setData();
	}

    @Override
    public void onClick(View v) {

    }


    @Override
	protected void onDestroy() {
		super.onDestroy();
		//从unDestroyActivityList里面移除当前的activity
		BaseApplication.undestroyActivities.remove(this);

		//如果progressDialog没关，关闭progressDialog
        LoadingDialog.dismissIfExist();
	
		//软键盘没关闭，则关闭软键盘
		CommonUtil.closeSoftKeyBoard(this);
	}
	
	/**
	 * 代替setContentView(id);
	 * @param layoutId
	 */
	protected void setContentViewInBase(int layoutId, String title){
		View view = View.inflate(this, layoutId, null);
		setContentViewInBase(view, title);
	}

	/**
	 * 代替setContentView(view)
	 * @param view
	 */
	protected void setContentViewInBase(View view, String title){
		baseLayout = new BaseLayout();

        if ( !TextUtils.isEmpty(title)){
            baseLayout.top_tab_center_title.setText(title);
        }
		baseLayout.ll_container.addView(view);

        if ( !modifyTopTabLeftImage(baseLayout.top_tab_left_image)){
            baseLayout.top_tab_left_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }

		this.setContentView(baseLayout.base_layout);
	}
	
	/**
	 * 修改top bar 返回键的处理时return true;
	 */
	public boolean modifyTopTabLeftImage(ImageView top_tab_left_image) {
        return false;
	}

	public class BaseLayout{
		/**
		 * 带top bar的外层框架
		 */
		public View base_layout;
		/**
		 * top bar 的返回图标
		 */
		public ImageView top_tab_left_image;
		/**
		 * 外层框架中间包裹view的容器
		 */
		public LinearLayout ll_container;
		/**
		 * top bar 中间的ImageView
		 */
		public ImageView top_tab_center_image;
		/**
		 * top bar 中间的TextView
		 */
		public TextView top_tab_center_title;
		private View emptyView;
		public ImageView top_tab_right_image;
		public TextView top_tab_right_text;
		
		
		public BaseLayout() {
			base_layout = View.inflate(BaseActivity.this, R.layout.base_layout, null);
			top_tab_left_image = (ImageView) base_layout.findViewById(R.id.top_tab_left_image);
			top_tab_center_image = (ImageView) base_layout.findViewById(R.id.top_tab_center_image);
			top_tab_center_title = (TextView) base_layout.findViewById(R.id.top_tab_center_title);
			top_tab_right_image = (ImageView) base_layout.findViewById(R.id.top_tab_right_image);
			top_tab_right_text = (TextView) base_layout.findViewById(R.id.top_tab_right_text);
			
			ll_container = (LinearLayout) base_layout.findViewById(R.id.ll_container);
			emptyView = base_layout.findViewById(R.id.empty);
		}
		
		
	}
	
	
	/**
	 * onCreate第一步
	 */
	public abstract  void initView();
	/**
	 * onCreate第三步
	 */
	public abstract void setData();


	@Override
	protected void onResume() {
//		MobclickAgent.onResume(this);
		/** umeng activity页面统计 */
		super.onResume();
	}

	@Override
	protected void onPause() {
//		MobclickAgent.onPause(this);
		/** umeng activity页面统计 */
		super.onPause();
	}


	public View getEmptyView() {
		return baseLayout.emptyView;
	}
	
	
	


}
