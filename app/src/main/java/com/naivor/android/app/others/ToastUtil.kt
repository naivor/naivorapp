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

package com.naivor.android.app.others

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast

/**
 * Toast
 */
object ToastUtil {
    lateinit var app: Application

    var time: Long? = null
    var toast: Toast? = null

    fun init(application: Application){
        this.app=application
    }

    private fun shouldShow(newToast: Toast): Boolean {
        if (toast == newToast && time != null) {  //Toast跟上一个相同，判断是否超过间隔时间
            val outTime = when (toast?.duration ?: 0) {
                Toast.LENGTH_SHORT -> 2000
                Toast.LENGTH_LONG -> 3500
                else -> 1000
            }

            val now = System.currentTimeMillis()
            val between = now - time!!

            time = now

            return between >= outTime
        }

        //Toast跟上一个不同，直接显示
        toast = newToast
        return true
    }

    fun show(content: String,
             duration: Int = Toast.LENGTH_SHORT,
             gravity: Int = Gravity.BOTTOM){

        val toast = Toast.makeText(app, content, duration)
        if (gravity != Gravity.NO_GRAVITY) {
            toast.setGravity(gravity, 0, 0)
        }

        //是否超时
        if (shouldShow(toast)) {
            val looper = Looper.getMainLooper()
            if (looper !== Looper.myLooper()) { //非主线程
                Handler(looper).post {
                    toast.show()
                }
            } else {  //主线程
                toast.show()
            }
        }

    }
}

/**
 * 显示toast
 */
fun showToast(
    content: String,
    duration: Int = Toast.LENGTH_SHORT,
    gravity: Int = Gravity.BOTTOM
) {
    ToastUtil.show(content,duration,gravity)
}