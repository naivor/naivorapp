package com.naivor.app.common.rxJava;


import com.naivor.app.common.base.BasePresenter;

import rx.Subscriber;

/**
 * 自定义的订阅类
 * <p>
 * Created by tianlai on 17-3-16.
 */

public abstract class MineSubscriber<T> extends Subscriber<T> {

    private BasePresenter presenter;

    public MineSubscriber(BasePresenter presenter) {
        this.presenter = presenter;

        if (presenter != null) {
            presenter.addNetTask(this);
        }
    }

    @Override
    public void onCompleted() {

        if (presenter != null) {
            presenter.loadComplete();

            presenter.removeNetTask(this);
        }
    }

    @Override
    public void onError(Throwable e) {
        presenter.loadError(e);
    }

}
