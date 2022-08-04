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
package com.naivor.android.app.domain.message

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.common.base.BaseViewModel
import com.naivor.android.app.databinding.FragmentMsgBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Naivor on 16-3-18.
 */
@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMsgBinding, BaseViewModel>() {
    override val viewModel: BaseViewModel?=null

    override val pageTitle: String="消息"

    override var hideNavigation: Boolean=true

    override val inflateRootView: (ViewGroup?) -> View={
        __binding= FragmentMsgBinding.inflate(layoutInflater,it,false)
        binding.root
    }

    override fun initTitle(activity: AppCompatActivity) {
        binding.customTitle.run {
            toolbarView=toolbar
            titleView=tvTitle

        }
        super.initTitle(activity)
    }

    override fun initPageView() {
    }

}