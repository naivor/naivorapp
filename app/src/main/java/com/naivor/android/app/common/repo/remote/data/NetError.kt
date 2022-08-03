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

package com.naivor.android.app.common.repo.remote.data

import com.naivor.android.app.embedder.logger.Logger
import com.naivor.android.app.others.showToast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

open class NetError(
    private val status: NetCode,
    message: String?
) :
    Throwable(message) {
    companion object {
        fun unknown(code: Int, message: String?, description: String?=null): NetError {
            val status = NetCode.UNKNOWN
            status.code = code
            status.message = message
            status.description = description
            return NetError(status, message)
        }
    }

    override fun toString(): String {
        return "NetError(status=$status,message=$message)"
    }
}

enum class NetCode(var code: Int, var message: String?, var description: String? = "") {
    OK(0, "请求成功", "网络请求成功，返回数据无异常"),
    NOT_LOGIN(100, "用户未登录", "未登录，请立即登录"),
    ACCOUNT_INFO_ERROR(101, "用户名或密码错误", "用户名或密码错误"),
    UNKNOWN(Int.MIN_VALUE, "未知状态", "未知状态")
}

/**
 * Flow默认异常捕获
 */
fun <T> Flow<T>.catch(showMessage: Boolean = true, custom: ((Throwable) -> Unit)? = null): Flow<T> {
    return this.catch { cause: Throwable ->

        custom?.invoke(cause)

        if (showMessage) {
            if (cause is NetError) {
                showToast("${cause.message}")
            } else {
                showToast("抱歉，出了点状况 ！")
            }
        }


        Logger.e("发生异常： ${cause.stackTraceToString()}")
    }
}