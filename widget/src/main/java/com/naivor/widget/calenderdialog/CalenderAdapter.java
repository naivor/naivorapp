package com.naivor.calenderdialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.naivor.widget.R;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalenderAdapter extends BaseAdapter {

	private long selectedTime;
	private Context context;

	private long[] dates;

	private int columns = 7;
	private int days = 30;   //可选择的日期是今天和未来的30天，总共是31天

	private SimpleDateFormat format;
	private LayoutInflater inflater;

	private int selectedRes = R.drawable.icon_selected_date;

	public CalenderAdapter(Context context, long selectedTime) {
		super();

		this.context = context;
		this.selectedTime = selectedTime;

		inflater = LayoutInflater.from(context);

		format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.CHINA);

		initData();
	}

	@Override
	public boolean isEnabled(int position) {
		if (dates[position]> 0) {
			return true;
		}

		return false;
	}

	/**
	 * 初始化日期数据
	 */
	private void initData() {
		long time = new Date().getTime();

		int start = getStartIndex(time);
		int count = (((int) (start + days) / columns) + 1) * columns;

		if (start >= 0) {
			if (dates == null) {
				dates = new long[count];

				for (int i = 0; i < days; i++) {
					dates[start + i] = getNextDay(time, i);
				}
			}
		}

	}

	/**
	 * 获取时间开始索引
	 * 
	 * @param time
	 * @return
	 */
	private int getStartIndex(long time) {
		// format = new SimpleDateFormat("EEE", Locale.CHINA);
		format.applyPattern("EEE");
		int index = -1;

		switch (format.format(new Date(time))) {
		case "周日":
			index = 0;
			break;
		case "周一":
			index = 1;
			break;
		case "周二":
			index = 2;
			break;
		case "周三":
			index = 3;
			break;
		case "周四":
			index = 4;
			break;
		case "周五":
			index = 5;
			break;
		case "周六":
			index = 6;
			break;

		default:
			break;
		}

		return index;
	}

	@Override
	public int getCount() {
		if (dates != null) {
			return dates.length;
		}

		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (dates != null) {
			return dates[position];
		}
		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		long timeCell = (long) getItem(position);
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_item, parent, false);
			holder = new ViewHolder();
			convertView.setTag(holder);

			holder.day = (TextView) convertView.findViewById(R.id.day_txt);
			holder.month = (TextView) convertView.findViewById(R.id.month_txt);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (timeCell != 0) {
			// 显示日期
			format.applyPattern("dd");
			String day = format.format(timeCell);
			if (Integer.parseInt(day) < 10) {
				day = day.replace("0", "");
			}
			holder.day.setText(day);

			// 显示月份
			format.applyPattern("MM");
			String month = format.format(timeCell);
			if (Integer.parseInt(month) < 10) {
				month = month.replace("0", "");
			}
			holder.month.setText(month + "月");

			//给原来选中的日期加上背景，字体变白色
			if (isSameDay(timeCell, selectedTime)) {
				convertView.setBackgroundResource(selectedRes);
				holder.day.setTextColor(Color.WHITE);
				holder.month.setTextColor(Color.WHITE);
			}
		}

		return convertView;
	}

	
	class ViewHolder {
		TextView day, month;
	}

	/**
	 * 判断两个时间是否是同一天
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	private   boolean isSameDay(long time1, long time2) {
		Time time = new Time();
		time.set(time1);

		int thenYear = time.year;
		int thenMonth = time.month;
		int thenMonthDay = time.monthDay;

		time.set(time2);
		return (thenYear == time.year) && (thenMonth == time.month) && (thenMonthDay == time.monthDay);
	}

	/**
	 * 获取未来日期的时间戳
	 *
	 * @param startTime
	 * @param index
	 * @return
	 */
	private  long getNextDay(long startTime, int index) { // 获取日期，index表示今天后的第几天
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startTime);
		calendar.add(Calendar.DAY_OF_MONTH, index);
		return calendar.getTime().getTime();
	}

}
