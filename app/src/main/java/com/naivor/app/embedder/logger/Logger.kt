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

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.naivor.app.BuildConfig
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.naivor.app.AppSetting
import com.naivor.app.embedder.logger.LogLevel.*
import timber.log.Timber

enum class LogLevel(val value: Int) {
    ERROR(-1), INFO(0), WARNING(1), DEBUG(2)
}

object Logger {

    private lateinit var level: LogLevel //默认打印提示级别的日志
    const val TAG = "Naivor-Log"
    const val TAG_PAGE = "Naivor-Pages"
    const val TAG_NET = "Naivor-Network"

    const val SEGMENT =1024  //分段大小，避免过长丢失

    private lateinit var application: Application

    fun context(): Context {
        return application.applicationContext
    }

    fun init(app: Application) {
        application = app

        resetLogLevel(AppSetting.logLevel)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReport())
        }

        //全局捕获异常
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Logger.e(
                """  exception thread: ${t.name}
                |       stack: ${e.stackTraceToString()}
            """.trimMargin()
            )
        }

        //监控页面状态
        watchActivityStatus(app)
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

    private fun logStatus(page: Any, methord: String) {
        val message = "|            ${page.javaClass.simpleName}.$methord()            |"
        val divider = "-".repeat(message.length)
        Timber.tag(TAG_PAGE).d(divider)
        Timber.tag(TAG_PAGE).d(message)
        Timber.tag(TAG_PAGE).d(divider)
    }

    private fun watchActivityStatus(app: Application) {
        //仅debug
        if (level == DEBUG) {

            app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    logStatus(activity, "onActivityCreated")
                    watchFragmentStatus(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                    logStatus(activity, "onActivityStarted")
                }

                override fun onActivityResumed(activity: Activity) {
                    logStatus(activity, "onActivityResumed")
                }

                override fun onActivityPaused(activity: Activity) {
                    logStatus(activity, "onActivityPaused")
                }

                override fun onActivityStopped(activity: Activity) {
                    logStatus(activity, "onActivityStopped")
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    logStatus(activity, "onActivitySaveInstanceState")
                }

                override fun onActivityDestroyed(activity: Activity) {
                    logStatus(activity, "onActivityDestroyed")
                }
            })
        }
    }

    private fun watchFragmentStatus(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                    super.onFragmentAttached(fm, f, context)
                    logStatus(f, "onFragmentAttached")
                }

                override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                    super.onFragmentCreated(fm, f, savedInstanceState)
                    logStatus(f, "onFragmentCreated")
                }

                override fun onFragmentViewCreated(
                    fm: FragmentManager,
                    f: Fragment,
                    v: View,
                    savedInstanceState: Bundle?
                ) {
                    super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                    logStatus(f, "onFragmentViewCreated")
                }

                override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                    super.onFragmentResumed(fm, f)
                    logStatus(f, "onFragmentResumed")
                }

                override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                    super.onFragmentPaused(fm, f)
                    logStatus(f, "onFragmentPaused")
                }

                override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                    super.onFragmentStopped(fm, f)
                    logStatus(f, "onFragmentStopped")
                }

                override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                    super.onFragmentViewDestroyed(fm, f)
                    logStatus(f, "onFragmentViewDestroyed")
                }

                override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                    super.onFragmentDestroyed(fm, f)
                    logStatus(f, "onFragmentDestroyed")
                }

                override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                    super.onFragmentDetached(fm, f)
                    logStatus(f, "onFragmentDetached")
                }

                override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                    super.onFragmentStarted(fm, f)
                    logStatus(f, "onFragmentStarted")
                }
            }, true)
        }
    }
}

class CrashReport : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Timber.e(t)
    }
}