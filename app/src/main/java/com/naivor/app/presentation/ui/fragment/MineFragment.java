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

package com.naivor.app.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naivor.app.R;
import com.naivor.app.extras.utils.FontUtil;
import com.naivor.app.presentation.di.component.FragmentComponent;
import com.naivor.app.presentation.presenter.BasePresenter;
import com.naivor.app.presentation.presenter.MineFragmentPresenter;
import com.naivor.app.presentation.view.MineFragmentView;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-18.
 */
public class MineFragment extends BaseFragment implements MineFragmentView{

    @Inject
    MineFragmentPresenter mineFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView=inflater.inflate(R.layout.fragment_mine,container,false);

        return contentView;
    }

    @Override
    protected void initToolbarHelper() {
        //初始化Toolbar显示
        toolbarHelper.setTitle(FontUtil.addColor(baseActivity.getResources().getColor(R.color.deepskyblue), "蓝色页"));
        toolbarHelper.setIsCenterTitleStyle(true);
        toolbarHelper.setTopView(baseActivity.addCenterTitleView());
    }

    @Override
    protected void injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mineFragmentPresenter;
    }


    @Override
    public void onResume() {
        super.onResume();

        presenter.onResume(this);
    }


}
