package com.naivor.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import com.naivor.widget.R;

/**
 * 自定义的scrollview，可以监控滑动的距离
 * 
 * @author tianlai
 *
 */
public class CustomScrollView extends ScrollView {
	private onScrollListerer scrollListerer;
//	private boolean mDisableEdgeEffects = true;

	public CustomScrollView(Context context) {
		super(context);

		init();
	}

	// xml文件中可预览
	private void init() {
		if (isInEditMode()) {
			return;
		}
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (scrollListerer!=null) {
			scrollListerer.onScrolled(this,l, t, oldl, oldt);
		}
	}

	public interface onScrollListerer {
		public void onScrolled(ScrollView  who,int l, int t, int oldl, int oldt);
	}

	public onScrollListerer getScrollListerer() {
		return scrollListerer;
	}

	public void setScrollListerer(onScrollListerer scrollListerer) {
		this.scrollListerer = scrollListerer;
	}

	private float x = 0;
	private float y = 0;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = ev.getX();
			y = ev.getY();

			break;
		case MotionEvent.ACTION_MOVE:
			float nowX = ev.getX();
			float nowY = ev.getY();

			float scrollX = nowX - x;
			float scrollY = nowY - y;

			if (Math.abs(scrollX) > Math.abs(scrollY)) { //
				return false;
			} else if (Math.abs(scrollX) < Math.abs(scrollY)) {
				return true;
			}

			break;

		default:
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}
	
//	@Override
//    protected float getTopFadingEdgeStrength() {
//        // http://stackoverflow.com/a/6894270/244576
//        if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//            return 0.0f;
//        }
//        return super.getTopFadingEdgeStrength();
//    }
// 
//    @Override
//    protected float getBottomFadingEdgeStrength() {
//        // http://stackoverflow.com/a/6894270/244576
//        if (mDisableEdgeEffects && Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//            return 0.0f;
//        }
//        return super.getBottomFadingEdgeStrength();
//    }


}
