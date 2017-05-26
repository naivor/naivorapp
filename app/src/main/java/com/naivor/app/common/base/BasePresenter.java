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

import com.naivor.app.common.bus.RxBus;
import com.naivor.app.features.exception.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.NonNull;
import timber.log.Timber;


/**
 * BasePresenter 是应用中所有Presenter的顶级抽象类，规定了Presenter的参数类型
 * <p>
 * Presenter：MVP架构中的P。
 * <p>
 * Created by tianlai on 16-3-3.
 */
public abstract class BasePresenter implements Presenter, Consumer<Throwable> {

    protected Context context;

    protected CompositeDisposable subscriptions;

    protected UiView view;

    public BasePresenter(Context context) {
        this.context = context;

        subscriptions = new CompositeDisposable();
    }

    @Override
    public void bindView(UiView view) {
        this.view = view;
    }

    /**
     * 获取View
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public abstract <T extends UiView> T getView();

    /**
     * Presenter 的 oncreate（）生命周期，在Activity中的 oncreate（）中调用
     * 作用：恢复状态，初始化数据
     *
     * @param savedInstanceState
     */
    @Override
    public void oncreate(Bundle savedInstanceState) {

    }

    /**
     * Presenter 的 onSave（）生命周期，在Activity中的 onSaveInstance（）中调用
     * 作用： 保存数据
     *
     * @param state
     */
    @Override
    public void onSave(Bundle state) {

    }


    /**
     * Presenter 的 onPause（）生命周期，在Activity中的 onPause（）中调用
     * 作用：解绑View，销毁View
     */
    @Override
    public void onStop() {
        cancle();
    }

    /**
     * Presenter 的 onDestroy（）生命周期，在Activity中的 onDestroy（）中调用
     * 作用：销毁持有的对象
     */
    @Override
    public void onDestroy() {
        view = null;
        context = null;
        subscriptions = null;
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

    public <E> void busEvent(Class<E> c, Consumer<E> a) {
        Disposable sub = RxBus.getDefault().toObservable(c)
                .subscribe(a, this);

        subscriptions.add(sub);
    }

    /**
     * 取消加载的方法
     */
    @Override
    public void cancle() {
        if (subscriptions != null) {
            subscriptions.dispose();
        }
    }

    @Override
    public void accept(@NonNull Throwable e) throws Exception {

        String msg = "未知错误";

        if (e instanceof SocketTimeoutException) {
            msg = "超时，请稍后重试";

        } else if (e instanceof ConnectException) {
            msg = "连接失败，请稍检查您的网络";
        } else if (e instanceof UnknownHostException) {
            msg = "请检查您的网络";
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;

            msg = "暂时没有找到你想要的，请换一个试试";

            Timber.e("ApiException:%s", exception.toString());
        } else {
            e.printStackTrace();
        }

        if (view != null) {
            view.showError(msg);
            Timber.e(msg);
        }


        dismissLoading();
    }


    /**
     * 加载完成
     */
    @Override
    public void loadComplete() {
        dismissLoading();
    }

    /**
     * 显示加载对话框
     *
     * @param isShow
     */
    protected void showLoading(boolean isShow) {
        if (isShow && view != null) {
            view.showLoading();
        }
    }

    /**
     * 取消加载对话框
     */
    protected void dismissLoading() {
        if (view != null) {
            view.dismissLoading();
        }
    }


    /**
     * 将网络请求添加到任务列表中
     *
     * @param sub
     */
    public void addNetTask(Disposable sub) {
        if (subscriptions != null) {
            subscriptions.add(sub);
        }
    }

    /**
     * 从任务列表移除请求任务
     *
     * @param sub
     */
    public void removeNetTask(Disposable sub) {
        if (subscriptions != null) {
            subscriptions.remove(sub);
        }
    }


}
