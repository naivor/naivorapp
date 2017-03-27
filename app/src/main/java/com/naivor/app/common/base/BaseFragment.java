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

package com.naivor.app.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.naivor.app.common.model.User;
import com.naivor.app.common.rxJava.RxBus;
import com.naivor.app.features.di.component.DaggerFragmentComponent;
import com.naivor.app.features.di.component.FragmentComponent;
import com.naivor.app.features.di.module.FragmentModule;
import com.naivor.app.others.UserManager;

import rx.Subscription;
import rx.functions.Action1;

public abstract class BaseFragment extends Fragment implements BaseUiView {
    protected  final String TAG = this.getClass().getSimpleName();

    protected FragmentComponent fragmentComponent;

    protected Context context;

    protected LayoutInflater inflater;

    protected BaseActivity baseActivity;

    protected BasePresenter presenter;

    private Subscription sub;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
        inflater = LayoutInflater.from(context);
        baseActivity = (BaseActivity) getActivity();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initInjector();

        injectFragment(fragmentComponent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化Presenter
        presenter = getPresenter();
        if (presenter == null) {
            throw new NullPointerException("presenter 不能为 Null");
        } else {
            presenter.oncreate(savedInstanceState, this);
        }

    }

    private void initInjector() {
        fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(baseActivity.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    private FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected abstract void injectFragment(FragmentComponent fragmentComponent);

    public abstract BasePresenter getPresenter();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        presenter.onSave(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        presenter.onStop();

        if (sub.isUnsubscribed()) {
            sub.unsubscribe();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();

        presenter = null;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public User getUser() {
        return UserManager.get().getUser();
    }

    /**
     * 更新当前用户
     *
     * @return
     */
    public void updateUser(User newUser) {
        UserManager.get().update(newUser);
    }


    /**
     * 将数据添加到总线中
     *
     * @param object
     */
    public void postBus(Object object) {
        RxBus.getDefault().post(object);
    }

    /**
     * 订阅事件
     *
     * @param c
     */
    public <T> void busEvent(Class<T> c, Action1<T> a) {
        sub = RxBus.getDefault().toObservable(c)
                .subscribe(a, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

    }

    /**
     * 显示加载对话框
     */
    @Override
    public void showLoading() {
        baseActivity.showLoading();
    }

    /**
     * 取消加载对话框
     */
    @Override
    public void dismissLoading() {
        baseActivity.dismissLoading();
    }

    @Override
    public void showLoadingDialogNow(boolean timeout) {
        baseActivity.showLoadingDialogNow(timeout);
    }

    @Override
    public void showEmpty() {
        baseActivity.showEmpty();
    }


    @Override
    public void showError(String msg) {
        baseActivity.showError(msg);
    }

    /**
     * 获取view
     *
     * @param viewId
     * @return
     */
    public View find(View parent, int viewId) {
        return parent.findViewById(viewId);
    }


    /**
     * 获取页面的toolbar
     *
     * @return
     */
    public abstract Toolbar getToolbar();


}
