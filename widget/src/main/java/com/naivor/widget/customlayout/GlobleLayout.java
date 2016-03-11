package com.naivor.widget.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.naivor.widget.R;

public class GlobleLayout extends LinearLayout {

	public GlobleLayout(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);

		init();

	}

	private void init() {
		if (isInEditMode()) {

		}
	}

	public GlobleLayout(Context context, AttributeSet attrs) {

		super(context, attrs);

		init();
	}

	public GlobleLayout(Context context) {

		super(context);

		init();
	}

	@Override

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(width*3/2, MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

}