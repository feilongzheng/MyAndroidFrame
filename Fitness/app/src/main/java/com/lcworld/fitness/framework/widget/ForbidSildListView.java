package com.lcworld.fitness.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ForbidSildListView extends ListView {

	public ForbidSildListView(Context context) {
		super(context);
	}
    
	public ForbidSildListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightSpec);
	}
}
