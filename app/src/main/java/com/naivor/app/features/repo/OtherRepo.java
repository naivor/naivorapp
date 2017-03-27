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

package com.naivor.app.features.repo;

import android.content.Context;

import com.naivor.app.R;
import com.naivor.app.common.base.BaseRepository;
import com.naivor.app.common.rxJava.RxUtils;
import com.naivor.app.features.repo.apiService.LoginApiService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by naivor on 16-4-2.
 */
public class OtherRepo extends BaseRepository<LoginApiService> {
    @Inject
    public OtherRepo(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    @Override
    protected Class<LoginApiService> defServiceType() {
        return LoginApiService.class;
    }

    /**
     * 获取首页数据
     *
     * @return
     */
    public Observable<List<String>> optHomeData(int index) {
        return Observable.timer(3, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {

                                List<String> list = Arrays.asList(mContext.getResources().getStringArray(R.array.list_data));

                                for (String str : list) {
                                    subscriber.onNext(str);
                                }

                                subscriber.onCompleted();
                            }

                        });
                    }
                })
                .skip(index * pageSum)
                .take(pageSum)
                .compose(RxUtils.<String>transSchedule())
                .toList();
    }
}
