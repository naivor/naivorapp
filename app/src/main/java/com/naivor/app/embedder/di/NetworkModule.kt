/*
 * Copyright (c) 2022. Naivor. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.embedder.di

import com.google.gson.GsonBuilder
import com.naivor.app.BuildConfig
import com.naivor.app.common.repo.remote.util.CommonParamInterceptor
import com.naivor.app.common.repo.remote.util.FlowCallAdapterFactory
import com.naivor.app.common.repo.remote.util.GsonConverterFactory
import com.naivor.app.common.repo.remote.util.HttpLoggingInterceptor
import com.naivor.app.others.Constants.API_URL_DEBUG
import com.naivor.app.others.Constants.API_URL_RELEASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(logger: Interceptor, params: CommonParamInterceptor): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(params)
            .addInterceptor(logger)
            .build()

        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
       val level = if (BuildConfig.DEBUG)
           HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE

        return HttpLoggingInterceptor(level)
    }

    /**
     * 获取baseUrl，自动判断用测试接口还是正式接口
     *
     * @return
     */
    private fun getBaseUrl(): String {
        return if (BuildConfig.DEBUG) {
            //测试接口
            API_URL_DEBUG
        } else {
            //正式接口
            API_URL_RELEASE
        }

    }
}