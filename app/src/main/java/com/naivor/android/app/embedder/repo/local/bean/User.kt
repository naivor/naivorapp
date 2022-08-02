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

package com.naivor.android.app.embedder.repo.local.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naivor.android.app.common.repo.local.data.Data
import com.naivor.android.app.others.Constants

/**
 * 用户信息
 */
@Entity(tableName = Constants.DB.TABLE_USER)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var icon: String,
    var gender: Constants.Gender = Constants.Gender.UNKNOWN,
    var phone: String,
    var email: String,
    var birth: Long,
    var desc: String,
    var image: String,
    var profession: String,
    var passwd: String,
    var address: String? = null,
    var isLogin:Boolean=false
) : Data {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        Constants.Gender.valueOf(parcel.readString()!!),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(icon)
        parcel.writeString(gender.name)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeLong(birth)
        parcel.writeString(desc)
        parcel.writeString(image)
        parcel.writeString(profession)
        parcel.writeString(passwd)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}