package com.naivor.requestdialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.naivor.widget.R;

public class LoadingDialog extends Dialog {
	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.dialog_request_layout, null);

		setContentView(view);
	}
}
