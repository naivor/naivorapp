package com.naivor.app.features.repo.interceptor;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
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
    private Context context;

    @Inject
    public ParamsInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request orgRequest = chain.request();


        RequestBody body = orgRequest.body();

        if (body != null) {

            RequestBody newBody = null;

            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body);
            }


            if (null != newBody) {

                Request newRequest = orgRequest.newBuilder()
                        .url(orgRequest.url())
                        .method(orgRequest.method(), newBody)
                        .build();

                return chain.proceed(newRequest);
            }


        }

        return chain.proceed(orgRequest);

    }

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param body
     * @return
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

//        添加通用的参数，如
//        String appcode = context.getString(R
//                .string.appkey);
//        builder.addFormDataPart("appcode", appcode);

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }

        return builder.build();
    }

    /**
     * 为FormBody类型请求体添加参数
     *
     * @param body
     * @return
     */
    private FormBody addParamsToFormBody(FormBody body) {
        FormBody.Builder builder = new FormBody.Builder();

//        添加通用的参数，如
//        String appcode = context.getString(R
//                .string.appkey);
//        builder.add("appcode", appcode);


        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
        }

        return builder.build();
    }
}
