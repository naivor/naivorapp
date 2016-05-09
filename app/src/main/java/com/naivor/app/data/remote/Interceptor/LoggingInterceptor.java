package com.naivor.app.data.remote.Interceptor;


import com.naivor.app.extras.utils.LogUtil;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * LoggingInterceptor 用来打印请求日志的网络拦截器
 * <p/>
 * Created by tianlai on 16-3-17.
 */
@Singleton
public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "network request";

    @Inject
    public LoggingInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        LogUtil.i(TAG, String.format("Sending request %s on %s%n%s requestType: %s %n contentLength: %d",
                request.url(), chain.connection(), request.headers(), new String(request.method().getBytes()), request
                        .body().contentLength()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        LogUtil.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
