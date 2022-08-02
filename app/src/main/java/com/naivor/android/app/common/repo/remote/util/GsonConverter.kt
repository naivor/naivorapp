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

package com.naivor.android.app.common.repo.remote.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.naivor.android.app.common.repo.remote.data.NetError
import com.naivor.android.app.common.repo.remote.data.NetResult
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

class GsonConverterFactory(val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return GsonConverter(gson, TypeToken.get(type))
    }

    companion object {

        fun create(gson: Gson = Gson()): GsonConverterFactory {
            return GsonConverterFactory(gson)
        }
    }
}

class GsonConverter<T>(private val gson: Gson, private val typeToken: TypeToken<T>) :
    Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {

        val jsonReader: JsonReader = gson.newJsonReader(value.charStream())

        val isNetResult = typeToken.rawType == NetResult::class.java
        try {
            return if (isNetResult) {
                gson.fromJson(jsonReader, typeToken.type)
            } else {
                val token = TypeToken.getParameterized(NetResult::class.java, typeToken.type)
                val result: NetResult<T> = gson.fromJson(jsonReader, token.type)
                result.result()
            }
        } catch (e: JsonSyntaxException) {  // NetResult 中的data在某些异常状态下，后台可能返回 String ，手动解析处理
            val adapter = gson.getAdapter(TypeToken.get(NetResult::class.java))
            val result = adapter.read(jsonReader)
            result.message = "JSON 解析出错"
            val dealResult = result.result()
            throw if (dealResult is NetError) {
                dealResult
            } else {
                NetError.unknown(result.code, result.message, e.stackTraceToString())
            }
        } finally {
            value.close()
        }
    }
}