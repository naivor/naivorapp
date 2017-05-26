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
import com.naivor.app.features.repo.apiService.LoginApiService;
import com.naivor.app.features.repo.deal.FlowableUtils;
import com.naivor.app.features.repo.responce.LoginData;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Retrofit;


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
    public Flowable<LoginData> login(@NonNull String mobile, @NonNull String psw) {

        return getService().login(mobile, psw)
                .compose(FlowableUtils.transDataAndSchedule());
    }
}
