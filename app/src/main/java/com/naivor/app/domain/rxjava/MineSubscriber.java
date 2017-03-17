package com.naivor.app.domain.rxjava;

import com.naivor.app.domain.presenter.BasePresenter;

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
    }

    @Override
    public void onCompleted() {
        presenter.loadComplete();
    }

    @Override
    public void onError(Throwable e) {
        presenter.loadErrorOccured(e);
    }

}
