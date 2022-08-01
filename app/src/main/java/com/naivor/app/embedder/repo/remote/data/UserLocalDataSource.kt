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

package com.naivor.app.embedder.repo.remote.data

import com.naivor.app.common.repo.local.LocalDataSource
import com.naivor.app.common.repo.remote.data.NetCode
import com.naivor.app.common.repo.remote.data.NetError
import com.naivor.app.embedder.repo.local.bean.User
import com.naivor.app.embedder.repo.local.db.AppDatabase
import com.naivor.app.others.Constants
import com.naivor.app.others.UserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(val database: AppDatabase) :
    LocalDataSource(database) {

    fun optUser(uid: Int) = database.userDao().getUserByUid(uid)

    fun optUserByPasswd(passwd: String) = database.userDao().getUserByPasswd(passwd)

    fun optUserByEmail(email: String) = database.userDao().getUserByEmail(email)

    fun optUserByName(name: String) = database.userDao().getUserByName(name)

    fun optUsers() = database.userDao().getUsers()

    suspend fun save(user: User) {
        database.userDao().insertAll(listOf(user))
    }

    fun login(account: String, passwd: String): Flow<User> {
        return flow {
            val user = optUserByPasswd(passwd).firstOrNull()
            user?.run {
                if (name == account || email == account || phone == account) { //登录成功
                    isLogin = true
                    save(this)
                    emit(this)
                } else {
                    throw NetError(NetCode.ACCOUNT_INFO_ERROR, "用户名或密码错误")
                }
            } ?: throw NetError(NetCode.ACCOUNT_INFO_ERROR, "用户名或密码错误")
        }.flowOn(io)
    }

    fun logout(): Flow<Boolean> {
        return flow {
            val logout = UserManager.getUser()?.run {
                isLogin = false
                save(this)
                true
            } ?: true
            UserManager.clear()
            emit(logout)
        }
    }

    fun register(name: String, email: String, passwd: String): Flow<User> {
        return flow {
            val user = User(0, name, "", Constants.Gender.UNKNOWN,
                "", email, 0L, "", "", "",
                passwd, "", true)

            save(user)
            emit(user)
        }.flowOn(io)
    }

    fun validateEmail(email: String): Flow<Boolean> {
        return flow {
            val user = optUserByEmail(email).firstOrNull()
            emit(user != null)
        }.flowOn(io)
    }

    fun resetPasswd(email: String, passwd: String): Flow<Boolean> {
        return flow {
            val user = optUserByEmail(email).firstOrNull()
            val reset = user?.run {
                this.passwd = passwd
                save(this)
                true
            } ?: false

            emit(reset)
        }.flowOn(io)
    }

}