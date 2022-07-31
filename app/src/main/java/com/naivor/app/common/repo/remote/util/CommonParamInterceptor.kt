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

package com.naivor.app.common.repo.remote.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class CommonParamInterceptor @Inject constructor() :
    Interceptor {
    private var addCommonParam: Boolean = false

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = if (addCommonParam) {
            chain.proceed(addCommonParam(request))
        } else null

        return response ?: chain.proceed(request)
    }

    private fun addCommonParam(
        request: Request
    ): Request {
        return request
            .newBuilder()
            .header("param1", "value1")
            .header("param2", "value2")
            .build()
    }
}