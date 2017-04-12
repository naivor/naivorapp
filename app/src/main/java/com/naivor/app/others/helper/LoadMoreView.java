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

package com.naivor.app.others.helper;


import com.naivor.adapter.AdapterOperator;

/**
 * 加载更多的view操作接口
 * <p>
 * Created by tianlai on 16-4-18.
 */
public interface LoadMoreView {

    /**
     * 获取客户列表的适配器
     *
     * @return
     */
    AdapterOperator getAdapter();

    /**
     * 是否加载更多
     */
    boolean isLoadMore();

    /**
     * 设置是否有更多数据
     *
     * @param hasMore
     */
    void setHasMore(boolean hasMore);

    /**
     * 隐藏空白页
     */
    void hideEmpty();

}
