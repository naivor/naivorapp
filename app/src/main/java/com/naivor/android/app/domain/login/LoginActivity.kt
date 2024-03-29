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
package com.naivor.android.app.domain.login

import android.view.View
import com.naivor.android.app.common.base.BaseActivity
import com.naivor.android.app.common.base.BaseViewModel
import com.naivor.android.app.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * LoginActivity app的登录页面
 *
 *
 * Created by Naivor on 16-3-3.
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, BaseViewModel>() {
    override val viewModel: BaseViewModel? = null

    override val inflateRootView: () -> View = {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.root
    }

    override fun initPageView() {

    }

}