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

package com.naivor.app.features.di;

import android.content.Context;

import com.naivor.app.features.di.component.ActivityComponent;
import com.naivor.app.features.di.component.ApplicationComponent;
import com.naivor.app.features.di.component.DaggerActivityComponent;
import com.naivor.app.features.di.component.DaggerApplicationComponent;
import com.naivor.app.features.di.component.DaggerFragmentComponent;
import com.naivor.app.features.di.component.DaggerServiceComponent;
import com.naivor.app.features.di.component.FragmentComponent;
import com.naivor.app.features.di.component.ServiceComponent;
import com.naivor.app.features.di.module.ActivityModule;
import com.naivor.app.features.di.module.ApplicationModule;
import com.naivor.app.features.di.module.FragmentModule;
import com.naivor.app.features.di.module.NetworkModule;
import com.naivor.app.features.di.module.ServiceModule;

import lombok.NonNull;


/**
 * 依赖注入管理器
 *
 * Created by tianlai on 17-5-19.
 */

public final class InjectionManager {

    private static Context context;

    private static InjectionManager manager;

    private ApplicationComponent applicationComponent;

    private ActivityComponent activityComponent;

    private FragmentComponent fragmentComponent;

    private ServiceComponent serviceComponent;

    private InjectionManager(Context context) {
    }

    public static final void init(@NonNull Context mContext){
        context=mContext;
    }

    public static InjectionManager get(){

        if (context==null){
            throw new NullPointerException("context can't be null,InjectionManager may not inited");
        }

        if (manager==null){
            synchronized (InjectionManager.class){
                if (manager==null){
                    manager=new InjectionManager(context);
                }
            }
        }

        return manager;
    }

    /**
     * 初始化 applicationComponent
     */
    public void  initAppInjector(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .networkModule(new NetworkModule())
                .build();
    }

    /**
     * 获取 ApplicationComponent
     *
     * @return
     */
    public ApplicationComponent  getAppComponent(){
        if(applicationComponent==null){
            initAppInjector();
        }

        return applicationComponent;
    }

    /**
     * 初始化 ActivityComponent
     */
    public void  initActivityInjector(){
        if (applicationComponent==null){
            initAppInjector();
        }

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(context))
                .build();
    }

    /**
     * 获取 ActivityComponent
     *
     * @return
     */
    public ActivityComponent  getActivityComponent(){
        if (activityComponent==null){
            initActivityInjector();
        }

        return activityComponent;
    }

    /**
     * 初始化 FragmentComponent
     */
    public void  initFragmentInjector(){
        if (applicationComponent==null){
            initAppInjector();
        }

        fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .fragmentModule(new FragmentModule(context))
                .build();
    }

    /**
     * 获取 FragmentComponent
     *
     * @return
     */
    public FragmentComponent  getFragmentComponent(){
        if (fragmentComponent==null){
            initFragmentInjector();
        }

        return fragmentComponent;
    }
    /**
     * 初始化 ServiceComponent
     */
    public void  initServiceInjector(){
        if (serviceComponent==null){
            initAppInjector();
        }

        serviceComponent = DaggerServiceComponent.builder()
                .applicationComponent(applicationComponent)
                .serviceModule(new ServiceModule(context))
                .build();
    }

    /**
     * 获取 ServiceComponent
     *
     * @return
     */
    public ServiceComponent  getServiceComponent(){
        if (serviceComponent==null){
            initServiceInjector();
        }

        return serviceComponent;
    }
}
