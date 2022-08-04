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

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.naivor.android.app.common.base.BaseViewModel
import com.naivor.android.app.domain.plant.data.PlantRepo
import com.naivor.android.app.embedder.repo.local.bean.Plant
import com.naivor.android.app.embedder.repo.local.bean.PlantAndGardenPlantings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GardenPlantViewModel @Inject constructor(val plantRepo: PlantRepo, private val savedStateHandle: SavedStateHandle):BaseViewModel() {
    companion object {
        private const val NO_GROW_ZONE = -1
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
    }

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        plantRepo.getPlantedGardens().asLiveData()

    private val growZone: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY) ?: NO_GROW_ZONE
    )

    val plants: LiveData<List<Plant>> = growZone.flatMapLatest { zone ->
        if (zone == NO_GROW_ZONE) {
            plantRepo.getPlants()
        } else {
            plantRepo.getPlantsWithGrowZoneNumber(zone)
        }
    }.asLiveData()


    fun isFiltered() = growZone.value != NO_GROW_ZONE

    override fun initPage() {
        if (!hasInited){
            hasInited=true

            viewModelScope.launch {
                growZone.collect { newGrowZone ->
                    savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
                }
            }
        }
    }


    fun setGrowZoneNumber(num: Int) {
        growZone.value = num
    }

    fun clearGrowZoneNumber() {
        growZone.value = NO_GROW_ZONE
    }
}