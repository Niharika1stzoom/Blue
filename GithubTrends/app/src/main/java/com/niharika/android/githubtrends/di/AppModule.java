package com.niharika.android.githubtrends.di;

import com.niharika.android.githubtrends.network.GithubApiInterface;
import com.niharika.android.githubtrends.repository.RepoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    String baseURL = "https://api.github.com/";
    @Singleton
    @Provides
    public GithubApiInterface getRetroServiceInterface(Retrofit retrofit) {
        return retrofit.create(GithubApiInterface.class);
    }
    @Singleton
    @Provides
    public Retrofit getRetroInstance() {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @Singleton
    @Provides
    RepoRepository provideRepository(GithubApiInterface apiInterface){
        return new RepoRepository(apiInterface);
    }
}

