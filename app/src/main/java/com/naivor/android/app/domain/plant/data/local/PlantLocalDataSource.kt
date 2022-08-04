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

package com.naivor.android.app.domain.plant.data.local

import com.naivor.android.app.common.repo.local.LocalDataSource
import com.naivor.android.app.embedder.repo.local.bean.GardenPlanting
import com.naivor.android.app.embedder.repo.local.db.AppDatabase
import javax.inject.Inject

class PlantLocalDataSource @Inject constructor(val database: AppDatabase):LocalDataSource(database) {

    fun getPlants() = database.plantDao().getPlants()

    fun getPlant(plantId: String) =  database.plantDao().getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        database.plantDao().getPlantsWithGrowZoneNumber(growZoneNumber)

    fun getPlantedGardens() = database.gardenPlantingDao().getPlantedGardens()

    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        database.gardenPlantingDao().insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        database.gardenPlantingDao().deleteGardenPlanting(gardenPlanting)
    }

    fun isPlanted(plantId: String) =
        database.gardenPlantingDao().isPlanted(plantId)

}