package com.naivor.app.data.remote;

import com.naivor.app.data.remote.ApiService.LoginApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by tianlai on 16-3-8.
 */
@Module
public class ApiModule {

    @Provides
    LoginApiService  provideLoginApiService(Retrofit retrofit){
        return retrofit.create(LoginApiService.class);
    }
}
