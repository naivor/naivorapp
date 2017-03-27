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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.naivor.app.common.base.BaseActivity;
import com.naivor.app.features.di.PerActivity;
import com.naivor.app.modules.partfour.PartFourFragment;
import com.naivor.app.modules.partone.PartOneFragment;
import com.naivor.app.modules.partthree.PartThreeFragment;
import com.naivor.app.modules.parttwo.PartTwoFragment;
import com.naivor.app.others.widget.LoadingDialog;

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
    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    BaseActivity provideBaseActivity(){
        return this.baseActivity;
    }

    //需要提供的公共对象

    @PerActivity
    @Provides
    FragmentManager provideFragmentManager() {
        return baseActivity.getSupportFragmentManager();
    }

    @PerActivity
    @Provides
    LoadingDialog provideRequestDialog() {
        return new LoadingDialog(baseActivity);
    }

    @PerActivity
    @Provides
    List<Fragment> provideFragments(){
        return new ArrayList<>();
    }

    //需要提供的fragment
    @PerActivity
    @Provides
    PartOneFragment provideHomeFragment() {
        return new PartOneFragment();
    }

    @PerActivity
    @Provides
    PartTwoFragment provideOrderFragment() {
        return new PartTwoFragment();
    }

    @PerActivity
    @Provides
    PartThreeFragment provideDateFragment() {
        return new PartThreeFragment();
    }

    @PerActivity
    @Provides
    PartFourFragment provideMineFragment() {
        return new PartFourFragment();
    }

}
