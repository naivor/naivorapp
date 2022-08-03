/*
 * Copyright (c) 2022-2022. Naivor. All rights reserved. 
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

package com.naivor.android.kotlinex

import android.app.Activity
import android.content.Context
import android.graphics.Outline
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.min

/**
 * PixelSize
 */
fun Activity.pixelSize(dimensId: Int): Int {
    return resources.getDimensionPixelSize(dimensId)
}

fun Fragment.pixelSize(dimensId: Int): Int {
    return resources.getDimensionPixelSize(dimensId)
}

fun View.pixelSize(dimensId: Int): Int {
    return resources.getDimensionPixelSize(dimensId)
}

/**
 * LayoutInflater
 */
fun View.inflater(): LayoutInflater {
    return LayoutInflater.from(context)
}

/**
 * view 的圆角剪裁
 */
fun View.clipToOutlineCorners(
    corners: Float = 0f,
    leftTop: Float = 0f,
    rightTop: Float = 0f,
    leftBottom: Float = 0f,
    rightBottom: Float = 0f
) {
    if (corners != 0f || leftTop != 0f || rightTop != 0f || leftBottom != 0f || rightBottom != 0f) {
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                view?.run {
                    val path = Path()
                    val rect = Rect()
                    getDrawingRect(rect)

                    var leftTopCorner = leftTop
                    var rightTopCorner = rightTop
                    var leftBottomCorner = leftBottom
                    var rightBottomCorner = rightBottom
                    var allCorners = corners
                    if (allCorners == 0f) {
                        val mWidth = min(width, height)
                        allCorners = (mWidth / 10).toFloat()
                    }
                    if (allCorners != 0f) {
                        val useCorners =
                            leftTopCorner == 0f && rightTopCorner == 0f && leftBottomCorner == 0f && rightBottomCorner == 0f
                        if (leftTopCorner == 0f && useCorners) leftTopCorner = allCorners
                        if (rightTopCorner == 0f && useCorners) rightTopCorner = allCorners
                        if (leftBottomCorner == 0f && useCorners) leftBottomCorner = allCorners
                        if (rightBottomCorner == 0f && useCorners) rightBottomCorner = allCorners
                    }

                    path.addRoundRect(
                        rect.left.toFloat(),
                        rect.top.toFloat(),
                        rect.right.toFloat(),
                        rect.bottom.toFloat(),
                        floatArrayOf(
                            leftTopCorner,
                            leftTopCorner,
                            rightTopCorner,
                            rightTopCorner,
                            rightBottomCorner,
                            rightBottomCorner,
                            leftBottomCorner,
                            leftBottomCorner
                        ),
                        Path.Direction.CW
                    )

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                        outline!!.setConvexPath(path)
                    } else {
                        outline!!.setPath(path)
                    }
                }
            }
        }
    }
}


/**
 * 加载HTML
 */
fun TextView.loadHtml(
    html: String,
    fromHtmlFlags: Int = HtmlCompat.FROM_HTML_MODE_LEGACY,
    tagHandler: Html.TagHandler? = null,
    clickImage: ((Int, ArrayList<String>) -> Unit)? = null
) {
    val imageGetter = Html.ImageGetter { source ->
        val drawable = LevelListDrawable()
        CoroutineScope(Dispatchers.Main).launch {
            flow {
                val sourceDrawable: Drawable = withContext(Dispatchers.IO) {
                    val futureTarget = Glide.with(context).load(source).submit()
                    futureTarget.get()
                }

                emit(sourceDrawable)
            }.flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.e("loadHtml", "load image failed：${e.stackTraceToString()}")
                }
                .collect {
                    it.let { dra ->
                        drawable.addLevel(1, 1, dra)

                        val width = this@loadHtml.width
                        //高度按比例取
                        val height =
                            if (dra.intrinsicWidth != 0) dra.intrinsicHeight * width / dra.intrinsicWidth else dra.intrinsicHeight
                        drawable.setBounds(0, 0, width, height)
                        drawable.level = 1
                        val text = this@loadHtml.text
                        this@loadHtml.text = text
                        this@loadHtml.refreshDrawableState()
                    }
                }
        }

        drawable
    }
    val spanned = HtmlCompat.fromHtml(
        html, fromHtmlFlags,
        imageGetter, tagHandler
    )

    //设置可触发ClickableSpan的点击事件
    if (clickImage != null) {

        isFocusable = true
        isFocusableInTouchMode = true
        movementMethod = LinkMovementMethod.getInstance()

        val imageUrlList = ArrayList<String>()

        if (spanned is SpannableStringBuilder) {
            val images = spanned.getSpans(0, spanned.length, ImageSpan::class.java)
            //获取所有图片
            for (span in images) {
                span.source?.let {
                    imageUrlList.add(it)
                }
            }

            //添加点击事件
            for (span in images) {
                val source = span.source
                val start: Int = spanned.getSpanStart(span)
                val end: Int = spanned.getSpanEnd(span)
                val clickSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        source?.let { clickImage.invoke(imageUrlList.indexOf(it), imageUrlList) }
                    }
                }

                spanned.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    text = spanned
}




/**
 * 圆角网格布局
 */
fun RecyclerView.simpleDecoration(
    left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0,
) {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            val columns = when (val manager = parent.layoutManager) {
                is GridLayoutManager -> manager.spanCount
                is StaggeredGridLayoutManager -> manager.spanCount
                is LinearLayoutManager -> {
                    if (manager.orientation == LinearLayoutManager.VERTICAL) 1 else Int.MAX_VALUE
                }
                else -> 0
            }

            if (columns != 0) {
                val position = parent.getChildAdapterPosition(view)

                if (columns > 1) {
                    if (position / columns != parent.childCount / columns) {  //非最后一行
                        outRect.bottom = bottom
                    }
                    val span = position % columns
                    if (span > 0) {  //非第一列
                        outRect.left = left
                    }
                    if (span < columns - 1) {  //非最后一列
                        outRect.right = right
                    }
                }

                if (position >= columns) {  //非第一行
                    outRect.top = top
                }
            }
        }
    })
}

/**
 * SearchView的文字大小
 */
fun SearchView.editTextSize(textSize: Int): EditText {
    val edit = findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
    edit.textSize = textSize.toFloat()
    return edit
}

/**
 * AppBarLayout收缩才显示Title，展开才能下拉刷新
 */
fun AppBarLayout.expandHideTitle(titleView: View?, refreshLayout: SwipeRefreshLayout?=null) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
        appBarLayout?.run {
            titleView?.run {
                visibility = if (abs(i) >= totalScrollRange) View.VISIBLE else View.INVISIBLE
            }
            refreshLayout?.run {
                isEnabled = abs(i) < totalScrollRange
            }
        }
    })
}

/**
 * 获取 statusBar 高度
 */
fun Context.statusBarHeight(): Int {
    var height = 0
    val resources = applicationContext.resources
    val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        height = resources.getDimensionPixelSize(resourceId)
    }

    return height
}