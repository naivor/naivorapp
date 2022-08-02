package com.naivor.android.widget.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.naivor.android.widget.R;

public class SquareLayout extends LinearLayout {

	public SquareLayout(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		
		init();

	}

	private void init() {
		if (isInEditMode()) {

		}
	}

	public SquareLayout(Context context, AttributeSet attrs) {

		super(context, attrs);

		init();
	}

	public SquareLayout(Context context) {

		super(context);

		init();
	}

	@Override

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

		// 高度和宽度一样

		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

}