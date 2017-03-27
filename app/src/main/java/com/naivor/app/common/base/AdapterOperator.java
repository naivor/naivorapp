package com.naivor.app.common.base;

import java.util.List;

/**
 * 适配器操作接口
 * <p>
 * Created by tianlai on 17-3-27.
 */

public interface AdapterOperator<T> {

    /**
     * 判断数据是否为空
     */
    boolean isEmpty();

    /**
     * 添加数据
     */
    void addItems(List<T> list);

    /**
     * 替换数据
     *
     * @param originItem
     * @param newItem
     */
    void replaceItem(T originItem, T newItem);


    /**
     * 添加数据
     */
    void addItems(int position, List<T> list);

    /**
     * 添加单个数据
     */
    void addItem(T item);

    /**
     * 添加单个数据
     */
    void addItem(int position, T item);

    /**
     * 删除数据
     */
    void removeItem(int position);

    /**
     * 删除数据
     */
    void removeItem(T data);

    /**
     * 设置新数据，原来的清空
     */
    void setItems(List<T> list);

    /**
     * 清空
     */
    void clearItems();

    List<T> getDatas();
}
