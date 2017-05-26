package com.naivor.app.features.repo.deal;


import com.naivor.app.common.base.BasePresenter;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * 自定义的订阅类
 * <p>
 * Created by tianlai on 17-3-16.
 */

public abstract class MineSubscriber<T> extends ResourceSubscriber<T> {

    private BasePresenter presenter;

    public MineSubscriber(BasePresenter presenter) {
        this.presenter = presenter;

        if (presenter != null) {
            presenter.addNetTask(this);
        }
    }


    @Override
    public void onComplete() {
        if (presenter != null) {
            presenter.loadComplete();

            presenter.removeNetTask(this);
        }
    }


    @Override
    public void onError(Throwable e) {
        if (presenter != null) {
            try {
                presenter.accept(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
