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

package com.naivor.app.presentation.di.component;


import com.naivor.app.presentation.di.PerFragment;
import com.naivor.app.presentation.di.module.FragmentModule;
import com.naivor.app.presentation.ui.fragment.DateFragment;
import com.naivor.app.presentation.ui.fragment.HomeFragment;
import com.naivor.app.presentation.ui.fragment.MineFragment;
import com.naivor.app.presentation.ui.fragment.OrderFragment;

import dagger.Subcomponent;

/**
 * Created by tianlai on 16-3-10.
 */
@PerFragment
@Subcomponent(modules =FragmentModule.class)
public interface FragmentComponent {

    ////要注入的类型
    void inject(HomeFragment homeFragment);

    void inject(OrderFragment orderFragment);

    void inject(DateFragment dateFragment);

    void inject(MineFragment mineFragment);


    //要暴露给其他依赖本组件的组件的依赖方法


    //子Component

}
