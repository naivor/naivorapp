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

package com.naivor.app.common.base;


import android.content.Context;

import retrofit2.Retrofit;

/**
 * BaseRepository app的数据仓库类，所有需要的数据都通过它获得
 * <p/>
 * Created by tianlai on 16-3-3.
 */

public abstract class BaseRepository<T> {

    protected Retrofit retrofit;

    protected Context mContext;//Context对象，用来判断网络连接

    protected T apiServer;

    //对于列表,每页显示的内容条数
    public static final int pageSum = 8;

    public BaseRepository(Context context, Retrofit retrofit) {
        this.mContext = context;
        this.retrofit = retrofit;
    }


    /**
     * 获取接口对象
     */
    protected  T getService() {
        if (apiServer==null){
           final Class<T> c=defServiceType();

            if (c==null){
                throw new NullPointerException("ApiServer 的类型不能为 null");
            }

            apiServer=retrofit.create(c);
        }

        return apiServer;
    }

    /**
     * 定义接口类型
     *
     * @return
     */
    protected abstract Class<T> defServiceType();



}
