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
package com.naivor.app.common.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * BaseActivity 是所有activity的基类，把一些公共的方法放到里面
 *
 *
 * Created by Naivor on 16-3-3.
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), PageInterface {

    protected lateinit var binding: VB

    abstract val viewModel: VM?

    protected lateinit var rootView: View

    abstract val setContentView: () -> View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = setContentView()
        initPageView()
        initPageData()
    }

    override fun initPageData() {
        viewModel?.initPage()
    }

    open fun pageTo(target: Class<out AppCompatActivity>, bundle: Bundle? = null) {
        val intent = Intent(this, target)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
    }
}