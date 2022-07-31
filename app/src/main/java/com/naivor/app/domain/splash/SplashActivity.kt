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
package com.naivor.app.domain.splash

import android.view.View
import com.naivor.app.common.base.BaseActivity
import com.naivor.app.common.base.BaseViewModel
import com.naivor.app.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SplashActivity app的欢迎页面
 *
 *
 * Created by Naivor on 16-3-3.
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>() {
    override val viewModel: BaseViewModel? = null

    override val setContentView: () -> View = {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.root
    }


    override fun initPageView() {
    }

}