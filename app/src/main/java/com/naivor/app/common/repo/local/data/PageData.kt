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

package com.naivor.app.common.repo.local.data

/**
 * 分页数据
 */
open class PagedData<T>(
    var index: Int = 0,
    var pageSize: Int = 10,
    var pageTotal: Int? = null,
    var totalSize: Int? = null,
    var datas: List<T> = emptyList()
) {

    fun hasNext(): Boolean {
        if (pageTotal == null && totalSize == null) {
            throw IllegalStateException("no pageTotal defined or no totalSize defined")
        }

        if (pageSize <= 0) {
            throw IllegalArgumentException("pageSize must more than zero")
        }

        if (index < 0) {
            throw IllegalStateException("index can't less than zero")
        }

        val total = pageTotal ?: (totalSize?.div(pageSize))!!

        return index < total
    }

    fun nextIndex(): Int {
        return ++index
    }

    override fun toString(): String {
        return "PagedData(index=$index, pageSize=$pageSize, pageTotal=$pageTotal, totalSize=$totalSize, datas=$datas)"
    }
}