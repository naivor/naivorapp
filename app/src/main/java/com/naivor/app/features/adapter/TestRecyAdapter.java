package com.naivor.app.features.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.naivor.adapter.AdapterOperator;
import com.naivor.adapter.RecyAdapter;
import com.naivor.adapter.RecyHolder;
import com.naivor.app.R;
import com.naivor.app.common.model.SimpleItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * recyclerAdapter测试
 * <p>
 * Created by tianlai on 17-4-2.
 */

public class TestRecyAdapter extends RecyAdapter<SimpleItem> {

    @Inject
    public TestRecyAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder createHolder(View view, int i) {
        return new SHolder(view);
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


    static class SHolder extends RecyHolder<SimpleItem> {

        @BindView(R.id.tv_text)
        TextView tvText;

        public SHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(AdapterOperator<SimpleItem> operator, int position, SimpleItem itemData) {
            super.bindData(operator, position, itemData);

            tvText.setText(itemData.getContent()+"编号："+position);
        }
    }
}
