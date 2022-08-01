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

package com.naivor.app.domain.login.ui.register

import androidx.lifecycle.viewModelScope
import com.naivor.app.common.base.BaseViewModel
import com.naivor.app.embedder.repo.UserRepo
import com.naivor.app.others.catch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val userRepo: UserRepo) : BaseViewModel() {

    override fun initPage() {
    }


    fun register(
        name: String,
        email: String,
        passwd: String,
        callback: ((Boolean) -> Unit)? = null
    ) {
        viewModelScope.launch {
            userRepo.register(name, email, passwd)
                .catch(true) { e ->
                    callback?.invoke(false)
                }
                .collect {
                    callback?.invoke(true)
                }
        }
    }
}