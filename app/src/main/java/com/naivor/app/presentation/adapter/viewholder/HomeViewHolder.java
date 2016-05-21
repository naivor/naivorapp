package com.naivor.app.presentation.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.presentation.adapter.BaseAbsListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by naivor on 16-5-21.
 */
public class HomeViewHolder extends BaseViewHolder<String> {

    @Bind(R.id.tv_text)
    TextView tvText;

    public HomeViewHolder(View convertView, BaseAbsListAdapter absListAdapter) {
        super(convertView, absListAdapter);

        ButterKnife.bind(this, convertView);

    }

    @Override
    public void loadDataToView(int position, String data) {
        super.loadDataToView(position, data);

        tvText.setText(data);
    }
}
