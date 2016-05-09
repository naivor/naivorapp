package com.naivor.app.presentation.adapter.viewholder;

import android.content.Context;
import android.view.View;

import com.naivor.app.presentation.adapter.BaseAbsListAdapter;


/**
 * Created by naivor on 16-4-12.
 */
public abstract class BaseViewHolder<T> {
    protected Context context;
    protected BaseAbsListAdapter absListAdapter;

    protected View convertView;

    protected int position;
    protected T data;

    public BaseViewHolder(View convertView,BaseAbsListAdapter absListAdapter) {
        this.convertView = convertView;
        context=convertView.getContext();
        this.absListAdapter=absListAdapter;

    }

    public View getConvertView() {
        return convertView;
    }

    public   void loadDataToView(int position, T data){
        this.position=position;
        this.data=data;
    }

    /**
     * 查找控件
     *
     * @param viewId
     * @return
     */
    public View find(int viewId) {
        return convertView.findViewById(viewId);

    }

    public static interface OnInnerViewClickListener<T> {
        public void onClick(View view, T itemData, int postition);
    }
}
