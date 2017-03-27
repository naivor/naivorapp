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
import com.naivor.app.common.rxJava.MineSubscriber;
import com.naivor.app.features.di.PerFragment;
import com.naivor.app.features.repo.OtherRepo;
import com.naivor.app.others.helper.LoadMoreHelper;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tianlai on 16-3-18.
 */
@PerFragment
public class PartOnePresenter extends BasePresenter<PartOneView> implements LoadMoreHelper.LoadMorePresenter {

    private int maxPageSize;

    private int index;

    private boolean isCanLoadMore;

    private boolean isShowLoading;

    @Inject
    OtherRepo mRepository;

    @Inject
    public PartOnePresenter(Context context) {
        super(context);
    }


    /**
     */
    public void requestData() {
        mRepository.optHomeData(index)
                .subscribe(new MineSubscriber<List<String>>(this) {
                    @Override
                    public void onNext(List<String> s) {
                        if (s != null) {
                            isCanLoadMore = s.size() < maxPageSize;

                            if (isLoadMore()) {
                                mUiView.getListAdapter().addItems(s);
                            } else {
                                mUiView.getListAdapter().setItems(s);
                            }
                        }
                    }
                });

    }

    @Override
    public void refreshPage() {
        requestData();
    }

    @Override
    public void resetAndLoad() {
        index = 0;
        isCanLoadMore = true;

        requestData();
    }

    @Override
    public void loadNextPage() {
        if (isCanLoadMore) {
            index++;

            requestData();
        }
    }

    @Override
    public void loadComplete() {
        dismissLoading();
    }

    public boolean isLoadMore() {
        return index > 0 ;
    }

}
