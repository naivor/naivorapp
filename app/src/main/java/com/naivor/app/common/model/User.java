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

import com.google.auto.value.AutoValue;
import com.naivor.app.common.model.enums.UserType;

/**
 * 用户的Bean类
 * <p/>
 * Created by tianlai on 16-3-4.
 */
@AutoValue
public abstract class User {
    public abstract int id(); //用户id

    public abstract int age(); //用户年龄

    public abstract String name(); //用户名

    public abstract String headIcon(); //用户头像

    public abstract long phone(); //用户电话

    public abstract String email();//邮箱

    public abstract float score();  //用户评分

    public abstract UserType userType(); //用户类型

    public abstract String city(); //城市

    public abstract String position(); //职位

    public static User.Builder Builder() {
        return new AutoValue_User.Builder()
                .id(0)
                .age(0)
                .name("")
                .email("")
                .headIcon("")
                .phone(0)
                .score(0.0f)
                .city("")
                .position("");
    }

    public abstract Builder newBuilder();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder age(int age); //用户年龄

        public abstract Builder name(String name);

        public abstract Builder headIcon(String headIcon);

        public abstract Builder email(String email);//邮箱

        public abstract Builder phone(long phone);

        public abstract Builder score(float score);  //用户评分

        public abstract Builder userType(UserType userType);

        public abstract Builder city(String city);

        public abstract Builder position(String position); //职位

        public abstract User build();


    }

}
