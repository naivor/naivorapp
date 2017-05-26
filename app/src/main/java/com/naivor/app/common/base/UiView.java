package com.naivor.app.common.base;

import lombok.NonNull;

/**
 * BaseUiView 是应用中所有UiView的顶级抽象类，适合抽取UiView的公共方法和属性
 * <p>
 * UiView：MVP架构中的V。
 * <p>
 * Created by tianlai on 16-3-3.
 */
public interface UiView {

    /**
     * 获取Presenter
     *
     * @return
     */
    @NonNull
    <T extends Presenter> T getPresenter();

    /**
     * showLoading 方法主要用于页面请求数据时显示加载状态
     */
    void showLoading();


    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    void showEmpty();

    /**
     * showError 方法用于请求数据出错
     */
    void showError(String msg);

    /**
     * showError 方法用于请求数据完成
     */
    void dismissLoading();

}