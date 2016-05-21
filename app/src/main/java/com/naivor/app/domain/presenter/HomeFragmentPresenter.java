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
import com.naivor.app.data.remote.ApiResponce.HomeResponce;
import com.naivor.app.data.remote.CheckResponce;
import com.naivor.app.domain.repository.HomeRepository;
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

    @Inject
    public HomeFragmentPresenter(HomeRepository mRepository) {
        super(mRepository);
    }

    private int maxPageSize;

    private int index;

    private boolean isCanLoadMore;

    private boolean isShowLoading;

    @Override
    protected void onResponce(Object o) {
        LogUtil.i("提示", "数据返回");

        if (o instanceof HomeResponce) {
            HomeResponce homeResponce = (HomeResponce) o;
            if (CheckResponce.check(context, homeResponce)) {
                List<String> datas = homeResponce.getList();

                if (datas.size() < maxPageSize || homeResponce.getPageSize()<maxPageSize) {
                    isCanLoadMore = false;
                }

                if (index == 0) {
                    mUiView.getListAdapter().setItems(datas);
                } else {
                    mUiView.getListAdapter().addItems(datas);
                }
            }
        }

    }

    @Override
    public void oncreate(Bundle savedInstanceState, Context context) {
        super.oncreate(savedInstanceState, context);

        maxPageSize = 18;
        index = 0;
        isCanLoadMore = true;
        isShowLoading = true;

        requestData(R.array.list_data, maxPageSize);
    }

    private void requestData(final int dataId, final int maxPageSize) {
        observable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                LogUtil.i("提示", "数据装载");

                HomeResponce responce = new HomeResponce();
                responce.setList(Arrays.asList(context.getResources().getStringArray(dataId)));
                responce.setRespCode(1000);
                responce.setPageSize(maxPageSize);

                mRepository.startRequest();

                subscriber.onNext(responce);
                subscriber.onCompleted();
            }

        }).subscribeOn(Schedulers.io());


    }

    @Override
    public void onResume(HomeFragmentView uiView) {
        super.onResume(uiView);

        dealResponce();
    }

    private void dealResponce() {

        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                if (isShowLoading)
                    mUiView.showLoading();

            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .delay(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        LogUtil.i("提示", "数据处理");
    }

    @Override
    public void refreshPageDate() {
       new Timer().schedule(new TimerTask() {
           @Override
           public void run() {
              mUiView.loadingComplete();
           }
       },1500);
    }

    @Override
    public void resetToOriginAndLoad() {
        index = 0;
        isCanLoadMore = true;

        requestData(R.array.list_data, maxPageSize);
        LogUtil.i("提示", "刷新页面");
        dealResponce();
    }

    @Override
    public void loadNextPageDate() {
        if (isCanLoadMore) {
            index++;

            if (index == 1) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mUiView.loadingComplete();

                        HomeResponce responce = new HomeResponce();
                        responce.setList(Arrays.asList(context.getResources().getStringArray(R.array.list_more)));
                        responce.setRespCode(1000);
                        responce.setPageSize(maxPageSize);

                        onResponce(responce);
                    }
                },1500);
            }

            LogUtil.i("提示", "加载下一页数据");
        }
    }

    @Override
    public boolean isLoadMore() {
        if (index != 0) {
            return true;
        }

        return false;
    }
}
