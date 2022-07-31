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

import com.naivor.app.embedder.logger.Logger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.Charset

/**
 * 打印请求参数，及返回结果
 */
public class HttpLoggingInterceptor(private var level: Level = Level.NONE) : Interceptor {
    private val logger: HttpLogger = HttpLogger()

    private val charset = Charset.forName("UTF-8");

    enum class Level {
        BASIC, NONE
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return when (level) {
            Level.BASIC -> {
                processRequest(request)
                val response = chain.proceed(request)
                processResponse(response)
            }
            Level.NONE -> {
                chain.proceed(request)
            }
        }
    }

    private fun processRequest(request: Request): String? {
        return request.body?.let {
            val buffer = Buffer()
            it.writeTo(buffer);
            it.contentType()?.run {
                //打印请求信息
                val requestInfo =
                    """"
                        发送请求: 
                             - url：${request.url} 
                             - time:${System.currentTimeMillis()}
                             - method：${request.method}
                             - headers：${request.headers.toString().replace("\n", " ")} 
                             - parameters: ${
                        buffer.readString(charset(charset)!!).replace("\n", "")
                    }
                    """.trimIndent()

                logger.log(requestInfo)

                requestInfo
            }
        }
    }

    private fun processResponse(
        response: Response
    ): Response {
        response.body.let { it ->
            val bodyString = it.string()
            it.contentType()?.let {
                //打印响应信息
                val responseInfo =
                    """"
                        收到响应: 
                             - url：${response.request.url} 
                             - time:${System.currentTimeMillis()}
                             - code：${response.code}
                             - body：$bodyString
                    """.trimIndent()

                logger.log(responseInfo)

                //response.body().string()只能请求一次,请求过后,就会关闭,
                // 再次调用response.body().string()就会报java.lang.IllegalStateException: closed异常
                //重新builder一个Response返回
                return response.newBuilder()
                    .body(bodyString.toResponseBody(it))
                    .build()
            }

        }

        return response.newBuilder().build()
    }
}

class HttpLogger {

    fun log(message: String) {
        Logger.d(message, tag = Logger.TAG_NET)
    }
}