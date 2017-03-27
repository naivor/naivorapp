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

package com.naivor.app.features.di.module;

import android.content.Context;

import com.naivor.app.BuildConfig;
import com.naivor.app.R;
import com.naivor.app.features.repo.interceptor.ParamsInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * NetworkModule 网络请求模块，提供OkHttpClient，Retrofit
 * <p>
 * Created by tianlai on 16-3-7.
 */
@Module
public class NetworkModule {
    private static final int TIMEOUT = 10 * 1000;

    @Singleton
    @Provides
    OkHttpClient privodeOkHttpClient(ParamsInterceptor paramsInterceptor) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(paramsInterceptor);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(logging);  //添加 loggingInterceptor
        }

        return builder.build();

    }

    @Singleton
    @Provides
    Retrofit privodeRetrofit(Context context, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(getBaseUrl(context))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
    }

    /**
     * 设置网络请求超时
     *
     * @param clientBuilder
     * @param timeOut
     * @return
     */
    private OkHttpClient okTimeOut(OkHttpClient.Builder clientBuilder, int timeOut) {
        return clientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS).readTimeout(timeOut,
                TimeUnit.MILLISECONDS).build();
    }

    /**
     * 获取baseUrl，自动判断用测试接口还是正式接口
     *
     * @return
     */
    private String getBaseUrl(Context context) {
        int apiResId = 0;

        if (BuildConfig.DEBUG) {
            //测试接口
            apiResId = R.string.api_url_test;
        } else {
            //正式接口
            apiResId = R.string.api_url_release;
        }

//        return context.getString(apiResId) + AppUtil.getAppVersionName(context) + "/";
        return context.getString(apiResId);
    }
}
