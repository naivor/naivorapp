package com.naivor.widget.orderprogress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.naivor.widget.R;
/**
 * 自定义的订单进度条
 * 
 * @author tianlai
 *
 */
public class OrderProgressView extends FrameLayout {
	private DisplayMetrics metrics;

	private List<ProgressEntity> datas;

	private Context context;

	private int count;

	private List<ImageView> points; // 记录的每个小圆点

	private int point_pass = R.drawable.icon_point_red;
	private int point_future = R.drawable.icon_point_gray;

	private ImageView start, end;

	private float y;

	private int redPointCount;
	private int grayPointCount;
	
	private Paint  paint;
	
	private int line_color_red = Color.parseColor("#ee4876"); // 红色进度条的颜色
	private int line_color_gray = Color.GRAY; // 灰色进度条的颜色


	public OrderProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView(context);
	}

	private void initView(Context context) {
		this.context = context;

		metrics=context.getResources().getDisplayMetrics();
		
		paint=new Paint();
		paint.setAntiAlias(false);
		paint.setStrokeWidth(2);
		paint.setStyle(Style.FILL);

		redPointCount = 0;
		grayPointCount = 0;

		if (isInEditMode()) {

		}
	}

	public OrderProgressView(Context context) {
		super(context);

		initView(context);
	}

	public List<ProgressEntity> getDatas() {
		return datas;
	}

	public void setDatas(List<ProgressEntity> datas) {
		this.datas = datas;

		refreshView();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);

		if (count > 1) {

			for (int i = 0; i < count; i++) {
				if (i != 0) {
					int  []  point1=new int [2];
					int  []  point2=new int [2];
					
					start = points.get(i - 1);
					start.getLocationOnScreen(point1);
					y = (start.getTop() + start.getBottom()) / 2;
					end = points.get(i);
					end.getLocationOnScreen(point2);

					if (datas.get(i).isExecute()) { // 已经发生，画红线
						paint.setColor(line_color_red);
					} else { // 未发生的事件，画灰线
						paint.setColor(line_color_gray);
					}
					canvas.drawLine(point1[0]+start.getWidth(), y,  point2[0], y, paint);

				}
			}

		}

	}

	/**
	 * 刷新视图
	 */
	private void refreshView() {

		if (datas == null) {
			return;
		}

		count = datas.size();

		if (count < 2) {
			return;
		} else {
			//
			LinearLayout layout = new LinearLayout(getContext());
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			params.leftMargin=dp2px(12);
			params.rightMargin=dp2px(12);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setLayoutParams(params);
			addView(layout);

			if (count == 2) {
				for (int i = 0; i < count; i++) {
					layout.addView(getSpaceView());
					layout.addView(getContentView(i));
					layout.addView(getSpaceView());
				}
			} else if (count > 2) {
				for (int i = 0; i < count; i++) {
					layout.addView(getContentView(i));
					if (i != count - 1) {
						layout.addView(getSpaceView());
					}
				}
			}
		}

		postInvalidate();
	}

	/**
	 * 生成占位的View
	 */
	private View getSpaceView() {
		View view = new View(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		params.weight = 1;
		view.setLayoutParams(params);

		return view;
	}

	/**
	 * 生成 每个节点
	 * 
	 * @param i
	 */
	private View getContentView(int i) {
		ProgressEntity entity = datas.get(i);

		// 容纳圆点、订单状态、时间的布局
		RelativeLayout layout = new RelativeLayout(context);
		layout.setId(R.id.iv_1);
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params1);

		// 小圆点
		ImageView imageView = new ImageView(context);
		imageView.setId(R.id.iv_2);
		int width = dp2px(10);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(width, width);
		params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params2.topMargin = dp2px( 8);
		imageView.setLayoutParams(params2);
		// 小圆点数据

		boolean execute = entity.isExecute();
		if (execute) {
			redPointCount++;
			imageView.setImageResource(point_pass);
		} else {
			grayPointCount++;
			imageView.setImageResource(point_future);
		}
		imageView.setTag(execute);

		if (points == null) {
			points = new ArrayList<ImageView>();
		}
		points.add(imageView);

		layout.addView(imageView);

		// 订单的状态名
		TextView statusNameTxt = new TextView(context);
		statusNameTxt.setId(R.id.iv_2);
		RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params3.addRule(RelativeLayout.BELOW, 0x0101);
		params3.topMargin = dp2px( 7);
		statusNameTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		statusNameTxt.setTextColor(Color.parseColor("#333333"));
		statusNameTxt.setLayoutParams(params3);

		String statusName = entity.getStatusName();
		if (statusName != null && !statusName.equals("null")) {
			statusNameTxt.setText(statusName);
		}

		layout.addView(statusNameTxt);

		// 状态发生的日期
		TextView statusDate = new TextView(context);
		statusDate.setId(R.id.tv_2);
		RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params4.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params4.addRule(RelativeLayout.BELOW, 0x0102);
		params4.topMargin = dp2px( 4);
		statusDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		statusDate.setTextColor(Color.parseColor("#333333"));
		statusDate.setLayoutParams(params4);

		long time = entity.getStatusTime();
		if (time > 0) {
			statusDate.setText(getDate("MM月dd日", time * 1000));
		}

		layout.addView(statusDate);

		// 状态发生的日期
		TextView statusTime = new TextView(context);
		statusTime.setId(R.id.tv_3);
		RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params5.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params5.addRule(RelativeLayout.BELOW, 0x0103);
		params5.topMargin = dp2px( 4);
		statusTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		statusTime.setTextColor(Color.parseColor("#333333"));
		statusTime.setLayoutParams(params5);

		if (time > 0) {
			statusTime.setText(getDate("HH:mm", time * 1000));
		}

		layout.addView(statusTime);

		return layout;
	}

	/**
	 * 将dp转换成px
	 *
	 * @return
	 */
	private int dp2px(float dp) {

		return (int) (metrics.density * dp + 0.5f);

	}

	/**
	 * 将时间date转化成String类型
	 *
	 * @param template
	 * @param date
	 * @return
	 */
	private  String getDate(String template, long date) {
		if (date > 0) {
			SimpleDateFormat format = new SimpleDateFormat(template, Locale.CHINA);
			return format.format(new Date(date));
		}
		return "";
	}

}
