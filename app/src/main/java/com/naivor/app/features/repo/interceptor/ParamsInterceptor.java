package com.naivor.app.features.repo.interceptor;

import android.content.Context;

import com.naivor.app.R;
import com.naivor.app.common.base.BaseRepository;
import com.naivor.app.common.model.User;
import com.naivor.app.common.model.enums.UserType;
import com.naivor.app.common.utils.LogUtil;

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
    private static final String TAG = "request params";
    private Context context;

    @Inject
    public ParamsInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request orgRequest = chain.request();


        RequestBody body = orgRequest.body();
        //收集请求参数，方便调试
        StringBuilder paramsBuilder = new StringBuilder();

        if (body != null) {

            RequestBody newBody = null;

            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body, paramsBuilder);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder);
            }


            if (null != newBody) {
                //打印参数
                LogUtil.i(TAG, paramsBuilder.toString());

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
     * @param paramsBuilder
     * @return
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //添加appcode
        String appcode = context.getString(R
                .string.appkey);
        builder.addFormDataPart("appcode", appcode);


        paramsBuilder.append("appcode=" + appcode);

        //添加id，city参数
        User user = BaseRepository.getUser();

        if (user != null) {
            String id = user.id() + "";
            UserType userType = user.userType();

            if (userType == UserType.BEAUTICIAN) {
                builder.addFormDataPart("beautician_id", id);

                paramsBuilder.append("&");
                paramsBuilder.append("beautician_id=" + id);
            } else  if (userType == UserType.BEAUTYSHOP){
                builder.addFormDataPart("bp_id", id);

                paramsBuilder.append("&");
                paramsBuilder.append("bp_id=" + id);
            }

            //城市
            String city = user.city();

            builder.addFormDataPart("city", city);

            paramsBuilder.append("&");
            paramsBuilder.append("city=" + city);
        }

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
     * @param paramsBuilder
     * @return
     */
    private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder) {
        FormBody.Builder builder = new FormBody.Builder();

        //添加appcode
        String appcode = context.getString(R
                .string.appkey);
        builder.add("appcode", appcode);

        paramsBuilder.append("appcode=" + appcode);

        //添加id，city参数
        User user = BaseRepository.getUser();
        if (user != null) {
            String id = user.id() + "";
            UserType userType = user.userType();

            if (userType == UserType.BEAUTICIAN) {
                builder.add("beautician_id", id);

                paramsBuilder.append("&");
                paramsBuilder.append("beautician_id=" + id);
            } else  if (userType == UserType.BEAUTYSHOP){
                builder.add("bp_id", id);

                paramsBuilder.append("&");
                paramsBuilder.append("bp_id=" + id);
            }

            //城市
            String city = user.city();

            builder.add("city", city);

            paramsBuilder.append("&");
            paramsBuilder.append("city=" + city);
        }

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
            paramsBuilder.append("&");
            paramsBuilder.append(body.name(i));
            paramsBuilder.append("=");
            paramsBuilder.append(body.value(i));
        }

        return builder.build();
    }
}
