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
package com.naivor.android.app.embedder.repo.remote.responce

import com.naivor.android.app.common.repo.remote.data.ResultData
import com.naivor.android.app.embedder.repo.local.bean.User
import com.naivor.android.app.others.Constants

/**
 * 登录请求返回的结果
 *
 *
 * Created by Naivor on 16-3-8.
 */
data class UserResult(
    private val id: Int,//用户的id
    private val type: String?, //用户的类型
    private val name: String?,
    private val blog: String?,
    private val location: String?,
    private val email: String?,
) : ResultData {

    /**
     * 将数据转换成User
     *
     */
    override fun wrap(): User {
        return User(
            id,
            safe(name),
            "",
            Constants.Gender.UNKNOWN,
            "",
            safe(email),
            0L,
            "",
            "",
            safe(blog),
            "",
            location
        )
    }
}


