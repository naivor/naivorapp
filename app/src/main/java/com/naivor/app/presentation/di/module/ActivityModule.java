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

package com.naivor.app.presentation.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.naivor.app.presentation.di.PerActivity;
import com.naivor.app.presentation.ui.activity.BaseActivity;
import com.naivor.app.presentation.ui.fragment.DateFragment;
import com.naivor.app.presentation.ui.fragment.HomeFragment;
import com.naivor.app.presentation.ui.fragment.MineFragment;
import com.naivor.app.presentation.ui.fragment.OrderFragment;
import com.naivor.widget.requestdialog.LoadingDialog;

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
    HomeFragment provideHomeFragment() {
        return new HomeFragment();
    }

    @PerActivity
    @Provides
    OrderFragment provideOrderFragment() {
        return new OrderFragment();
    }

    @PerActivity
    @Provides
    DateFragment provideDateFragment() {
        return new DateFragment();
    }

    @PerActivity
    @Provides
    MineFragment provideMineFragment() {
        return new MineFragment();
    }

}
