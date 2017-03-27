package com.naivor.app.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recyclerView的viewholder基类
 * <p/>
 * Created by tianlai on 16-7-16.
 */
public abstract class RecyHolder<T> extends RecyclerView.ViewHolder implements HolderOperator<T>{
    protected  final String TAG = this.getClass().getSimpleName();

    protected T itemData;
    protected int position;

    protected Context context;

    protected RecyAdapter adapter;

    protected InnerClickListener clickListener;

    public RecyHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }


    /**
     * 绑定数据
     *
     * @param itemData
     * @param position
     * @param operator
     */
    public void bindData(AdapterOperator<T> operator,int position, T itemData ){
        this.itemData = itemData;
        this.position = position;

        if (operator instanceof RecyAdapter) {
            adapter = (RecyAdapter) operator;
        }

        if (adapter != null) {
            clickListener = adapter.getInnerClickListener();
        }
    }

    /**
     * 查找控件
     *
     * @param viewId
     * @return
     */
    public View find(int viewId) {
        return itemView.findViewById(viewId);

    }

    /**
     * 查找控件
     *
     * @param viewId
     * @return
     */
    public View find(View itemView, int viewId) {
        return itemView.findViewById(viewId);

    }
}
