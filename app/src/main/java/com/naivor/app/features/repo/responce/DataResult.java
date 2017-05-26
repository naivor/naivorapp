/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.features.repo.responce;

import lombok.Data;

/**
 * DataResult 请求返回的基类，包含公共部分，比如返回码，返回消息等等
 *
 * Created by tianlai on 16-3-8.
 */
@Data
public  class DataResult<T> {

    private int code;
    private String message;
    private T data;


}
