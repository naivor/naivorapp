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

package com.naivor.android.app.domain.plant.adapter

import android.view.ViewGroup
import com.naivor.android.adapter.RecyclerAdapter
import com.naivor.android.adapter.holder.ItemHolder
import com.naivor.android.adapter.holder.ViewHolderFactory
import com.naivor.android.app.databinding.ListItemGardenPlantingBinding
import com.naivor.android.app.embedder.imageloader.load
import com.naivor.android.app.embedder.repo.local.bean.PlantAndGardenPlantings
import com.naivor.android.kotlinex.inflater
import com.naivor.android.kotlinex.toPassedTime

class GardenPlantingAdapter : RecyclerAdapter<PlantAndGardenPlantings>() {
    override val factory = object : ViewHolderFactory<PlantAndGardenPlantings>() {
        override fun produceViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder<PlantAndGardenPlantings> {
            return GardenPlantingHolder(
                ListItemGardenPlantingBinding.inflate(
                    parent.inflater(),
                    parent,
                    false
                )
            )
        }
    }
}


class GardenPlantingHolder(val binding: ListItemGardenPlantingBinding) :
    ItemHolder<PlantAndGardenPlantings>(binding) {

    override fun bindData(data: PlantAndGardenPlantings) {
        super.bindData(data)

        with(binding) {
            data.plant.let {
                imageView.load(it.imageUrl)
                plantName.text = it.name
                waterInterval.text = "${it.wateringInterval}"
            }
            data.gardenPlantings[0].let {
                plantDate.text = it.plantDate.timeInMillis.toPassedTime()
                waterDate.text = it.lastWateringDate.timeInMillis.toPassedTime()

            }
        }

    }

}