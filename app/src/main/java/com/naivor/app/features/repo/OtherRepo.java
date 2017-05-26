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
import com.naivor.app.features.repo.apiService.LoginApiService;
import com.naivor.app.features.repo.deal.ObservableUtils;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Retrofit;

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
    public Flowable<List<String>> optHomeData(int index) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            List<String> list1 = Arrays.asList(mContext.getResources().getStringArray(R.array.list_data));

            for (String str:list1) {
                emitter.onNext(str);
            }

        })
                .skip(index * pageSum)
                .take(pageSum)
                .compose(ObservableUtils.transSchedule())
                .toList()
                .toFlowable();
    }

}
