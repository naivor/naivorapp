package com.naivor.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.naivor.widget.R;

public class SquareLayout2 extends FrameLayout {

	public SquareLayout2(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		
		init();

	}

	private void init() {
		if (isInEditMode()) {

		}
	}

	public SquareLayout2(Context context, AttributeSet attrs) {

		super(context, attrs);

		init();
	}

	public SquareLayout2(Context context) {

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