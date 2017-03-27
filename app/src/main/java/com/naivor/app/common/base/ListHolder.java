package com.naivor.app.common.base;

import android.content.Context;
import android.view.View;


/**
 * ListAdapter的View容器
 *
 * Created by naivor on 16-4-12.
 */
public abstract class ListHolder<T> implements HolderOperator<T>{
    protected  final String TAG=this.getClass().getSimpleName();

    protected Context context;

    protected View itemView;

    protected int position;
    protected T itemData;
    protected ListAdapter adapter;

    protected InnerClickListener clickListener;

    public ListHolder(View convertView) {
        this.itemView = convertView;
        context = convertView.getContext();

    }

    public View getConvertView() {
        return itemView;
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

        if (operator instanceof ListAdapter) {
            adapter = (ListAdapter) operator;
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
