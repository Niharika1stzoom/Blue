package com.niharika.android.githubtrends.ui.login;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;
import com.niharika.android.githubtrends.model.Repo;
import com.niharika.android.githubtrends.repository.LoginRepository;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<AuthResult> mLiveData;
    public LoginViewModel() {
        mLiveData = new MutableLiveData();
    }
    public LiveData<AuthResult> signIn(Context context) {
        LoginRepository.signInGithub(mLiveData,context);
        return mLiveData;
    }




}