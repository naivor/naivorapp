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

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.naivor.android.app.BuildConfig
import com.naivor.android.app.embedder.logger.Logger
import com.naivor.android.app.embedder.logger.Logger.TAG_PAGE

object PageWatcher {
    lateinit var application: Application

    fun init(app: Application) {
        application = app

        //仅debug
        if (BuildConfig.DEBUG) {
            watchActivityStatus(app)
        }
    }

    fun context(): Context {
        return application.applicationContext
    }

    private fun logStatus(page: Any, methord: String) {
        val message = "|            ${page.javaClass.simpleName}.$methord()            |"
        val divider = "-".repeat(message.length)

        Logger.d(divider, TAG_PAGE)
        Logger.d(message, TAG_PAGE)
        Logger.d(divider, TAG_PAGE)
    }

    private fun watchActivityStatus(app: Application) {

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

    private fun watchFragmentStatus(activity: Activity) {

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object :
                FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentAttached(
                    fm: FragmentManager,
                    f: Fragment,
                    context: Context
                ) {
                    super.onFragmentAttached(fm, f, context)
                    logStatus(f, "onFragmentAttached")
                }

                override fun onFragmentCreated(
                    fm: FragmentManager,
                    f: Fragment,
                    savedInstanceState: Bundle?
                ) {
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