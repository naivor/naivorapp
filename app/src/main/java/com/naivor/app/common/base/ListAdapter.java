package com.naivor.app.common.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * absListview的适配器基类
 *
 * Created by naivor on 16-4-12.
 */
public abstract class ListAdapter<T> extends BaseAdapter implements AdapterOperator<T>{
    protected  final String TAG=this.getClass().getSimpleName();

    protected Context context;
    protected LayoutInflater inflater;

    protected List<T> itemDatas;

    private ListHolder<T> viewHolder;

    protected InnerClickListener innerClickListener;

    public ListAdapter(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;

        itemDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return itemDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (position < getCount()) {
            return itemDatas.get(position);
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = onCreateViewHolder(parent,getItemViewType(position), inflater);
            convertView = viewHolder.getConvertView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListHolder<T>) convertView.getTag();
        }

        onBindViewHolder(viewHolder,position);

        return convertView;
    }

    /**
     * 通过重写此方法,不用设置数据,更改getitemcount的值就可以看到效果
     *
     * @param viewHolder
     * @param position
     */
    public void onBindViewHolder(ListHolder<T> viewHolder, int position) {
        viewHolder.bindData(this,position, getItem(position));
    }

    /**
     * 创建viewholder
     *
     * @param parent
     * @param viewType
     * @param inflater
     * @return
     */
    public abstract ListHolder<T> onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater);

    /**
     * 判断数据是否为空
     */
    public boolean isEmpty() {
        return itemDatas.isEmpty();
    }

    /**
     * 添加数据
     */
    public void addItems(List<T> list) {
        if (list != null) {
            itemDatas.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 替换数据
     *
     * @param originItem
     * @param newItem
     */
    public void replaceItem(T originItem, T newItem) {
        if (itemDatas.contains(originItem)) {
            int position = itemDatas.indexOf(originItem);
            itemDatas.remove(position);
            itemDatas.add(position, newItem);

            notifyDataSetChanged();
        }
    }


    /**
     * 添加数据
     */
    public void addItems(int position, List<T> list) {
        if (list != null) {
            itemDatas.addAll(position, list);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加单个数据
     */
    public void addItem(T item) {
        if (item != null) {
            itemDatas.add(item);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加单个数据
     */
    public void addItem(int position, T item) {
        if (item != null && position >= 0) {
            itemDatas.add(position, item);
            notifyDataSetChanged();
        }
    }

    /**
     * 删除数据
     */
    public void removeItem(int position) {
        if (position >= 0 && position < getCount()) {

            itemDatas.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 删除数据
     */
    public void removeItem(T data) {
        if (data != null && itemDatas.contains(data)) {

            itemDatas.remove(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置新数据，原来的清空
     */
    public void setItems(List<T> list) {
        itemDatas.clear();

        if (list != null) {
            itemDatas.addAll(list);

            notifyDataSetChanged();
        }

    }

    /**
     * 清空
     */
    public void clearItems() {
        if (!isEmpty()) {
            itemDatas.clear();
            notifyDataSetChanged();
        }
    }

    public List<T> getDatas() {
        return itemDatas;
    }


    public InnerClickListener getInnerClickListener() {
        return innerClickListener;
    }

    public void setInnerClickListener(InnerClickListener innerClickListener) {
        this.innerClickListener = innerClickListener;
    }
}
