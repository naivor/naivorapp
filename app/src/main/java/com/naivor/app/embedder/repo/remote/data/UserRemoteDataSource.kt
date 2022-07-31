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

import com.naivor.app.common.repo.remote.RemoteDataSource
import com.naivor.app.embedder.repo.local.bean.User
import com.naivor.app.embedder.repo.remote.apiService.UserApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor() : RemoteDataSource<UserApis>() {

    fun login(account: String, passwd: String): Flow<User> {
        return apis().login(account, passwd)
            .transform { emit(it.wrap()) }
            .flowOn(io)
    }
}