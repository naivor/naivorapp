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

import com.naivor.app.common.bus.RxBus;
import com.naivor.app.features.di.InjectionManager;
import com.naivor.app.features.di.component.FragmentComponent;

import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Consumer;

/**
 * BaseFragment 是所有activity的基类，把一些公共的方法放到里面
 * <p/>
 * Created by tianlai on 16-3-3.
 */
public abstract class BaseFragment extends Fragment implements UiView {

    protected Context context;

    protected LayoutInflater inflater;

    protected BaseActivity baseActivity;

    private SerialDisposable sub;

    private Presenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
        inflater = LayoutInflater.from(context);
        baseActivity = (BaseActivity) getActivity();

        sub = new SerialDisposable();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectFragment(InjectionManager.get().getFragmentComponent());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化Presenter
        presenter = getPresenter();

        if (presenter != null) {
            presenter.bindView(this);
            presenter.oncreate(savedInstanceState);
        }

    }

    /**
     * 进行注入
     *
     * @param component
     */
    protected abstract void injectFragment(FragmentComponent component);

    /**
     * 获取注入Fragment的Presenter对象
     *
     * @return
     */
    @Override
    public abstract Presenter getPresenter();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (presenter != null) {
            presenter.onSave(outState);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (presenter != null) {
            presenter.onStop();
        }

        if (sub != null && !sub.isDisposed()) {
            sub.dispose();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (presenter != null) {
            presenter.onDestroy();
        }

        presenter = null;
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
    public <T> void busEvent(Class<T> c, Consumer<T> a) {
        sub.replace(RxBus.getDefault().toObservable(c)
                .subscribe(a, throwable -> throwable.printStackTrace()));

    }

    /**
     * 显示加载对话框
     */
    @Override
    public void showLoading() {

        if (baseActivity != null) {
            baseActivity.showLoading();
        }
    }

    /**
     * 取消加载对话框
     */
    @Override
    public void dismissLoading() {
        if (baseActivity != null) {
            baseActivity.dismissLoading();
        }
    }


    @Override
    public void showEmpty() {
        if (baseActivity != null) {
            baseActivity.showEmpty();
        }
    }


    @Override
    public void showError(String msg) {
        if (baseActivity != null) {
            baseActivity.showError(msg);
        }
    }

    /**
     * 获取view
     *
     * @param viewId
     * @return
     */
    public View find(View parent, int viewId) {
        if (parent != null) {
            return parent.findViewById(viewId);
        }
        return null;
    }

    /**
     * 获取页面的toolbar
     *
     * @return
     */
    public abstract Toolbar getToolbar();


}
