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

package com.naivor.app.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.naivor.app.presentation.di.PerActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewPagerAdapter  为容纳Fragment的Viewpager提供的适配器
 *
 * Created by tianlai on 16-3-21.
 */
@PerActivity
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    @Inject
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     *  传入fragment数据源
     *
     * @param fragments
     */
    public void setDate( List<Fragment> fragments){
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int paramInt) {

        return mFragments.get(paramInt);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}