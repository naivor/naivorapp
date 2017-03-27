package com.naivor.app.common.base;

import android.view.View;

/**
 * 列表类的内部控件点击监听
 *
 * Created by tianlai on 16-7-16.
 */
public interface InnerClickListener<T> {
    public void onClick(View view, T itemData, int postition);
}
