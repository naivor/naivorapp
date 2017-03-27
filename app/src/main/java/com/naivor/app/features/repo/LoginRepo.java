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
import android.support.annotation.NonNull;

import com.naivor.app.common.base.BaseRepository;
import com.naivor.app.common.rxJava.RxUtils;
import com.naivor.app.features.repo.apiService.LoginApiService;
import com.naivor.app.features.repo.responce.DataResult;
import com.naivor.app.features.repo.responce.LoginData;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * LoginRepo 登录的数据仓库类
 * <p/>
 * Created by tianlai on 16-3-16.
 */
public class LoginRepo extends BaseRepository<LoginApiService> {

    @Inject
    public LoginRepo(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    @Override
    protected Class<LoginApiService> defServiceType() {
        return LoginApiService.class;
    }

    /**
     * 登录请求
     *
     * @param mobile
     * @param psw
     * @return
     */
    public rx.Observable<LoginData> login(@NonNull String mobile, @NonNull String psw) {

        return getService().login(mobile, psw)
                .compose(new Observable.Transformer<DataResult<LoginData>, LoginData>() {
                    @Override
                    public Observable<LoginData> call(Observable<DataResult<LoginData>> result) {
                        return result.flatMap(new Func1<DataResult<LoginData>, Observable<LoginData>>() {
                            @Override
                            public Observable<LoginData> call(DataResult<LoginData> loginDataDataResult) {
                                return Observable.create(new Observable.OnSubscribe<LoginData>() {
                                    @Override
                                    public void call(Subscriber<? super LoginData> subscriber) {
                                        LoginData loginData = new LoginData();
                                        subscriber.onNext(loginData);
                                        subscriber.onCompleted();
                                    }
                                });
                            }
                        });
                    }
                })
                .compose(RxUtils.<LoginData>transSchedule());
    }
}
