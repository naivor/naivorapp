package com.naivor.app.common.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.naivor.app.NaivorApp;
import com.naivor.app.common.bus.RxBus;
import com.naivor.app.features.di.component.ServiceComponent;

import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Consumer;

/**
 * BaseService Service，把一些公共的方法放到里面
 * <p>
 * Created by tianlai on 17-5-19.
 */

public abstract class BaseService extends Service implements UiView {

    protected Context context;

    protected NaivorApp application;

    private SerialDisposable sub;

    private Presenter presenter;

    @Override
    public void onCreate() {
        super.onCreate();

        sub = new SerialDisposable();

        context = getApplicationContext();
        application = (NaivorApp) getApplication();

        presenter = getPresenter();
        if (presenter != null) {
            presenter.bindView(this);
        }
    }

    /**
     * 进行注入
     *
     * @param component
     */
    protected abstract void injectService(ServiceComponent component);

    /**
     * 获取注入Activity的Presenter对象
     *
     * @return
     */
    @Override
    public abstract Presenter getPresenter();

    @Override
    public boolean onUnbind(Intent intent) {

        //取消正在进行的请求
        if (presenter != null) {
            presenter.onStop();
        }

        if (sub != null && !sub.isDisposed()) {
            sub.dispose();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        if (sub != null && !sub.isDisposed()) {
            sub.dispose();
        }
        sub = null;

        if (presenter != null) {
            presenter.onDestroy();
        }
        presenter = null;
        application = null;
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

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void dismissLoading() {

    }
}
