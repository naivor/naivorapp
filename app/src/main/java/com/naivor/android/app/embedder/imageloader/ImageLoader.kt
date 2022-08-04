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

package com.naivor.android.app.embedder.imageloader

import android.content.Context
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.naivor.android.app.common.base.KotlinTask
import com.naivor.android.app.embedder.logger.Logger
import com.naivor.android.kotlinex.loadHtml
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//图片加载框架
object ImageLoader {
    var placeholderGlobal: Int? = null
    var errorGlobal: Int? = null

    fun load(
        view: ImageView,
        url: Any?,
        placeholder: Int? = null,
        error: Int? = null,
        corners: Int? = null,
        circleCrop: Boolean = false,
        width: Int? = null,
        height: Int? = null
    ) {
        val loader = Glide.with(view).load(url)

        //占位
        val placeImage = placeholder ?: placeholderGlobal
        placeImage?.let { loader.placeholder(it) }

        //错误
        val errorImage = error ?: errorGlobal
        errorImage?.let { loader.error(it) }

        //圆形还是圆角
        if (!circleCrop) {
            if (corners != null) {
                loader.apply(RequestOptions().transform(CenterCrop(), RoundedCorners(corners)))
            }
        } else {
            loader.circleCrop()
        }

        //自定义大小
        if (width != null && height != null) {
            loader.override(width, height)
        }


        loader.into(view)
    }

    fun getDrawable(
        context: Context,
        url: Any?,
        placeholder: Int? = null,
        error: Int? = null,
    ): Flow<Drawable?> {
        val loader = Glide.with(context).load(url)

        //占位
        val placeImage = placeholder ?: placeholderGlobal
        val placeDrawable = placeImage?.let { ContextCompat.getDrawable(context, it) }


        //错误
        val errorImage = error ?: errorGlobal
        val errorDrawable = errorImage?.let { ContextCompat.getDrawable(context, it) }

        return flow {
            emit(placeDrawable)

            val drawable = withContext(KotlinTask.io) {
                val futureTarget = loader.submit()
                futureTarget.get()
            }

            emit(drawable)
        }.flowOn(KotlinTask.io)
            .catch { e ->
                Logger.e("加载图片$url 出错,错误信息：${e.stackTraceToString()}")
                emit(errorDrawable)
            }
    }
}

class ImageOptions {

    var placeholder: Int? = null
    var error: Int? = null
    var width: Int? = null
    var height: Int? = null
    var corners: Int? = null
    var circleCrop: Boolean = false
}

//图片加载扩展 -- url
fun ImageView.load(url: Any?, options: (ImageOptions.() -> Unit)? = null) {

    val imageOptions = options?.let { ImageOptions().also(options) }

    ImageLoader.load(
        this,
        url,
        imageOptions?.placeholder,
        imageOptions?.error,
        imageOptions?.corners,
        imageOptions?.circleCrop ?: false,
        imageOptions?.width,
        imageOptions?.height
    )
}

//图片加载扩展 -- url
fun View.setBackground(url: Any?, options: (ImageOptions.() -> Unit)? = null) {
    val imageOptions = options?.let { ImageOptions().also(options) }

    val drawable = LevelListDrawable()
    background=drawable



    CoroutineScope(KotlinTask.main).launch {
        ImageLoader.getDrawable(
            context,
            url,
            imageOptions?.placeholder,
            imageOptions?.error,
        ).collect{
            it?.run {
                drawable.addLevel(1, 1, this)

                val width =  imageOptions?.width ?:  this@setBackground.width
                //高度按比例取
                val height =  imageOptions?.height ?: if (intrinsicWidth != 0) intrinsicHeight * width / intrinsicWidth else intrinsicHeight
                drawable.setBounds(0, 0, width, height)
                drawable.level = 1
                this@setBackground.refreshDrawableState()
            }
        }
        
    }
}