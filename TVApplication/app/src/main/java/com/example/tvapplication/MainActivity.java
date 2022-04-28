package com.example.tvapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.tvapplication.network.D3ApiInterface;
import com.example.tvapplication.network.VerificationData;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
@AndroidEntryPoint
public class MainActivity extends FragmentActivity {
@Inject
D3ApiInterface apiInterface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Call<VerificationData> call=apiInterface.
                registerDevice("446717031912-kds4k5sku5vcvuve5k99fpqd2u1drr20.apps.googleusercontent.com"
                ,"email%20profile");

        call.enqueue(new Callback<VerificationData>() {
            @Override
            public void onResponse(Call<VerificationData> call, Response<VerificationData> response) {
                if(response.isSuccessful()){
                    Log.d("debugtv","response"+response.body().getVerification_url());
                }
                else{
                    Log.d("debugtv","response unsuccess"+response.code());
                }

            }


            @Override
            public void onFailure(Call<VerificationData> call, Throwable t) {
                Log.d("debugtv","failure"+t.getLocalizedMessage());

            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_browse_fragment, new MainFragment())
                    .commitNow();
        }
    }
}