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

package com.naivor.app.modules.partone;

import android.content.Context;

import com.naivor.app.common.base.BasePresenter;
import com.naivor.app.features.di.PerFragment;
import com.naivor.app.features.repo.OtherRepo;
import com.naivor.app.features.repo.deal.MineSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-18.
 */
@PerFragment
public class OnePresenter extends BasePresenter implements OneVPContact.OnePresenter {

    @Inject
    OtherRepo mRepository;

    private int maxPageSize = 8;

    @Inject
    public OnePresenter(Context context) {
        super(context);
    }

    @Override
    public OneVPContact.OneView getView() {
        return (OneVPContact.OneView) view;
    }


    /**
     * 请求数据
     */
    public void requestData(int index) {
        mRepository.optHomeData(index)
                .subscribe(new MineSubscriber<List<String>>(this) {
                    @Override
                    public void onNext(List<String> s) {
                        if (s != null) {
                            getView().setHasMore(s.size() >= maxPageSize);

                            if (getView().isLoadMore()) {
                                getView().getAdapter().addItems(s);
                            } else {
                                getView().getAdapter().setItems(s);
                            }
                        }
                    }
                });

    }

}
