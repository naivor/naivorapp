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

package com.naivor.app.presentation.ui.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naivor.app.presentation.presenter.adapter.AppBaseAdapter;

/**
 * BaseViewHolder ViewHolder的基类
 * <p/>
 * Created by tianlai on 16-3-21.
 */
public abstract class BaseViewHolder<T> {
    protected AppBaseAdapter adapter;
    protected View parentView;
    protected View view;
    protected int position;
    protected Context context;
    protected T item;

    public BaseViewHolder() {
    }

    public void handView(AppBaseAdapter adapter, View view, int position) {

        this.adapter = adapter;
        this.parentView = view;
        this.position = position;

        context = parentView.getContext();

        item = (T) adapter.getItem(position);

        loadDataToView(this, item);
    }

    protected abstract void loadDataToView(BaseViewHolder viewholder, T data);

    /**
     * 查找控件
     *
     * @param viewId
     * @return
     */
    public BaseViewHolder find(int viewId) {
        view = parentView.findViewById(viewId);

        return this;
    }

    /**
     * 查找控件
     *
     * @param viewId
     * @return
     */
    public View getView(int viewId) {
        view = parentView.findViewById(viewId);

        return view;
    }

    /**
     * 加载布局
     */
    public void inflate() {
        if (view instanceof ViewStub) {
            ((ViewStub) view).inflate();
        }
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setText(CharSequence text) {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public BaseViewHolder append(CharSequence text) {
        if (view instanceof TextView) {
            ((TextView) view).append(text);
        }

        return this;
    }

    /**
     * 设置文本
     *
     * @return
     */
    public BaseViewHolder append0() {
        if (view instanceof TextView) {
            ((TextView) view).setText("");
        }

        return this;
    }

    /**
     * 设置图片
     *
     * @param drawable
     */
    public void setImage(Drawable drawable) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawable);
        }

    }

    /**
     * 设置图片
     *
     * @param resId
     */
    public void setImageRes(int resId) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(resId);
        }

    }

    /**
     * 设置图片
     *
     * @param url
     */
    public void setUri(String url) {
        if (view instanceof SimpleDraweeView) {
            ((SimpleDraweeView) view).setImageURI(Uri.parse(url));
        }

    }

    /**
     * 设置显示隐藏
     *
     * @param visible
     */
    public void setVisible(int visible) {
        view.setVisibility(visible);
    }


    public static interface OnInnerViewClickListener<T> {
        public void onClick(View view, T itemData, int postition);
    }
}
