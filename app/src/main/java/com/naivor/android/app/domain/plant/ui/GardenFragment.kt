/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.android.app.domain.plant.ui

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.naivor.android.adapter.RecyclerAdapter
import com.naivor.android.app.R
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.databinding.FragmentGardenBinding
import com.naivor.android.app.domain.MainActivity
import com.naivor.android.app.domain.plant.adapter.GardenPlantingAdapter
import com.naivor.android.app.domain.plant.adapter.PLANT_LIST_PAGE_INDEX
import com.naivor.android.app.domain.sub.SubActivity
import com.naivor.android.app.embedder.repo.local.bean.PlantAndGardenPlantings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : BaseFragment<FragmentGardenBinding, GardenPlantViewModel>() {

    override val viewModel: GardenPlantViewModel by viewModels()

    override val pageTitle: String = "我的花园"

    val adapter = GardenPlantingAdapter().apply {
        itemClick = object : RecyclerAdapter.OnItemClick<PlantAndGardenPlantings> {
            override fun clickItem(position: Int, view: View, data: PlantAndGardenPlantings) {
                (activity as MainActivity).pageTo(
                    SubActivity::class.java,
                    generateBundle(SubActivity.SKIP_PLANT_DETAIL, data)
                )
            }

        }
    }

    override val inflateRootView: (ViewGroup?) -> View = {
        __binding = FragmentGardenBinding.inflate(layoutInflater, it, false)
        binding.root
    }


    override fun initPageView() {

        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }
    }

    override fun initPageData() {
        super.initPageData()

        subscribeUi(adapter, binding)
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) {
            binding.gardenList.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.layoutEmpty.visibility = if (!it.isNullOrEmpty()) View.GONE else View.VISIBLE

            adapter.submitItems(it)
        }
    }

    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }


}
