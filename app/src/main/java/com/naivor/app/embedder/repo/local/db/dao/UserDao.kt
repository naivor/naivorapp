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

package com.naivor.app.embedder.repo.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naivor.app.embedder.repo.local.bean.User
import com.naivor.app.others.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM ${Constants.DB.TABLE_USER} ORDER BY id")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM ${Constants.DB.TABLE_USER} WHERE id=:uid")
    fun getUserByUid(uid: Int): Flow<User>

    @Query("SELECT * FROM ${Constants.DB.TABLE_USER} WHERE passwd=:passwd")
    fun getUserByPasswd(passwd: String): Flow<User>

    @Query("SELECT * FROM ${Constants.DB.TABLE_USER} WHERE email=:email")
    fun getUserByEmail(email: String): Flow<User>

    @Query("SELECT * FROM ${Constants.DB.TABLE_USER} WHERE name=:name")
    fun getUserByName(name: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)
}