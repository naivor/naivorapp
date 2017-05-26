package com.naivor.app.features.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.naivor.adapter.AdapterOperator;
import com.naivor.adapter.ListAdapter;
import com.naivor.adapter.ListHolder;
import com.naivor.app.R;

import javax.inject.Inject;

import butterknife.BindView;
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
    public ListHolder<String> onCreateViewHolder(View view, int i) {
        return new HomeViewHolder(view);
    }

    /**
     * 获取布局资源
     *
     * @param viewType
     * @return
     */
    @Override
    public int getLayoutRes(int viewType) {
        return R.layout.list_item_home;
    }

    /**
     * Created by naivor on 16-5-21.
     */
    static class HomeViewHolder extends ListHolder<String> {

        @BindView(R.id.tv_text)
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
