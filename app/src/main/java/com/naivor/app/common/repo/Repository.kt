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
package com.naivor.app.common.repo

import com.naivor.app.common.base.TaskDispatchers
import com.naivor.app.common.repo.local.LocalDataSource
import com.naivor.app.common.repo.remote.RemoteDataSource

/**
 * app的数据仓库类，所有需要的数据都通过它获得
 *
 *
 * Created by Naivor on 16-3-3.
 */
open class Repository<out T : LocalDataSource, out R : RemoteDataSource<*>>(
    val local: T?,
    val remote: R?
) {

    protected val compute = TaskDispatchers.compute
}