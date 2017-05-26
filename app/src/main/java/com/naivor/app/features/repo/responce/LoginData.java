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

import com.naivor.app.common.model.User;
import com.naivor.app.common.model.enums.UserType;

/**
 * 登录请求返回的结果
 * <p>
 * Created by tianlai on 16-3-8.
 */
public class LoginData {
    private int id;  //用户的id

    private String type; //用户的类型

    private String name;
    private String blog;
    private String location;
    private String email;

    /**
     * 将数据转换成User
     *
     * @return
     */
    public User userMapper() {

        return User.builder()
                .id(id)
                .userType(UserType.BEAUTICIAN)
                .email("")
                .city("")
                .build();

    }


}
