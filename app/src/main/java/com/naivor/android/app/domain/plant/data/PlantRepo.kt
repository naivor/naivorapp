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

package com.naivor.android.app.domain.plant.data

import com.naivor.android.app.common.repo.Repository
import com.naivor.android.app.common.repo.remote.RemoteDataSource
import com.naivor.android.app.domain.plant.data.local.PlantLocalDataSource
import com.naivor.android.app.embedder.repo.local.bean.GardenPlanting
import javax.inject.Inject

class PlantRepo @Inject constructor(local: PlantLocalDataSource) :
    Repository<PlantLocalDataSource, RemoteDataSource<*>>(local, null) {

    fun getPlantedGardens() = local!!.getPlantedGardens()

    fun getPlants() = local!!.getPlants()

    fun getPlant(plantId: String) =  local!!.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        local!!.getPlantsWithGrowZoneNumber(growZoneNumber)

    suspend fun createGardenPlanting(plantId: String) {
        local!!.createGardenPlanting(plantId)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        local!!.removeGardenPlanting(gardenPlanting)
    }

    fun isPlanted(plantId: String) =
        local!!.isPlanted(plantId)
}