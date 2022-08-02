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
package com.naivor.android.app.domain.login.ui.resetPsw

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.databinding.FragmentResetpswBinding
import com.naivor.android.app.others.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * Created by Naivor on 16-4-2.
 */
@AndroidEntryPoint
class ResetPswFragment : BaseFragment<FragmentResetpswBinding, ResetPswViewModel>() {
    override val viewModel: ResetPswViewModel by viewModels()

    override val pageTitle: String = "重置密码"

    override var isToolbarWhite: Boolean=false

    override val inflateRootView: (ViewGroup?) -> View = {
        __binding = FragmentResetpswBinding.inflate(layoutInflater, it, false)
        binding.root
    }


    override fun initTitle(activity: AppCompatActivity) {
        binding.customTitle.run {
            toolbarView=toolbar
            titleView=tvCenter
            navigationView=imgNavigation

        }
        super.initTitle(activity)
    }

    override fun initPageView() {
        with(binding) {
            tvSendemail.setOnClickListener {
                val email = edtEmail.text?.toString()
                if (email.isNullOrEmpty()) {
                    showToast("请填写您的邮箱")

                } else {
                    viewModel.validateEmail(email) {
                        if (it) {
                            layoutEmail.visibility = View.GONE
                            layoutPasswd.visibility = View.VISIBLE
                        } else {
                            showToast("无此账号，请填写正确邮箱")
                        }
                    }
                }
            }

            tvReset.setOnClickListener {
                val passwd = edtPsw.text?.toString()
                val passwdAgain = edtPswagain.text?.toString()

                if (passwd.isNullOrEmpty()) {
                    showToast("请填写密码")
                } else if (passwdAgain.isNullOrEmpty()) {
                    showToast("请再次填写密码")
                } else {
                    if (passwd == passwdAgain) {
                        viewModel.resetPasswd(passwd) {
                            showToast(if (it) "密码重置成功" else "密码重置失败，请稍后再试")
                            if (it) {
                                Timer().schedule(object : TimerTask() {
                                    override fun run() {
                                        onBackPressed()
                                    }
                                }, 800)
                            }
                        }
                    } else {
                        showToast("两次输入的密码不一致")
                    }
                }
            }
        }

    }

}