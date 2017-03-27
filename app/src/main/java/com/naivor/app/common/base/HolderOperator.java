package com.naivor.app.common.base;

import android.view.View;

/**
 * viewholder 操作类
 * <p>
 * Created by tianlai on 17-3-27.
 */

public interface HolderOperator<T> {

    /**
     * 绑定数据
     *
     * @param position
     * @param data
     * @param operator
     */
    void bindData(AdapterOperator<T> operator,int position, T data );


    /**
     * 查找控件
     *
     * @param viewId
     * @return
     */
    View find(int viewId);

    /**
     * 查找控件
     *
     * @param viewId
     * @return
     */
    View find(View itemView, int viewId);
}
