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
package com.naivor.android.app.domain.sub.setting

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.databinding.FragmentSettingBinding
import com.naivor.android.app.others.showToast
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Naivor on 16-3-18.
 */
@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    override val viewModel: SettingViewModel by viewModels()

    override val pageTitle: String = "设置"

    override val inflateRootView: (ViewGroup?) -> View = {
        __binding = FragmentSettingBinding.inflate(layoutInflater, it, false)
        binding.root
    }

    override fun initTitle(activity: AppCompatActivity) {
        binding.customTitle.run {
            toolbarView = toolbar
            titleView = tvTitle

        }
        super.initTitle(activity)
    }

    override fun initPageView() {
        with(binding) {
            tvLogout.setOnClickListener {
                viewModel.logout() {
                    showToast(if (it) "退出登录成功" else "退出登录失败")
                }
            }
        }
    }

}