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

import android.content.Context.MODE_PRIVATE
import com.naivor.app.embedder.repo.UserRepo
import com.naivor.app.embedder.repo.local.bean.User
import com.naivor.app.others.Constants.LAST_LOGIN_ACCOUNT
import com.naivor.app.others.Constants.SHARED_PREF_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

/**
 * 用户管理器,保存用户的信息
 *
 *
 * Created by Naivor on 17-3-23.
 */
object UserManager {
    private lateinit var userRepo: UserRepo

    private var user: User? = null

    //初始化
    fun init(repo: UserRepo) {
        userRepo = repo

        AppSetting.app.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
            .run {
                val account = getString(LAST_LOGIN_ACCOUNT, "")
                update(account)
            }


    }

    fun update(account:String?) {
        account?.let { it->
            if (it.isNotEmpty()) {
                CoroutineScope(Dispatchers.Default).launch {
                    user =   if (it.contains("@")) {
                        userRepo.local!!.optUserByEmail(it).firstOrNull()
                    }else{
                        userRepo.local!!.optUserByName(it).firstOrNull()
                    }
                }
            }

        }


    }

    fun update(newUser: User) {
        newUser.run {
            if (id != user?.id && user?.email!=email) {
                AppSetting.app.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
                    .edit().run {
                        val account = if (name.isEmpty()) email else name
                        putString(LAST_LOGIN_ACCOUNT, account)
                        commit()
                    }
            }
        }

        user = newUser
    }

    fun getUser(): User? {
        return user
    }

    fun isLogin(): Boolean {
        return user?.isLogin ?: false
    }
}