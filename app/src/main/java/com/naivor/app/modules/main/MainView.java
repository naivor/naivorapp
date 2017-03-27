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

package com.naivor.app.modules.main;


import com.naivor.app.common.base.BaseUiView;

/**
 * Created by tianlai on 16-3-9.
 */
public interface MainView extends BaseUiView {
    /**
     * 双击退出应用
     */
    public void exitOnClickTwice(int keyCode);

    /**
     * 切换到HomeFragment
     */
    public void toHomePage();

    /**
     * 切换到OrderFragment
     */
    public void toOrderPage();

    /**
     *切换到DateFragment
     */
    public void toDatePage();

    /**
     *切换到MineFragment
     */
    public void toMinePage();

}
