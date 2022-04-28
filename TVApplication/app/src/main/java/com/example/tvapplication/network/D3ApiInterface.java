package com.example.tvapplication.network;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface D3ApiInterface {

    @POST("device/code")
    Call<VerificationData>
    registerDevice(@Query("client_id") String client_id,@Query("scope") String scope);


}