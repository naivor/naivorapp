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
import com.naivor.app.common.rxJava.RxBus;
import com.naivor.app.common.model.User;
import com.naivor.app.others.UserManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import icepick.Icepick;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * BasePresenter 是应用中所有Presenter的顶级抽象类，规定了Presenter的参数类型
 * <p>
 * Presenter：MVP架构中的P。
 * <p>
 * Created by tianlai on 16-3-3.
 */
public abstract class BasePresenter<V extends BaseUiView> {
    protected static final String TAG = "BasePresenter";

    protected V mUiView;

    protected Context context;

    protected CompositeSubscription subscriptions;

    public BasePresenter(Context context) {
        this.context = context;

        subscriptions = new CompositeSubscription();
    }

    /**
     * Presenter 的 oncreate（）生命周期，在Activity中的 oncreate（）中调用
     * 作用：恢复状态，初始化数据
     *
     * @param savedInstanceState
     */
    public void oncreate(Bundle savedInstanceState, V uiView) {
        Icepick.restoreInstanceState(this, savedInstanceState);
        this.mUiView = uiView;

    }


    /**
     * Presenter 的 onSave（）生命周期，在Activity中的 onSaveInstance（）中调用
     * 作用： 保存数据
     *
     * @param state
     */
    public void onSave(Bundle state) {
        Icepick.saveInstanceState(this, state);
    }


    /**
     * Presenter 的 onPause（）生命周期，在Activity中的 onPause（）中调用
     * 作用：解绑View，销毁View
     */
    public void onStop() {
        cancle();
    }

    /**
     * Presenter 的 onDestroy（）生命周期，在Activity中的 onDestroy（）中调用
     * 作用：销毁持有的对象
     */
    public void onDestroy() {
        mUiView = null;
        context = null;
        subscriptions = null;
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
        Subscription sub = RxBus.getDefault().toObservable(c)
                .subscribe(a, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadError(throwable);
                    }
                });

        subscriptions.add(sub);
    }

    /**
     * 取消加载的方法
     */
    public void cancle() {
        if (subscriptions != null) {
            subscriptions.unsubscribe();
        }
    }

    /**
     * 加载发生错误
     */
    public void loadError(Throwable e) {

        e.printStackTrace();

        if (e instanceof SocketTimeoutException) {
            mUiView.showError("超时，请稍后重试");
        } else if (e instanceof ConnectException) {
            mUiView.showError("连接失败，请稍检查您的网络");
        }

        dismissLoading();
    }

    public void loadComplete() {
        //TODO
    }

    /**
     * 显示加载对话框
     *
     * @param isShow
     */
    protected void showLoading(boolean isShow) {
        if (isShow && mUiView != null) {
            mUiView.showLoading();
        }
    }

    /**
     * 取消加载对话框
     */
    protected void dismissLoading() {
        if (mUiView != null) {
            mUiView.dismissLoading();
        }
    }


    /**
     * 将网络请求添加到任务列表中
     *
     * @param sub
     */
    public void addNetTask(Subscription sub) {
        if (subscriptions != null) {
            subscriptions.add(sub);
        }
    }

    /**
     * 从任务列表移除请求任务
     *
     * @param sub
     */
    public void removeNetTask(Subscription sub) {
        if (subscriptions != null) {
            subscriptions.remove(sub);
        }
    }


}
