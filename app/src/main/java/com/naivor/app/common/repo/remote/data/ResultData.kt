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

package com.naivor.app.common.repo.remote.data

/**
 * 接收后端的具体数据必须实现此接口
 */
interface ResultData {

    fun wrap(): Any   //使用此方法将后端传递过来的数据转换成我们本地需要的数据

    fun safe(str: String?) = str ?: ""

    fun safe(num: Number?) = num ?: 0

    fun safe(obj: Any?) = when (obj) {
        is List<*>? -> obj ?: emptyList<Any>()
        is Set<*>? -> obj ?: emptySet<Any>()
        is Map<*, *>? -> obj ?: emptyMap<Any, Any>()
        else -> obj
    }
}