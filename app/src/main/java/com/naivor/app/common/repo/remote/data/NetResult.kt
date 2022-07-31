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

package com.naivor.app.common.repo.remote.data

data class NetResult<T>(
    var code: Int,
    var data: T,
    var message: String?
) {


    fun result(): T {
        if (code == NetCode.OK.code) return data as T
        else throw  when (code) {
            NetCode.NOT_LOGIN.code -> NetError(NetCode.NOT_LOGIN, message)
            else -> NetError.unknown(code, message)
        }
    }
}