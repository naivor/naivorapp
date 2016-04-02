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

package com.naivor.app.domain.repository;

import android.content.Context;

import com.naivor.app.data.remote.ApiService.DateApiService;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 *
 *
 * Created by naivor on 16-4-2.
 */
public class DateRepository extends BaseRepository<DateApiService> {
    @Inject
    public DateRepository(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    @Override
    protected Class<DateApiService> getServiceClass() {
        return DateApiService.class;
    }
}
