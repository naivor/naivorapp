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
package com.naivor.app.embedder.repo

import com.naivor.app.common.repo.Repository
import com.naivor.app.common.repo.remote.data.NetCode
import com.naivor.app.common.repo.remote.data.NetError
import com.naivor.app.embedder.repo.local.bean.User
import com.naivor.app.embedder.repo.remote.data.UserLocalDataSource
import com.naivor.app.embedder.repo.remote.data.UserRemoteDataSource
import com.naivor.app.others.UserManager
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * User的数据仓库类
 *
 *
 * Created by Naivor on 16-3-16.
 */
class UserRepo @Inject constructor(local: UserLocalDataSource, remote: UserRemoteDataSource) :
    Repository<UserLocalDataSource, UserRemoteDataSource>(local, remote) {


    /**
     * 登录请求
     *
     * @param account
     * @param psw
     * @return
     */
    fun login(account: String, passwd: String): Flow<User> {
//        return remote!!.login(account, psw)
//            .transform {
//                local!!.save(it)
//                emit(it)
//            }

        return local!!.login(account, passwd)
            .transform {
                UserManager.update(it)
                emit(it)
            }
    }

    fun logout():Flow<Boolean>{
        return local!!.logout()
    }

    fun register(name:String,email:String,passwd: String):Flow<User>{
        return local!!.register(name, email, passwd)
            .transform {
                UserManager.update(it)
                emit(it)
            }
    }

    fun validateEmail(email: String):Flow<Boolean>{
        return local!!.validateEmail(email)

    }

    fun resetPasswd(email: String,passwd: String): Flow<Boolean> {
        return local!!.resetPasswd(email,passwd)
    }
}