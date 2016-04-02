/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.presentation.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.naivor.app.presentation.ui.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * AppBaseAdapter 适配器基类
 *
 * @param <T>
 * @author tianlai
 */
public class AppBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> itemDatas;

    protected int  layoutId;
    protected BaseViewHolder viewHolder;

    protected Set<Integer> positions;

    @Inject
    public AppBaseAdapter(Context context, LayoutInflater inflater) {
        super();
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
        if (convertView == null) {
            convertView=inflater.inflate(layoutId,parent,false);
        }

        viewHolder.handView(this,convertView,position);

        return convertView;
    }

    /**
     * 设置layoutid
     *
     * @param layoutId
     * @return
     */
    public AppBaseAdapter layoutId(int layoutId){
        this.layoutId=layoutId;
        return  this;
    }

    /**
     * @param viewHolder
     * @return
     */
    public AppBaseAdapter viewHolder(BaseViewHolder viewHolder){
        this.viewHolder=viewHolder;
        return  this;
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
