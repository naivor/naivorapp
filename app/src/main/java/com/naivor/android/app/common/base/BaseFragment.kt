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
package com.naivor.android.app.common.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.viewbinding.ViewBinding
import com.naivor.android.app.R
import com.naivor.android.app.others.Constants.EXTRA_KEY
import com.naivor.android.app.others.Constants.EXTRA_VALUE


/**
 * BaseFragment 是所有activity的基类，把一些公共的方法放到里面
 *
 *
 * Created by Naivor on 16-3-3.
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), PageInterface {

    protected var __binding: VB? = null

    protected val binding get() = __binding!!

    abstract val viewModel: VM?

    abstract val pageTitle: String

    protected lateinit var rootView: View
    protected var toolbarView: Toolbar? = null
    protected var titleView: TextView? = null

    open var toolbarColorPrimary = R.color.colorPrimary
    open var isToolbarColorPrimary = true

    open var hideNavigation = false
    open var isUseWhiteNavigation = false
    open var whiteNavigation = R.drawable.ic_nav_back_white
    open var blackNavigation = R.drawable.ic_nav_back_black

    abstract val inflateRootView: (ViewGroup?) -> View

    protected val requestKey = "result_from_another_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments(arguments)
    }

    open fun initArguments(bundle: Bundle?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflateRootView(container)

        initTitle(activity as AppCompatActivity)

        initPageView()

        // 当从下一个Fragment返回会触发onViewCreated，因此初始化数据放到这里，如果返回需要刷新页面，可在onViewCreated中再次调用
        initPageData()

        return rootView
    }

    open fun initTitle(activity: AppCompatActivity) {
        activity.run {
            toolbarView?.run {
                setSupportActionBar(this)

                //背景
                if (isToolbarColorPrimary) setBackgroundResource(toolbarColorPrimary)

                //标题
                supportActionBar?.setDisplayShowTitleEnabled(false)
                titleView?.run { text = pageTitle }


                //返回
                if (!hideNavigation) {

                    setNavigationIcon(
                        if (isUseWhiteNavigation) {
                            whiteNavigation
                        } else {
                            blackNavigation
                        }
                    )

                    setNavigationOnClickListener {
                        this@BaseFragment.onBackPressed()
                    }
                }
            }


        }
    }


    override fun initPageData() {
        viewModel?.initPage()
    }

    open fun onBackPressed() {
        activity?.onBackPressed()
    }

    @Suppress("UNCHECKED_CAST")
    open fun generateBundle(target: String?, data: Any?): Bundle {
        return Bundle().apply {
            putString(EXTRA_KEY, target)
            data?.let {
                viewModel?.dataToBus(it)
                when (it) {
                    is String -> putString(EXTRA_VALUE, it)
                    is Parcelable -> putParcelable(EXTRA_VALUE, it)
                    is List<*> -> putParcelableArrayList(
                        EXTRA_VALUE,
                        it as ArrayList<out Parcelable>
                    )
                }
            }

        }
    }

    open fun initResultListener(handResult: ((Bundle) -> Unit)) {
        setFragmentResultListener(
            requestKey,
        ) { key, result ->
            if (key == requestKey) {
                handResult.invoke(result)
            }
        }
    }

    open fun setResult(target: String?, data: Any?) {
        setFragmentResult(requestKey, generateBundle(target, data))
    }
}