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

package com.naivor.android.app.domain.login.ui

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.naivor.android.app.R
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.common.utils.ToastUtil.show
import com.naivor.android.app.databinding.FragmentLoginBinding
import com.naivor.android.app.others.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()

    override val pageTitle: String = "登录"

    override val inflateRootView: (ViewGroup?) -> View = {
        __binding = FragmentLoginBinding.inflate(layoutInflater,it,false)
        binding.root
    }

    override fun initPageView() {
        with(binding){
            tvSkip.setOnClickListener {
                pageToMain()
            }

            tvLogin.setOnClickListener {
                val account=edtAccount.text?.toString()
                val passwd=edtPsw.text?.toString()

                val validate = validateInput(account, passwd)
                if (validate){
                    viewModel.login(account!!,passwd!!){
                        if (it) {
                            showToast("登录成功")
                            pageToMain()
                        }
                    }
                }

            }

            tvForgetpsw.setOnClickListener {
                findNavController().navigate(R.id.action_LoginFragment_to_resetPswFragment)
            }

            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_LoginFragment_to_registerFragment)
            }
        }

    }

    private fun pageToMain() {
        findNavController().navigate(R.id.action_LoginFragment_to_mainActivity)
        activity?.finish()
    }

    /**
     * 验证输入
     *
     * @param account
     * @param psw
     * @return
     */
    private fun validateInput(account: String?, psw: String?): Boolean {
        //检查账号是否合法
        if (account.isNullOrEmpty()) {
            show("请输入用户名或邮箱")
            return false
        }

        //检查密码是否合法
        if (psw.isNullOrEmpty()) {
            show("请输入密码")
            return false
        }
        return true
    }
}