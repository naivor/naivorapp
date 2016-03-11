package com.naivor.calenderdialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.naivor.widget.R;

public class CalenderDialog extends Dialog {
	private DisplayMetrics metrics;

	private  GridView  dateGridView;
	private  TextView  titleView;
	private  ImageView  homeImage;
	
	private  CalenderAdapter  adapter;
	
	private  long  time;
	
	private  Context  context;
	
	private  onDateSelectedListener  onDateSelectedListener;

	public CalenderDialog(Context context ) {
		super(context, R.style.dialog);

		init(context);

	}

	private void init(Context context) {
		this.context=context;

		metrics=context.getResources().getDisplayMetrics();
	}

	public CalenderDialog(Context context, int theme) {
		super(context, theme);


		init(context);
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		
		getWindow().setLayout(metrics.widthPixels, metrics.heightPixels);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_calender);
		
		dateGridView=(GridView) findViewById(R.id.date_gridview);
		titleView=(TextView) findViewById(R.id.actionbar_title);
		titleView.setText("选择日期");
		homeImage=(ImageView) findViewById(R.id.actionbar_home);
		
		homeImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		adapter=new CalenderAdapter(getContext(), time);
		dateGridView.setAdapter(adapter);
		dateGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (onDateSelectedListener!=null) {
					onDateSelectedListener.selectDate((long)adapter.getItem(position));
				}
				
				dismiss();
			}
		});
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public  interface  onDateSelectedListener{
			public  void  selectDate(long  date);
	}

	public onDateSelectedListener getOnDateSelectedListener() {
		return onDateSelectedListener;
	}

	public void setOnDateSelectedListener(onDateSelectedListener onDateSelectedListener) {
		this.onDateSelectedListener = onDateSelectedListener;
	}
	
}
