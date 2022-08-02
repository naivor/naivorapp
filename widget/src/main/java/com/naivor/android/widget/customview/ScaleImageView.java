package com.naivor.android.widget.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.naivor.android.widget.R;


public class ScaleImageView extends ImageView {

	private float scaleValue = 0.0f;

	public ScaleImageView(Context context) {
		this(context, null);

	}

	public ScaleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
		scaleValue = a.getFloat(R.styleable.ScaleImageView_ScaleValue, 0.0f);

		if (isInEditMode()) {
			scaleValue = a.getFloat(R.styleable.ScaleImageView_ScaleValue, 0.0f);

		}

		a.recycle();
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

		int width = getMeasuredWidth();
		int height = 0;
		if (scaleValue != 0.0f) {
			height = (int) (width * scaleValue);
		} else {
			height = getMeasuredHeight();
		}

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	public float getScaleValue() {
		return scaleValue;
	}

	public void setScaleValue(float scaleValue) {
		this.scaleValue = scaleValue;
	};

}
