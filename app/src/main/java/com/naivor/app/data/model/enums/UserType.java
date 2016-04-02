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

package com.naivor.app.data.model.enums;

/**
 * 用户的类型，普通，VIP
 * <p/>
 * Created by tianlai on 16-3-10.
 */
public enum UserType {
    NORMAL("normal"), VIP("vip");

    private final String value;

    private UserType(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }

    public static UserType getType(String type) {
        if (type != null) {

            switch (type) {
                case "normal":

                    return UserType.NORMAL;
                case "vip":

                    return UserType.VIP;

                default:
                    break;
            }
        }
        return null;

    }
}