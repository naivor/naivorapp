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
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.naivor.app.NaivorApp;
import com.naivor.app.UIController;
import com.naivor.app.common.bus.RxBus;
import com.naivor.app.features.di.InjectionManager;
import com.naivor.app.features.di.component.ActivityComponent;

import javax.inject.Inject;

import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Consumer;


/**
 * BaseActivity 是所有activity的基类，把一些公共的方法放到里面
 * <p/>
 * Created by tianlai on 16-3-3.
 */
public abstract class BaseActivity extends AppCompatActivity implements UiView, DialogInterface.OnCancelListener {

    protected Context context;

    protected LayoutInflater inflater;

    protected NaivorApp application;

    @Inject
    protected UIController controller;

    private SerialDisposable sub;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sub = new SerialDisposable();

        //初始化
        application = (NaivorApp) getApplication();
        context = getApplicationContext();
        inflater = LayoutInflater.from(context);

        // 使用依赖注入
        injectActivity(InjectionManager.get().getActivityComponent());

        // 将当前activity加入ActivityManager中
        controller.addActivity(this);

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
    protected abstract void injectActivity(ActivityComponent component);


    /**
     * 获取注入Activity的Presenter对象
     *
     * @return
     */
    @Override
    public abstract Presenter getPresenter();

    /**
     * 把布局变成View
     *
     * @param layoutId
     * @return
     */
    protected View inflateView(int layoutId) {
        return inflater.inflate(layoutId, null);
    }


    /**
     * 显示加载对话框
     */
    @Override
    public void showLoading() {

    }


    /**
     * 取消加载对话框
     */
    @Override
    public void dismissLoading() {

    }

    /**
     * 对于列表页面,数据条数为0
     */
    @Override
    public void showEmpty() {

    }


    /**
     * 数据加载出错
     */
    @Override
    public void showError(String msg) {

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        if (presenter != null) {
            presenter.onSave(outState);
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

        if (presenter != null) {
            presenter.cancle();
        }
    }

    @Override
    protected void onStop() {

        //取消正在进行的请求

        if (presenter != null) {
            presenter.onStop();
        }

        if (sub != null && !sub.isDisposed()) {
            sub.dispose();
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        controller.removeActivity(this);

        if (presenter != null) {
            presenter.onDestroy();
        }

        presenter = null;
    }


    /**
     * 设置应用的字体不随系统设置的更改而更改
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
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
                .subscribe(a, Throwable::printStackTrace));
    }
}