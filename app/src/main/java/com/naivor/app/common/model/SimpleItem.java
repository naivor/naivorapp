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

/**
 * Created by tianlai on 16-3-24.
 */
@AutoValue
public abstract class SimpleItem {

    public abstract int resId();

    public abstract String name();

    public abstract String content();

    public static Builder Builder(){
        return new AutoValue_SimpleItem.Builder()
                .resId(0)
                .content("")
                .name("");
    }

    public abstract Builder newBuilder();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder resId(int resId);

        public abstract Builder name(String name);

        public abstract Builder content(String content);

        public abstract SimpleItem build();
    }
}
