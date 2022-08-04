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

object Constants {
    const val EXTRA_KEY="extra_key"
    const val EXTRA_VALUE="extra_value"

    const val DATABASE_NAME = "app-database"
    const val SHARED_PREF_NAME = "app-shared_preference"

    const val PLANT_DATA_FILENAME = "plants.json"

    const val  LAST_LOGIN_ACCOUNT="login_account"

    const val API_URL_DEBUG ="https://api.github.com"
    const val API_URL_RELEASE="https://api.github.com"

    const val CRASH_PATH="/naivor/crash"
    const val CACHE_PATH="/naivor/cache"

    //数据库
    object DB {

        const val TABLE_USER = "user_info"
    }

    /**
     * 性别
     */
    enum class Gender(val value: String) {

        MALE("男"), FEMALE("女"), UNKNOWN("未知")
    }
}