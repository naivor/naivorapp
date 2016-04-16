package com.naivor.app.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.naivor.app.presentation.adapter.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naivor on 16-4-12.
 */
public abstract class BaseAbsListAdapter<T,VH extends BaseViewHolder<T>> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater inflater;

    protected List<T> itemDatas;
    private VH viewHolder;

    public BaseAbsListAdapter(Context context, LayoutInflater inflater) {
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
        return itemDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            viewHolder=onCreateViewHolder(parent,getItemViewType(position),inflater);
            convertView=viewHolder.getConvertView();
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (VH) convertView.getTag();
        }

        viewHolder.loadDataToView(position,getItem(position));

        return convertView;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater);

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
        if (list != null) {
            itemDatas.clear();

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

}
