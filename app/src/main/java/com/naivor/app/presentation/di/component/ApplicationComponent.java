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

package com.naivor.app.presentation.di.component;


import android.content.Context;
import android.view.LayoutInflater;

import com.naivor.app.AppPageManager;
import com.naivor.app.AppApplication;
import com.naivor.app.data.cache.spf.SpfManager;
import com.naivor.app.presentation.di.module.ApplicationModule;
import com.naivor.app.presentation.di.module.NetworkModule;
import com.naivor.app.presentation.ui.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ApplicationComponent 生命周期跟Application一样的组件。可注入到自定义的Application类中，@Singletion代表各个注入对象为单例。
 * <p>
 * Created by tianlai on 16-3-3.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    //要注入的类型
    void inject(BaseActivity baseActivity);

    void inject(AppApplication appApplication);

    //要暴露给其他依赖本组件的组件的依赖方法
    Context context();

    LayoutInflater layoutInflater();

    AppPageManager pageManager();

    SpfManager spfManager();

    Retrofit retrofit();

    OkHttpClient okHttpClient();

    //子Component
}
