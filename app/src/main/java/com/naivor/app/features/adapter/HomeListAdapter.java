package com.naivor.app.features.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naivor.app.R;
import com.naivor.app.common.base.AdapterOperator;
import com.naivor.app.common.base.ListAdapter;
import com.naivor.app.common.base.ListHolder;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by naivor on 16-5-21.
 */
public class HomeListAdapter extends ListAdapter<String> {

    @Inject
    public HomeListAdapter(Context context, LayoutInflater inflater) {
        super(context, inflater);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return new HomeViewHolder(inflater.inflate(R.layout.list_item_home,null));
    }

    /**
     * Created by naivor on 16-5-21.
     */
    static class HomeViewHolder extends ListHolder<String> {

        @Bind(R.id.tv_text)
        TextView tvText;

        public HomeViewHolder(View convertView) {
            super(convertView);

            ButterKnife.bind(this, convertView);

        }

        @Override
        public void bindData(AdapterOperator<String> operator, int position, String itemData) {
            super.bindData(operator, position, itemData);

            tvText.setText(itemData);
        }
    }

}
