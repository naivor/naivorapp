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
