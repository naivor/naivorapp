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

package com.naivor.app.data.remote.Interceptor;

import android.content.Context;

import com.naivor.app.R;
import com.naivor.app.data.model.User;
import com.naivor.app.domain.repository.BaseRepository;
import com.naivor.app.extras.utils.LogUtil;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ParamsInterceptor 用来添加通用请求参数的网络拦截器，主要添加 id，city，appcode(经测试，拦截器无法操作原requestBody添加参数，此类无用)
 * <p/>
 * Created by tianlai on 16-3-16.
 */
@Singleton
public class ParamsInterceptor implements Interceptor {
    private static final String TAG="request params";
    private Context context;

    @Inject
    public ParamsInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request orgRequest = chain.request();

        RequestBody body=orgRequest.body();

        //收集请求参数，方便调试
        StringBuilder paramsLogBuilder = new StringBuilder();

        if (body instanceof FormBody){
            FormBody.Builder builder=new FormBody.Builder();

            //添加appcode
            String appcode = context.getString(R
                    .string.appkey);
            builder.add("appcode", appcode);

            paramsLogBuilder.append("appcode=" + appcode);

            //添加id，city，用户类型参数
            User user = BaseRepository.getUser();
            if (user != null) {
                // id
                String id = user.id() + "";
                builder.add("uid", id);
                paramsLogBuilder.append("&");
                paramsLogBuilder.append("uid=" + id);

                // type
                String userType = user.userType().getValue();
                builder.add("type", userType);
                paramsLogBuilder.append("&");
                paramsLogBuilder.append("type=" + userType);

                //城市
                String city = user.city();
                builder.add("city", city);
                paramsLogBuilder.append("&");
                paramsLogBuilder.append("city=" + city);
            }

            //添加原请求体
            FormBody oldBody= (FormBody) body;

            for (int i=0;i<oldBody.size();i++){
                builder.addEncoded(oldBody.encodedName(i),oldBody.encodedValue(i));

                paramsLogBuilder.append("&");
                paramsLogBuilder.append(oldBody.name(i));
                paramsLogBuilder.append("=");
                paramsLogBuilder.append(oldBody.value(i));
            }

            //打印参数
            LogUtil.i(TAG, paramsLogBuilder.toString());


            Request newRequest = orgRequest.newBuilder()
                    .url(orgRequest.url())
                    .method(orgRequest.method(), builder.build())
                    .build();

            return chain.proceed(newRequest);
        }

        return chain.proceed(orgRequest);

    }
}
