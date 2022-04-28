package com.example.tvapplication.di;

import com.example.tvapplication.network.D3ApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class APIModule {
    // String baseURL="https://mocki.io/v1/";
    String baseURL="https://oauth2.googleapis.com/";
    @Singleton
    @Provides
    public D3ApiInterface getRestApiInterface(Retrofit retrofit) {
        return retrofit.create(D3ApiInterface.class);
    }
    @Singleton
    @Provides
    public Retrofit getRetroInstance() {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
