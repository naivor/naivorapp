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

package com.naivor.app.features.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.naivor.app.features.di.PerActivity;
import com.naivor.app.modules.partfour.FourFragment;
import com.naivor.app.modules.partone.OneFragment;
import com.naivor.app.modules.partthree.ThreeFragment;
import com.naivor.app.modules.parttwo.TwoFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * ActivityModule  Activity的模块，为Activity里面需要自动实例化的类提供依赖
 * <p/>
 * Created by tianlai on 16-3-9.
 */
@Module
public class ActivityModule {
    private Context context;

    public ActivityModule( Context context) {
        this.context = context;
    }

    //需要提供的公共对象

    @PerActivity
    @Provides
    List<Fragment> provideFragments(){
        return new ArrayList<>();
    }

    //需要提供的fragment
    @PerActivity
    @Provides
    OneFragment provideHomeFragment() {
        return new OneFragment();
    }

    @PerActivity
    @Provides
    TwoFragment provideOrderFragment() {
        return new TwoFragment();
    }

    @PerActivity
    @Provides
    ThreeFragment provideDateFragment() {
        return new ThreeFragment();
    }

    @PerActivity
    @Provides
    FourFragment provideMineFragment() {
        return new FourFragment();
    }

}
