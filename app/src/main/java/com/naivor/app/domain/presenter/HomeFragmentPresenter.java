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

package com.naivor.app.domain.presenter;

import android.content.Context;
import android.os.Bundle;

import com.naivor.app.R;
import com.naivor.app.data.remote.ApiResponce.HomeData;
import com.naivor.app.domain.repository.HomeRepository;
import com.naivor.app.domain.rxjava.MineSubscriber;
import com.naivor.app.extras.utils.LogUtil;
import com.naivor.app.presentation.di.PerFragment;
import com.naivor.app.presentation.ui.helper.LoadMorePresenterImpl;
import com.naivor.app.presentation.view.HomeFragmentView;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by tianlai on 16-3-18.
 */
@PerFragment
public class HomeFragmentPresenter extends BasePresenter<HomeFragmentView, HomeRepository> implements LoadMorePresenterImpl {

    private int maxPageSize;

    private int index;

    private boolean isCanLoadMore;

    private boolean isShowLoading;

    @Inject
    public HomeFragmentPresenter(HomeRepository mRepository) {
        super(mRepository);

        maxPageSize = 18;
        index = 0;
        isCanLoadMore = true;
        isShowLoading = true;
    }

    /**
     */
    public void requestData() {
        mRepository.optHomeData()
                .subscribe(new MineSubscriber<List<String>>(this) {
                    @Override
                    public void onNext(List<String> s) {
                        if (s != null) {
                            isCanLoadMore = s.size() < maxPageSize;

                            if (index == 0) {
                                mUiView.getListAdapter().setItems(s);
                            } else {
                                mUiView.getListAdapter().addItems(s);
                            }
                        }
                    }
                });

    }

    @Override
    public void refreshPageDate() {
        requestData();
    }

    @Override
    public void resetToOriginAndLoad() {
        index = 0;
        isCanLoadMore = true;

        requestData();
    }

    @Override
    public void loadNextPageDate() {
        if (isCanLoadMore) {
            index++;

           requestData();
        }
    }

    @Override
    public void loadComplete() {
        super.loadComplete();
    }

    @Override
    public boolean isLoadMore() {
        if (index != 0) {
            return true;
        }

        return false;
    }
}
