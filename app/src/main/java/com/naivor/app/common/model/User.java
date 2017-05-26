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

package com.naivor.app.common.model;

import com.naivor.app.common.model.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 用户的Bean类
 * <p/>
 * Created by tianlai on 16-3-4.
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public   class User {
    private  int id; //用户id

    private  int age; //用户年龄

    private  String name; //用户名

    private  String headIcon; //用户头像

    private  long phone; //用户电话

    private  String email;//邮箱

    private  float score;  //用户评分

    private  UserType userType; //用户类型

    private  String city; //城市

    private  String position; //职位


}
