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

package com.naivor.app.presentation.view;

/**
 * BaseUiView 是应用中所有UiView的顶级抽象类，适合抽取UiView的公共方法和属性
 *
 * UiView：MVP架构中的V。
 *
 * Created by tianlai on 16-3-3.
 */
public interface BaseUiView {

    /**
     * showLoading 方法主要用于页面请求数据时显示加载状态
     */
    public void showLoading();

    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    public void showEmpty();

    /**
     * showError 方法用于请求数据出错
     */
    public void showError();

    /**
     * showError 方法用于请求数据完成
     */
    public void loadingComplete();


}
