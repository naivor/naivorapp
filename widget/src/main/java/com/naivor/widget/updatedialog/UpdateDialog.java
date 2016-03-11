package com.naivor.widget.updatedialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.naivor.widget.R;


public class UpdateDialog extends Dialog {
    private DisplayMetrics metrics;

    private Context context;

    private String updateVersion;
    private String updateContent;

    private onCancleListerer onCancleListerer;
    private onSureListerer onSureListerer;

    public UpdateDialog(Context context) {
        super(context, R.style.dialog);

        init(context);
    }

    private void init(Context context2) {
        this.context = context2;

        metrics = context2.getResources().getDisplayMetrics();
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);

        init(context);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Window window = getWindow();
        window.setLayout(dp2px(240), metrics.heightPixels / 2);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.y = dp2px(-50);
        window.setAttributes(lp);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_update);

        ((TextView) findViewById(R.id.update_date_version)).setText(updateVersion);
        ((TextView) findViewById(R.id.update_content)).setText(updateContent);
        ;

        findViewById(R.id.update_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancleListerer != null) {
                    onCancleListerer.onClick(v);
                }

                dismiss();
            }
        });
        findViewById(R.id.update_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSureListerer != null) {
                    onSureListerer.onClick(v);
                }

                dismiss();
            }
        });

    }

    public static interface onCancleListerer {
        public void onClick(View v);
    }

    public static interface onSureListerer {
        public void onClick(View v);
    }

    public onCancleListerer getOnCancleListerer() {
        return onCancleListerer;
    }

    public void setOnCancleListerer(onCancleListerer onCancleListerer) {
        this.onCancleListerer = onCancleListerer;
    }

    public onSureListerer getOnSureListerer() {
        return onSureListerer;
    }

    public void setOnSureListerer(onSureListerer onSureListerer) {
        this.onSureListerer = onSureListerer;
    }

    public void setVersionInfo(String text) {
        this.updateVersion = text;
    }

    public void setUpdateContent(String text) {
        this.updateContent = text;
    }

    /**
     * 将dp转换成px
     *
     * @return
     */
    private int dp2px(float dp) {

        return (int) (metrics.density * dp + 0.5f);

    }

}
