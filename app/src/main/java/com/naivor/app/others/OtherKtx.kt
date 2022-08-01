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

package com.naivor.app.others

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import com.naivor.app.common.repo.remote.data.NetError
import com.naivor.app.embedder.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

/**
 * Toast过滤
 */
object LastToast {

    var time: Long? = null
    var toast: Toast? = null

    fun shouldShow(newToast: Toast): Boolean {
        if (toast == newToast && time != null) {
            val duration: Int = toast?.duration ?: 0
            val outTime = when (duration) {
                Toast.LENGTH_SHORT -> 2000
                Toast.LENGTH_LONG -> 3500
                else -> 1000
            }

            val now = System.currentTimeMillis()
            val between = now - time!!
            time = now
            toast = newToast
            return between >= outTime
        }

        return true
    }
}

/**
 * 显示toast
 */
fun showToast(
    content: String,
    context: Context = PageWatcher.context(),
    duration: Int = Toast.LENGTH_SHORT,
    gravity: Int = Gravity.BOTTOM
) {
    val toast = Toast.makeText(context, content, duration)
    if (gravity != Gravity.NO_GRAVITY) {
        toast.setGravity(gravity, 0, 0)
    }

    //是否超时
    if (LastToast.shouldShow(toast)) {
        val looper = Looper.getMainLooper()
        if (looper !== Looper.myLooper()) { //非主线程
            Handler(looper).post {
                toast.show()
            }
        } else {
            toast.show()
        }
    }
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