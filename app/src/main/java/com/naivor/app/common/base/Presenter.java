package com.naivor.app.common.base;

import android.os.Bundle;

public interface Presenter {


    /**
     * 将view绑定到Presenter
     *
     * @param view
     */
    void bindView(UiView view);


    /**
     * Presenter 的 oncreate（）生命周期，在Activity中的 oncreate（）中调用
     * 作用：恢复状态，初始化数据
     *
     * @param savedInstanceState
     */
    void oncreate(Bundle savedInstanceState);


    /**
     * Presenter 的 onSave（）生命周期，在Activity中的 onSaveInstance（）中调用
     * 作用： 保存数据
     *
     * @param state
     */
    void onSave(Bundle state);


    /**
     * Presenter 的 onPause（）生命周期，在Activity中的 onPause（）中调用
     * 作用：解绑View，销毁View
     */
    void onStop();


    /**
     * Presenter 的 onDestroy（）生命周期，在Activity中的 onDestroy（）中调用
     * 作用：销毁持有的对象
     */
    void onDestroy();


    /**
     * 取消加载的方法
     */
    void cancle();


    /**
     * 加载完成
     */
    void loadComplete();


}