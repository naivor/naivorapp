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
package com.naivor.app.domain

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.naivor.app.R
import com.naivor.app.common.base.BaseActivity
import com.naivor.app.common.base.BaseViewModel
import com.naivor.app.databinding.ActivityMainBinding
import com.naivor.app.domain.sub.SubActivity
import com.naivor.app.domain.sub.SubActivity.Companion.SKIP_SETTING
import com.naivor.app.others.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

/**
 * Created by Naivor on 16-3-11.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    override val viewModel: BaseViewModel? = null

    var lastPosition = R.id.rb_hot

    private var firstTime: Long = 0

    private var drawerToggle: ActionBarDrawerToggle? = null

    override val inflateRootView: () -> View = {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root
    }


    override fun initPageView() {
        with(binding) {
            radios.setOnCheckedChangeListener { group, checkedId ->
                when (lastPosition) {
                    R.id.rb_hot -> {
                        skipFromHot(checkedId)
                    }
                    R.id.rb_plant -> {
                        skipFromPlant(checkedId)
                    }
                    R.id.rb_msg -> {
                        skipFromMsg(checkedId)
                    }
                    R.id.rb_mine -> {
                        skipFromMine(checkedId)
                    }
                }
                lastPosition = checkedId
            }

            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.item_setting -> pageTo(
                        SubActivity::class.java,
                        generateBundle(SKIP_SETTING, null)
                    )
                    else -> showToast("你选择了：${it.title}")
                }

                drawerLayout.closeDrawers()
                true
            }
        }

    }

    fun setupDrawerToggle(toolbar: Toolbar) {
        binding.drawerLayout.run {
            drawerToggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
            )
            addDrawerListener(drawerToggle!!)
        }
    }

    fun syncDrawerToggleState() {
        drawerToggle?.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }

        if (item.itemId == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        syncDrawerToggleState()
    }

    private fun skipFromMine(checkedId: Int) {
        when (checkedId) {
            R.id.rb_hot -> {
                findNavController(R.id.container).navigate(R.id.action_mineFragment_to_hotFragment)
            }
            R.id.rb_plant -> {
                findNavController(R.id.container).navigate(R.id.action_mineFragment_to_plantFragment)
            }
            R.id.rb_msg -> {
                findNavController(R.id.container).navigate(R.id.action_mineFragment_to_messageFragment)
            }
        }
    }

    private fun skipFromMsg(checkedId: Int) {
        when (checkedId) {
            R.id.rb_hot -> {
                findNavController(R.id.container).navigate(R.id.action_messageFragment_to_hotFragment)
            }
            R.id.rb_plant -> {
                findNavController(R.id.container).navigate(R.id.action_messageFragment_to_plantFragment)
            }
            R.id.rb_mine -> {
                findNavController(R.id.container).navigate(R.id.action_messageFragment_to_mineFragment)
            }
        }
    }

    private fun skipFromPlant(checkedId: Int) {
        when (checkedId) {
            R.id.rb_hot -> {
                findNavController(R.id.container).navigate(R.id.action_plantFragment_to_hotFragment)
            }
            R.id.rb_msg -> {
                findNavController(R.id.container).navigate(R.id.action_plantFragment_to_messageFragment)
            }
            R.id.rb_mine -> {
                findNavController(R.id.container).navigate(R.id.action_plantFragment_to_mineFragment)
            }
        }
    }

    private fun skipFromHot(checkedId: Int) {
        when (checkedId) {
            R.id.rb_plant -> {
                findNavController(R.id.container).navigate(R.id.action_hotFragment_to_plantFragment)
            }
            R.id.rb_msg -> {
                findNavController(R.id.container).navigate(R.id.action_hotFragment_to_messageFragment)
            }
            R.id.rb_mine -> {
                findNavController(R.id.container).navigate(R.id.action_hotFragment_to_mineFragment)
            }
        }
    }

    override fun onBackPressed() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime > 2000) {
            showToast("再按一次退出程序")
            firstTime = secondTime
        } else {
            finish()
            exitProcess(0)
        }
    }

}