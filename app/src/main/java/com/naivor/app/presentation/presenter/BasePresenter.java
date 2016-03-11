package com.naivor.app.presentation.presenter;

import android.os.Bundle;

import com.naivor.app.presentation.usecase.BaseUseCase;
import com.naivor.app.presentation.view.BaseUiView;

import icepick.Icepick;
import icepick.State;

/**
 * BasePresenter 是应用中所有Presenter的顶级抽象类，规定了Presenter的参数类型
 *
 * Presenter：MVP架构中的P。
 *
 * Created by tianlai on 16-3-3.
 */
public abstract class BasePresenter<M extends BaseUseCase, V extends BaseUiView> {

    @State protected String baseMessage;

    protected V mUiView;
    protected M mUseCase;

    public BasePresenter() {

    }

    /**
     * Presenter 的 oncreate（）生命周期，在Activity中的 oncreate（）中调用
     * 作用：恢复状态，初始化数据
     *
     * @param savedInstanceState
     */
    public void oncreate(Bundle savedInstanceState){
        Icepick.restoreInstanceState(this, savedInstanceState);
    }


    /**
     *Presenter 的 onSave（）生命周期，在Activity中的 onSaveInstance（）中调用
     * 作用： 保存数据
     *
     * @param state
     */
    public void onSave(Bundle state){
        Icepick.saveInstanceState(this, state);
    }

    /**
     * Presenter 的 onResume（）生命周期，在Activity中的 onResume（）中调用
     * 作用：绑定view，初始化view状态
     *
     * @param uiView
     */
    public void  onResume(V uiView){
        this.mUiView = uiView;
    }

    /**
     * Presenter 的 onPause（）生命周期，在Activity中的 onPause（）中调用
     * 作用：解绑View，销毁View
     *
     */
    public void  onPause(){
        mUiView=null;
    }

    /**
     * Presenter 的 onDestroy（）生命周期，在Activity中的 onDestroy（）中调用
     * 作用：销毁持有的对象
     */
    public void  onDestroy(){
        mUiView=null;
        mUseCase=null;

    }

    /**
     * 取消加载的方法
     */
    public abstract void cancleLoading();

    /**
     * 重新加载页面的方法
     */
    public abstract void retryLoading();

}
