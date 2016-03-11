package com.naivor.app.data.remote.ApiService;

import android.database.Observable;

import com.naivor.app.data.remote.ApiResponce.LoginResponce;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * LoginApiService 登录的请求接口
 *
 * Created by tianlai on 16-3-8.
 */
public interface LoginApiService {


    @FormUrlEncoded
    @POST("/reg/login")
    Observable<LoginResponce> login(@Field("mobile") String mobile,@Field("psw") String psw);
}
