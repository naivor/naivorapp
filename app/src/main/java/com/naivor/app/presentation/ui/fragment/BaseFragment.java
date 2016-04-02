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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.naivor.app.presentation.di.component.FragmentComponent;
import com.naivor.app.presentation.di.module.FragmentModule;
import com.naivor.app.presentation.presenter.BasePresenter;
import com.naivor.app.presentation.ui.activity.BaseActivity;
import com.naivor.app.presentation.ui.helper.ToolbarHelper;
import com.naivor.app.presentation.view.BaseUiView;

import javax.inject.Inject;

public abstract class BaseFragment extends Fragment implements BaseUiView {

    protected FragmentComponent fragmentComponent;

    protected ToolbarHelper toolbarHelper;

    @Inject
    protected Context context;

    @Inject
    protected LayoutInflater inflater;

    protected BaseActivity baseActivity;

    protected BasePresenter presenter;

    protected boolean isCanShowLoading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        baseActivity= (BaseActivity) getActivity();

        toolbarHelper=new ToolbarHelper();
        
        initToolbarHelper();

        initInjector();

        injectFragment(fragmentComponent);
    }

    /**
     * 初始化ToolbarHelper
     */
    protected abstract void initToolbarHelper();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化Presenter
        presenter = getPresenter();
        if (presenter == null) {
            throw new NullPointerException("presenter 不能为 Null");
        } else {
            presenter.oncreate(savedInstanceState, context);
        }

    }

    private void initInjector() {
        fragmentComponent=baseActivity.getActivityComponent().plus(getFragmentModule());
    }

    private FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected abstract void injectFragment(FragmentComponent fragmentComponent);

    public abstract BasePresenter getPresenter() ;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        presenter.onSave(outState);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
        isCanShowLoading=false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();

        presenter=null;
    }

    @Override
    public void showLoading() {
        if (isCanShowLoading){
            baseActivity.showLoading();
        }
    }

    @Override
    public void showEmpty() {
        baseActivity.showEmpty();
    }

    @Override
    public void showError() {
        baseActivity.showError();
    }

    @Override
    public void loadingComplete() {
        baseActivity.loadingComplete();
    }

    public ToolbarHelper getToolbarHelper() {
        return toolbarHelper;
    }

    public void setToolbarHelper(ToolbarHelper toolbarHelper) {
        this.toolbarHelper = toolbarHelper;
    }

    /**
     * 获取view
     *
     * @param viewId
     * @return
     */
    public View find(View parent,int viewId) {
        return parent.findViewById(viewId);
    }

    public boolean isCanShowLoading() {
        return isCanShowLoading;
    }

    public void setIsCanShowLoading(boolean isCanShowLoading) {
        this.isCanShowLoading = isCanShowLoading;
    }
}
