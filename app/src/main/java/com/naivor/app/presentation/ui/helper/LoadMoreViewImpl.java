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

package com.naivor.app.presentation.ui.helper;


import com.naivor.app.presentation.adapter.BaseAbsListAdapter;

/**
 * Created by tianlai on 16-4-18.
 */
public interface LoadMoreViewImpl {

    /**
     * 获取客户列表的适配器
     *
     * @return
     */
    public BaseAbsListAdapter getListAdapter();

    /**
     * @param showEmpty
     */
    public void showDataOrEmpty(boolean showEmpty);
    /**
     * 设置listview是否可以加载更多
     *
     * @param hasMoreData
     */
    public abstract void setHasMoreData(boolean hasMoreData);

    /**
     *隐藏emptyview
     */
    public abstract void hideEmptyView();

    /**
     * listview底部置为初始状态
     */
    public abstract void resetBottomToOrigin();
}
