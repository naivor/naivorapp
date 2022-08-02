/*
 * Copyright (c) 2022-2022. Naivor. All rights reserved.
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

package com.naivor.android.app.common.repo.remote

import com.google.gson.GsonBuilder
import com.naivor.android.app.common.repo.DataSource
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * 远程数据源的顶层封装
 */
open class RemoteDataSource<T> : DataSource(){
    @Inject
    protected lateinit var retrofit: Retrofit

    protected val gson = GsonBuilder().setLenient().create()

    fun apis(): T {
        val type = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]

        @Suppress("UNCHECKED_CAST")
        val realApis = retrofit.create(type as Class<T>)

        return realApis
    }
}