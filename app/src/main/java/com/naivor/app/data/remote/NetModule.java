package com.naivor.app.data.remote;

import com.naivor.app.AppApplication;
import com.naivor.app.R;
import com.naivor.app.extras.utils.AppUtil;
import com.naivor.app.extras.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * NetModule 网络请求模块，提供OkHttpClient，Retrofit
 *
 * Created by tianlai on 16-3-7.
 */
@Module
public class NetModule {

    @Singleton
    @Provides
    public Interceptor privodeInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                return null;
            }
        };
    }

    @Singleton
    @Provides
    public OkHttpClient privodeOkHttpClient() {
        return okTimeOut(new OkHttpClient.Builder(), 60 * 100);

    }

    @Singleton
    @Provides
    public Retrofit privodeRetrofit(AppApplication appApplication, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(getBaseUrl(appApplication))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
    }

    /**
     * 设置网络请求的 interceptor
     *
     * @param client
     * @param interceptor
     * @return
     */
    private OkHttpClient okInterceptor(OkHttpClient client, Interceptor interceptor) {
        client.interceptors().add(interceptor);

        return client;
    }

    /**
     * 设置网络请求超时
     *
     * @param clientBuilder
     * @param timeOut
     * @return
     */
    private OkHttpClient okTimeOut(OkHttpClient.Builder clientBuilder, int timeOut) {
        return clientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS).readTimeout(timeOut,
                TimeUnit.MILLISECONDS).build();
    }

    /**
     * 获取baseUrl，自动判断用测试接口还是正式接口
     *
     * @param appApplication
     * @return
     */
    private String getBaseUrl(AppApplication appApplication) {
        //通过是否打印日志来判断是测试版还是正式版
        int resId;
        if (LogUtil.isLogable()) {
            resId = R.string.api_url_test;
        } else {
            resId = R.string.api_url_release;
        }

        return appApplication.getString(resId) + AppUtil.getAppVersionName(appApplication);
    }
}
