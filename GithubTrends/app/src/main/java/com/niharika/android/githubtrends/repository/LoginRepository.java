package com.niharika.android.githubtrends.repository;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

import static com.niharika.android.githubtrends.utility.AppConstants.PROVIDER_URL;

public class LoginRepository {
    static public void signInGithub(MutableLiveData<AuthResult> liveData, Context context) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        OAuthProvider.Builder provider = OAuthProvider.newBuilder(PROVIDER_URL);
        List<String> scopes =
                new ArrayList<String>() {
                    {
                        add("user:email");
                    }
                };
        provider.setScopes(scopes);
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            pendingResultTask
                    .addOnSuccessListener(
                            authResult -> {
                                liveData.postValue(authResult);
                            })
                    .addOnFailureListener(
                            e -> {
                                liveData.postValue(null);
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            firebaseAuth
                    .startActivityForSignInWithProvider(/* activity= */ (Activity) context, provider.build())
                    .addOnSuccessListener(
                            authResult -> liveData.postValue(authResult))
                    .addOnFailureListener(
                            e -> {
                                liveData.postValue(null);
                            });
        }
    }
}