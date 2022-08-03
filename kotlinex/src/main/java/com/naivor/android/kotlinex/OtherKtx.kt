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

package com.naivor.android.kotlinex

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间格式化
 */
fun Long.toPassedTime(): String {
    //现在时间
    val today = Calendar.getInstance()

    //Long代表的时间
    val time = Calendar.getInstance()
    time.time = Date(this)

    return if (today.get(Calendar.YEAR) > time.get(Calendar.YEAR)) {
        formatTime()
    } else if (today.get(Calendar.MONTH) > time.get(Calendar.MONTH)) {
        "${today.get(Calendar.MONTH) - time.get(Calendar.MONTH)}月前"
    } else if (today.get(Calendar.DAY_OF_MONTH) > time.get(Calendar.DAY_OF_MONTH)) {
        "${today.get(Calendar.DAY_OF_MONTH) - time.get(Calendar.DAY_OF_MONTH)}天前"
    } else if (today.get(Calendar.HOUR) > time.get(Calendar.HOUR)) {
        "${today.get(Calendar.HOUR) - time.get(Calendar.HOUR)}小时前"
    } else if (today.get(Calendar.MINUTE) > time.get(Calendar.MINUTE)) {
        "${today.get(Calendar.MINUTE) - time.get(Calendar.MINUTE)}分钟前"
    } else {
        "刚刚"
    }
}

/**
 * 时间格式化
 */
fun Long.formatTime(format: String = "yyyy-MM-dd HH:mm:ss"): String =SimpleDateFormat(format).format(Date(this))


/**
 * json 转 RequestBody
 */
fun String.jsonToBody(): RequestBody {
    return this.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
}