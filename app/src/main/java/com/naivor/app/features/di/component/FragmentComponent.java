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

package com.naivor.app.features.di.component;


import com.naivor.app.features.di.PerFragment;
import com.naivor.app.features.di.module.FragmentModule;
import com.naivor.app.modules.partfour.PartFourFragment;
import com.naivor.app.modules.partone.PartOneFragment;
import com.naivor.app.modules.partthree.PartThreeFragment;
import com.naivor.app.modules.parttwo.PartTwoFragment;

import dagger.Component;

/**
 * Created by tianlai on 16-3-10.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class,modules =FragmentModule.class)
public interface FragmentComponent {

    ////要注入的类型
    void inject(PartOneFragment partOneFragment);

    void inject(PartTwoFragment partTwoFragment);

    void inject(PartThreeFragment partThreeFragment);

    void inject(PartFourFragment partFourFragment);


    //要暴露给其他依赖本组件的组件的依赖方法


    //子Component

}
