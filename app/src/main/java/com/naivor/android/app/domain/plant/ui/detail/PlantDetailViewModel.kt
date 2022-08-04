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

package com.naivor.android.app.domain.plant.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.naivor.android.app.BuildConfig
import com.naivor.android.app.common.base.BaseViewModel
import com.naivor.android.app.domain.plant.data.PlantRepo
import com.naivor.android.app.embedder.repo.local.bean.Plant
import com.naivor.android.app.embedder.repo.local.bean.PlantAndGardenPlantings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    val plantRepo: PlantRepo,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    companion object {
        private const val PLANT_ID_SAVED_STATE_KEY = "plantId"
    }

    var plantId: String =
        savedStateHandle.get<String>(PLANT_ID_SAVED_STATE_KEY) ?: when (val dataFromBus =
            dataFromBus<Any>()) {
            is Plant -> dataFromBus.plantId
            is PlantAndGardenPlantings -> dataFromBus.plant.plantId
            else -> throw IllegalArgumentException("plant detail must know the plant id")
        }

    val isPlanted = plantRepo.isPlanted(plantId).asLiveData()
    val plant = plantRepo.getPlant(plantId).asLiveData()

    fun addPlantToGarden() {
        viewModelScope.launch {
            plantRepo.createGardenPlanting(plantId)
        }
    }

    fun hasValidUnsplashKey() = (BuildConfig.UNSPLASH_ACCESS_KEY != "null")


    override fun initPage() {
        if (!hasInited) {
            hasInited = true


        }
    }
}