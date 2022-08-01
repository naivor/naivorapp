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

package com.naivor.app.embedder.logger

import com.naivor.app.BuildConfig
import com.naivor.app.embedder.logger.LogLevel.*
import com.naivor.app.others.AppSetting
import timber.log.Timber

enum class LogLevel(val value: Int) {
    ERROR(-1), INFO(0), WARNING(1), DEBUG(2)
}

object Logger {

    private lateinit var level: LogLevel //默认打印提示级别的日志
    const val TAG = "Naivor-Log"
    const val TAG_PAGE = "Naivor-Pages"
    const val TAG_NET = "Naivor-Network"
    const val TAG_CRASH = "Naivor-Crash"

    const val SEGMENT =1024*2  //分段大小，避免过长丢失

    fun init() {

        resetLogLevel(AppSetting.logLevel)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReport())
        }

    }

    fun d(content: String, tag: String = TAG) {
        if (shouleLog(DEBUG)) {
            logSegment(content, tag) { message, logTag ->
                Timber.tag(logTag).d(message)
            }
        }
    }

    fun w(content: String, tag: String = TAG) {
        if (shouleLog(WARNING)) {
            logSegment(content, tag) { message, logTag ->
                Timber.tag(logTag).w(message)
            }
        }
    }

    fun i(content: String, tag: String = TAG) {
        if (shouleLog(INFO)) {
            logSegment(content, tag) { message, logTag ->
                Timber.tag(logTag).i(message)
            }
        }
    }

    fun e(content: String, tag: String = TAG) {
        if (shouleLog(ERROR)) {
            logSegment(content, tag) { message, logTag ->
                Timber.tag(logTag).e(message)
            }
        }
    }

    private fun shouleLog(logLevel: LogLevel): Boolean {
        return logLevel <= level
    }

    fun logSegment(content: String, tag: String = TAG, execLog: (String, String) -> Unit) {
        if (content.length > SEGMENT) {
            var message = content
            while (message.length > SEGMENT) {
                val segmentStr = message.substring(0, SEGMENT)
                execLog(segmentStr, tag)
                message = message.substring(SEGMENT, message.length)
            }
            execLog(message, tag)
        }else{
            execLog(content, tag)
        }
    }

    fun resetLogLevel(resetLevel: LogLevel) {
        level = if (BuildConfig.DEBUG) {
            DEBUG
        } else {
            resetLevel
        }
    }


}

class CrashReport : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Timber.e(t)
    }
}