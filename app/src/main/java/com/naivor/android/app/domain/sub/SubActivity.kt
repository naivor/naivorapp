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
package com.naivor.android.app.domain.sub

import android.view.View
import androidx.navigation.findNavController
import com.naivor.android.app.R
import com.naivor.android.app.common.base.BaseActivity
import com.naivor.android.app.common.base.BaseViewModel
import com.naivor.android.app.databinding.ActivitySubBinding
import com.naivor.android.app.domain.plant.ui.detail.PlantDetailFragment
import com.naivor.android.app.domain.sub.setting.SettingFragment
import com.naivor.android.app.others.Constants.EXTRA_KEY
import dagger.hilt.android.AndroidEntryPoint

/**
 * LoginActivity app的登录页面
 *
 *
 * Created by Naivor on 16-3-3.
 */
@AndroidEntryPoint
class SubActivity : BaseActivity<ActivitySubBinding, BaseViewModel>() {
    companion object {
        val SKIP_SETTING = SettingFragment::class.qualifiedName
        val SKIP_PLANT_DETAIL = PlantDetailFragment::class.qualifiedName
    }

    override val viewModel: BaseViewModel? = null

    private var skipPage: String? = null

    override val inflateRootView: () -> View = {
        binding = ActivitySubBinding.inflate(layoutInflater)
        binding.root
    }

    override fun initPageView() {
        skipPage = intent.getStringExtra(EXTRA_KEY)
    }

    override fun onStart() {
        super.onStart()

        dealSkipPage()
    }

    private fun dealSkipPage() {
        skipPage?.run {
            val action = when (this) {
                SKIP_SETTING -> R.id.action_subFragment_to_settingFragment
                SKIP_PLANT_DETAIL -> R.id.action_subFragment_to_plantDetailFragment
                else -> throw IllegalArgumentException("no such skip page destination defined:$this")
            }

            findNavController(R.id.container).navigate(action)
        }
    }

}