package com.naivor.widget.unscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import com.naivor.widget.R;

//自定义ListView，重写onMeasure方法，使其失去滑动属性，这样才能嵌套在同样具有滑动属性的ScrollView中了。
public class UnScrollListView extends ListView {

	public UnScrollListView(Context context) {
		super(context);
	}

	public UnScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UnScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
