/*
 * Copyright (c) 2016-2022. Naivor. All rights reserved.
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

import android.os.Build
import com.naivor.android.app.BuildConfig
import com.naivor.android.app.common.base.KotlinTask
import com.naivor.android.app.common.utils.DateUtil.currentTime
import com.naivor.android.app.common.utils.SDCardUtil.checkSDCardAvailable
import com.naivor.android.app.common.utils.SDCardUtil.saveFileToSDCard
import com.naivor.android.app.embedder.logger.Logger
import com.naivor.android.app.embedder.logger.Logger.TAG_CRASH
import com.naivor.android.app.others.Constants.CRASH_PATH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier
import java.util.*

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author Naivor
 */
object CrashHandler : Thread.UncaughtExceptionHandler {
    private val TAG = TAG_CRASH

    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    // 用来存储设备信息和异常信息
    private var infos: MutableMap<String, String>? = null

    //保存崩溃信息的目录
    private var path: String = CRASH_PATH

    /**
     * 初始化CrashHandler
     */
    fun init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()

        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
        infos = HashMap()
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        Logger.w("很抱歉,出了点意外，应用崩溃了", TAG)

        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable): Boolean {
        CoroutineScope(KotlinTask.compute).launch {
            // 收集设备参数信息
            collectDeviceInfo()
            // 保存日志文件
            saveCrashInfo2File(ex)
        }

        return true
    }

    /**
     * 收集设备参数信息
     */
    private fun collectDeviceInfo() {
        infos!!.clear()

        infos!!["versionName"] = "${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"
        infos!!["versionCode"] = "${BuildConfig.VERSION_CODE}"

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true

                if (Modifier.isStatic(field.modifiers)) {
                    val fieldString = field.get(Build::class.java)?.toString() ?: ""
                    infos!![field.name] = fieldString
                }
            } catch (e: Exception) {
                Logger.e("an error occured when collect crash info:${e.stackTraceToString()}", TAG)
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {
        var sb = StringBuffer()

        //设备信息
        sb.append("设备信息：\n")
        for ((key, value) in infos!!) {
            sb.append("$key=$value\n")
        }
        val deviceInfo = sb.toString()

        //异常信息
        sb = StringBuffer()
        sb.append("异常信息：\n")
        sb.append(ex.stackTraceToString())
        val throwableInfo = sb.toString()


        //打印日志，以便调试
        Logger.e(throwableInfo, TAG)
        Logger.e(deviceInfo, TAG)


        //保存错误信息到文件
        try {
            Logger.w("crash log will write to $path", TAG)
            val timestamp = System.currentTimeMillis()
            val time = currentTime()
            val fileName = "crash-$time-$timestamp.log"
            if (checkSDCardAvailable()) {
                saveFileToSDCard(path, fileName, "$throwableInfo\n$deviceInfo")
            }
            return fileName
        } catch (e: Exception) {
            Logger.e(
                "an error occured while writing file... path:$path \n info:${e.stackTraceToString()}",
                TAG,
            )
        }
        return null
    }

}