/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.naivor.android.app.domain.login.ui.register

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.naivor.android.app.R
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.databinding.FragmentRegisterBinding
import com.naivor.android.app.others.showToast
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Naivor on 16-4-2.
 */
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {
    override val viewModel: RegisterViewModel by viewModels()

    override val pageTitle: String="用户注册"

    override val inflateRootView: (ViewGroup?) -> View={
        __binding=FragmentRegisterBinding.inflate(layoutInflater,it,false)
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
        with(binding){
            tvRegister.setOnClickListener {
                val userName = edtName.text?.toString()
                val email = edtEmail.text?.toString()
                val passwd = edtPsw.text?.toString()
                val passwdAgain = edtPswagain.text?.toString()
                performRegister(userName,email,passwd,passwdAgain)
            }
        }
    }

    private fun performRegister(userName: String?, email: String?, passwd: String?, passwdAgain: String?) {
        if (userName.isNullOrEmpty()){
            showToast("请填写用户名")
            return
        }
        if (email.isNullOrEmpty()){
            showToast("请填写您的邮箱")
            return
        }
        if (passwd.isNullOrEmpty()){
            showToast("请填写密码")
            return
        }
        if (passwdAgain.isNullOrEmpty()){
            showToast("请再次填写密码")
            return
        }

        if (passwd==passwdAgain){
            viewModel.register(userName,email,passwd){
                if (it) {
                    showToast("注册账号成功")
                    findNavController().navigate(R.id.action_registerFragment_to_mainActivity)
                    activity?.finish()
                }else{
                    showToast("注册账号失败，请稍后再试")
                }
            }
        }else{
            showToast("两次输入的密码不一致")
        }

    }
}