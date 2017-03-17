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

package com.naivor.app.domain.presenter;

import android.content.Context;
import android.os.Bundle;

import com.bugtags.library.Bugtags;
import com.naivor.app.data.model.User;
import com.naivor.app.data.model.enums.UserType;
import com.naivor.app.domain.repository.BaseRepository;
import com.naivor.app.presentation.view.BaseUiView;

import icepick.Icepick;
import icepick.State;
import rx.Subscriber;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

/**
 * BasePresenter 是应用中所有Presenter的顶级抽象类，规定了Presenter的参数类型
 * <p/>
 * Presenter：MVP架构中的P。
 * <p/>
 * Created by tianlai on 16-3-3.
 */
public abstract class BasePresenter<V extends BaseUiView,R extends BaseRepository> {

    @State
    protected String baseMessage;

    protected V mUiView;

    protected Context context;

    protected R mRepository;

    //定义一个总线
    protected static Subject<Object,Object> subject;

    public BasePresenter(final R mRepository) {
        this.mRepository=mRepository;

        //初始化总线
        if (subject==null){
            subject= BehaviorSubject.create(new Object());
        }

    }

    /**
     * Presenter 的 oncreate（）生命周期，在Activity中的 oncreate（）中调用
     * 作用：恢复状态，初始化数据
     *
     * @param savedInstanceState
     */
    public void oncreate(Bundle savedInstanceState, Context context) {
        Icepick.restoreInstanceState(this, savedInstanceState);

        this.context = context;
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
     * Presenter 的 onResume（）生命周期，在Activity中的 onResume（）中调用
     * 作用：绑定view，初始化view状态
     *
     * @param uiView
     */
    public void onResume(V uiView) {
        this.mUiView = uiView;
    }

    /**
     * Presenter 的 onPause（）生命周期，在Activity中的 onPause（）中调用
     * 作用：解绑View，销毁View
     */
    public void onPause() {
        mUiView = null;
    }

    /**
     * Presenter 的 onDestroy（）生命周期，在Activity中的 onDestroy（）中调用
     * 作用：销毁持有的对象
     */
    public void onDestroy() {
        mUiView = null;

    }

    /**
     * 取消加载的方法
     */
    public void cancleLoading(){
        mRepository.cancleRequest();
    }

    /**
     * 重新加载页面的方法
     */
    public  void retryLoading(){

    }

    /**
     * 获取用户类型
     *
     * @return
     */
    public UserType getUserType() {
        return mRepository.getUser().userType();
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public User getUser(){
        return mRepository.getUser();
    }

    /**
     * 加载完成
     */
    public void loadComplete(){
        if (mUiView!=null) {
            mUiView.loadingComplete();
        }
    }

    /**
     * 加载发生错误
     */
    public void loadErrorOccured(Throwable e){
        e.printStackTrace();

        if (mUiView!=null) {
            mUiView.loadingComplete();
            mUiView.showError();
        }
    }

}
