package com.naivor.app.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView的适配器基类
 * <p>
 * Created by tianlai on 16-7-11.
 */
public abstract class RecyAdapter<T> extends RecyclerView.Adapter implements AdapterOperator<T> {
    protected static final String TAG = "BaseRecyclerAdapter";

    protected Context context;
    protected LayoutInflater inflater;

    protected List<T> itemDatas;

    protected InnerClickListener innerClickListener;

    public RecyAdapter(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;

        itemDatas = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return itemDatas.size();
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyHolder<T>) holder).bindData(this, position,itemDatas.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


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
            itemDatas.set(position, newItem);

//            notifyItemChanged(position);

            notifyDataSetChanged();
        }
    }

    /**
     * 替换数据
     *
     * @param position
     * @param newItem
     */
    public void replaceItem(int position, T newItem) {
        itemDatas.set(position, newItem);

//        notifyItemChanged(position);

        notifyDataSetChanged();
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
        if (position >= 0 && position < getItemCount()) {

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
