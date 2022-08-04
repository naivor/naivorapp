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

package com.naivor.android.app.domain.plant.ui


import android.view.*
import androidx.fragment.app.viewModels
import com.naivor.android.adapter.RecyclerAdapter
import com.naivor.android.app.R
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.databinding.FragmentPlantListBinding
import com.naivor.android.app.domain.MainActivity
import com.naivor.android.app.domain.plant.adapter.PlantAdapter
import com.naivor.android.app.domain.sub.SubActivity
import com.naivor.android.app.embedder.repo.local.bean.Plant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantListFragment : BaseFragment<FragmentPlantListBinding, GardenPlantViewModel>() {

    override val viewModel: GardenPlantViewModel by viewModels()

    override val pageTitle: String = "植物目录"

    val adapter = PlantAdapter().apply {
        itemClick = object : RecyclerAdapter.OnItemClick<Plant> {
            override fun clickItem(position: Int, view: View, data: Plant) {
                (activity as MainActivity).pageTo(
                    SubActivity::class.java,
                    generateBundle(SubActivity.SKIP_PLANT_DETAIL, data)
                )
            }
        }
    }

    override val inflateRootView: (ViewGroup?) -> View = {
        __binding = FragmentPlantListBinding.inflate(layoutInflater, it, false)
        binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initPageView() {

        binding.plantList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
    }

    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitItems(plants)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }


}
