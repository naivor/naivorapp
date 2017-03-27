package com.naivor.app.others.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.naivor.app.R;


public class LoadingDialog extends Dialog {
	public LoadingDialog(Context context) {
		super(context, R.style.AppTheme_Dialog);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.dialog_loading, null);

		setContentView(view);
	}
}
