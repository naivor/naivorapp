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
package com.naivor.android.app.domain.plant

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.naivor.android.app.R
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.common.base.BaseViewModel
import com.naivor.android.app.databinding.FragmentPlantBinding
import com.naivor.android.app.domain.plant.adapter.MY_GARDEN_PAGE_INDEX
import com.naivor.android.app.domain.plant.adapter.PLANT_LIST_PAGE_INDEX
import com.naivor.android.app.domain.plant.adapter.SunflowerPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Naivor on 16-3-18.
 */
@AndroidEntryPoint
class PlantFragment : BaseFragment<FragmentPlantBinding, BaseViewModel>() {
    override val viewModel: BaseViewModel?=null

    override val pageTitle: String="Sunflower"

    override var hideNavigation: Boolean=true

    override val inflateRootView: (ViewGroup?) -> View={
        __binding= FragmentPlantBinding.inflate(layoutInflater,it,false)
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
            viewPager.adapter=SunflowerPagerAdapter(this@PlantFragment)
            // Set the icon and text for each tab
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.setIcon(getTabIcon(position))
                tab.text = getTabTitle(position)
            }.attach()
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plant_list_title)
            else -> null
        }
    }

}