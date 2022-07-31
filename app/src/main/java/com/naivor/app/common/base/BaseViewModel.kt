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

package com.naivor.app.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naivor.app.embedder.logger.Logger

abstract class BaseViewModel : ViewModel() {
    companion object {

        protected var bus: MutableLiveData<Any?> = MutableLiveData()
    }

    protected var hasInited = false

    fun dataToBus(value: Any?) {
        Logger.d("dataToBus data:$value")
        bus.postValue(value)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> dataFromBus(): T? {
        val value: Any? = bus.value
        return try {
            value as? T
        } catch (e: Exception) {
            Logger.w("dataFromBus didn't succeed,data:${value.toString()} exception:${e.stackTraceToString()}")
            null
        }
    }

    /**
     * 此方法用来控制页面初始化，如果需要从下一个Fragment返回刷新页面，
     * 需在onViewCreated再次调用initPageDatas，
     * 用hasInited来避免初次进入页面时多次加载数据
     */
    abstract fun initPage()
}