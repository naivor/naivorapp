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
package com.naivor.app

import com.naivor.app.embedder.logger.Logger
import com.naivor.app.embedder.repo.UserRepo
import com.naivor.app.others.AppSetting
import com.naivor.app.others.CrashHandler
import com.naivor.app.others.PageWatcher
import com.naivor.app.others.UserManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * NaivorApp 程序的Application类，统领全局
 *
 *
 * Created by Naivor on 16-3-3.
 */
@HiltAndroidApp
class NaivorApp : android.app.Application() {

    @Inject
    lateinit var repo: UserRepo

    override fun onCreate() {
        super.onCreate()

        //应用设置
        AppSetting.init(this)

        //用户信息
        UserManager.init(repo)

        //初始化日志系统
        Logger.init()

        //全局捕获异常
        CrashHandler.init()

        //监控页面
        PageWatcher.init(this)
    }
}